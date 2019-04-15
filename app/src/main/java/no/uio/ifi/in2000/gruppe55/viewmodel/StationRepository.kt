package no.uio.ifi.in2000.gruppe55.viewmodel

import no.uio.ifi.in2000.gruppe55.AirQualityTimeDataModel
import no.uio.ifi.in2000.gruppe55.Airqualityforecast
import no.uio.ifi.in2000.gruppe55.StationModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO (julianho): Consider turning into interface *or* parametrising on `AirqualityforecastInterface`.
// TODO: Implement caching of requests to reduce unnecessary network usage.

/**
 * [StationRepository] represents a simple, idiomatic way to access measurements (specifically, air quality
 * measurements) from a given station at a given point in time.
 *
 * [StationRepository] implements the "Repository" of Android Architecture Components, providing a flexible and
 * extensible method to separate concerns of data gathering from user interfaces.
 */
class StationRepository(private val stationModel: StationModel) {
    /**
     * [at] asynchronously extracts air quality measurements from the relevant station at a given point in time.
     *
     * If [date] is deeply in the past (e.g. one month ago), there is no guarantee that any value will be returned at
     * all.
     *
     * [at] is suspendable and must therefore be executed in a Kotlin coroutine (e.g. via [launch] or [runBlocking].)
     */
    suspend fun at(date: Date): AirQualityTimeDataModel? {
        val locationModel = Airqualityforecast.main(
            lat = stationModel.latitude,
            lon = stationModel.longitude
        )

        for (moment in locationModel.data?.time ?: ArrayList()) {
            // Airqualityforecast times are weird, so simply pick either the first or second date & time and consider
            // that to be "now". Additionally, all Airqualityforecast times should be within a Norwegian time-zone.

            val middleDate = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.forLanguageTag("no")
            ).parse(moment?.from)

            // To find the measurement of the current data & time, one needs to pick times within a range. One potential
            // range is the previous time plus/minus 30 minutes.

            var startDate = middleDate.clone() as Date
            startDate.minutes -= 30

            var endDate = middleDate.clone() as Date
            endDate.minutes += 30

            // Pick the first measurement within the relevant date & time.

            if (date.time > startDate.time && date.time < endDate.time) {
                return moment
            }
        }

        return null
    }
}