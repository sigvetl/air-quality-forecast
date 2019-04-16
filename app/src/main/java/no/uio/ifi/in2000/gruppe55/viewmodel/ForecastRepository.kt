package no.uio.ifi.in2000.gruppe55.viewmodel

import no.uio.ifi.in2000.gruppe55.Airqualityforecast
import no.uio.ifi.in2000.gruppe55.StationModel

// TODO (julianho): Consider turning into interface *or* parametrising on `AirqualityforecastInterface`.
// TODO: Implement caching of requests to reduce unnecessary network usage.

/**
 * [ForecastRepository] represents a simple, idiomatic way to access a set of stations which can be used to perform
 * further measurements.
 *
 * Essentially, think of [ForecastRepository] as simply a container of [StationRepository]s, where [list] can be used to
 * access said list at the current moment in time.
 *
 * [ForecastRepository] implements the "Repository" of Android Architecture Components, providing a flexible and
 * extensible method to separate concerns of data gathering from user interfaces.
 */
class ForecastRepository {
    /**
     * [list] asynchronously extracts the current list of [StationModel]s and their corresponding [StationRepository]s.
     * Said repositories can be used to extract actual measurements from the stations in question.
     *
     * [list] is suspendable and must therefore be executed in a Kotlin coroutine (e.g. via [launch] or [runBlocking].)
     */
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