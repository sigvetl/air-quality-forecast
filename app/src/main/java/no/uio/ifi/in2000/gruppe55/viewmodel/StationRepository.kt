package no.uio.ifi.in2000.gruppe55.viewmodel

import no.uio.ifi.in2000.gruppe55.AirQualityTimeDataModel
import no.uio.ifi.in2000.gruppe55.Airqualityforecast
import no.uio.ifi.in2000.gruppe55.StationModel
import java.util.Date

// TODO: Implement caching of requests to reduce unnecessary network usage.
class StationRepository(private val stationModel: StationModel) {
    suspend fun at(date: Date): AirQualityTimeDataModel {
        val locationModel = Airqualityforecast.main(
            lat = stationModel.latitude,
            lon = stationModel.longitude
        )

        // TODO (julianho): Actually pick the measurement corresponding to the given date & time. Currently just picking
        // the first measurement in the day for the sake of simplicity.
        return locationModel.data!!.time!!.first()
    }
}