package no.uio.ifi.in2000.gruppe55.viewmodel

import android.app.Application
import no.uio.ifi.in2000.gruppe55.AirQualityTimeDataModel
import no.uio.ifi.in2000.gruppe55.Airqualityforecast
import no.uio.ifi.in2000.gruppe55.StationModel
import no.uio.ifi.in2000.gruppe55.database.DailyForecastDatabase
import no.uio.ifi.in2000.gruppe55.database.MeasurementEntity
import no.uio.ifi.in2000.gruppe55.database.StationEntity
import org.threeten.bp.Instant

// TODO (julianho): Consider turning into interface *or* parametrising on `AirqualityforecastInterface`.
// TODO: Implement caching of requests to reduce unnecessary network usage.

/**
 * [StationRepository] represents a simple, idiomatic way to access measurements (specifically, air quality
 * measurements) from a given station at a given point in time.
 *
 * [StationRepository] implements the "Repository" of Android Architecture Components, providing a flexible and
 * extensible method to separate concerns of data gathering from user interfaces.
 */
class StationRepository(private val application: Application, private val stationModel: StationModel) {

    private val dailyForecastDatabase = DailyForecastDatabase.of(application.applicationContext)
    private val stationDao = dailyForecastDatabase.stationDao()
    private val measurementDao = dailyForecastDatabase.measurementDao()

    /**
     * [at] asynchronously extracts air quality measurements from the relevant station at a given point in time.
     *
     * If [date] is deeply in the past (e.g. one month ago), there is no guarantee that any value will be returned at
     * all.
     *
     * [at] is suspendable and must therefore be executed in a Kotlin coroutine (e.g. via [launch] or [runBlocking].)
     */
    suspend fun at(timestamp: Instant): AirQualityTimeDataModel? {
        // If there already exists a previously-cached measurement, simply reuse that one.

        for (measurement in measurementDao.recentTo(stationModel.eoi ?: "", timestamp)) {
            return measurement.airQualityTimeDataModel
        }

        val locationModel = Airqualityforecast.main(
            lat = stationModel.latitude,
            lon = stationModel.longitude
        )

        // Add every airquality throughout the day into the database.

        for (moment in locationModel.data?.time ?: arrayListOf()) {
            val middleDate = Instant.parse(moment.from) ?: Instant.now()

            // Ensure the relevant station is always marked as one. If not, foreign keys requirements can be violated
            // when communicating with SQLite.

            val station = StationEntity(
                eoi = stationModel.eoi ?: "",
                name = stationModel.name ?: "",
                kommune = stationModel.kommune?.name ?: "",
                latitude = stationModel.latitude ?: -1.0,
                longitude = stationModel.longitude ?: -1.0
            )

            if (!stationDao.has(station.eoi)) {
                stationDao.insert(station)
            }

            // Cache the measurement to the associated date and time.

            val measurement = MeasurementEntity(
                eoi = station.eoi,
                timestamp = middleDate,
                aqi = moment.variables?.aqi?.value ?: 0.0
            )

            if (!measurementDao.has(measurement.eoi, measurement.timestamp)) {
                measurementDao.insert(measurement)
            }
        }

        // Return the first now-cached airquality most relevant to the current time.

        for (measurement in measurementDao.recentTo(stationModel.eoi ?: "", timestamp)) {
            return measurement.airQualityTimeDataModel
        }

        return null
    }
}