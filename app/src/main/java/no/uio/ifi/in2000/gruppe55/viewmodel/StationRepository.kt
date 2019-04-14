package no.uio.ifi.in2000.gruppe55.viewmodel

import no.uio.ifi.in2000.gruppe55.AirQualityTimeDataModel
import no.uio.ifi.in2000.gruppe55.Airqualityforecast
import no.uio.ifi.in2000.gruppe55.StationModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Implement caching of requests to reduce unnecessary network usage.
class StationRepository(private val stationModel: StationModel) {
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