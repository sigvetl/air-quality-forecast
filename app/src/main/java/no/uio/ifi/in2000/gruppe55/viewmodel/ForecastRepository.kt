package no.uio.ifi.in2000.gruppe55.viewmodel

import no.uio.ifi.in2000.gruppe55.Airqualityforecast
import no.uio.ifi.in2000.gruppe55.StationModel

// TODO: Implement caching of requests to reduce unnecessary network usage.
class ForecastRepository {
    suspend fun list(): HashMap<StationModel, StationRepository> {
        val stationList = Airqualityforecast.stations()
        val stationMap = HashMap<StationModel, StationRepository>(stationList.size)

        for (station in stationList) {
            val repository = StationRepository(station)
            stationMap[station] = repository
        }

        return stationMap
    }
}