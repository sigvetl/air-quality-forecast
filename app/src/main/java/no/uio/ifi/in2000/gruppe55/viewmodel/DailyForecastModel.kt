package no.uio.ifi.in2000.gruppe55.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import no.uio.ifi.in2000.gruppe55.AirQualityTimeDataModel
import no.uio.ifi.in2000.gruppe55.StationModel
import no.uio.ifi.in2000.gruppe55.database.StationEntity
import java.util.*

/**
 * [DailyForecastModel] implements a [ViewModel] that represents an up-to-date, hourly view of airquality according to a
 * given source. By observing changes to [stations], different [Fragment]s and [Activity]s can update their views in a
 * way that requires no knowledge about how the data is synchronised and maintained.
 *
 * [DailyForecastModel] implements the "View Model" of Android Architecture Components, providing a flexible and
 * extensible way to separate all the data & corresponding logic of an interface from the actual code to maintain and
 * display the interface.
 */
class DailyForecastModel(application: Application) : AndroidViewModel(application) {

    private val forecastRepository: ForecastRepository = ForecastRepository(application)

    // TODO (julianho): Observations currently have no way to only pick-up partial updates (such as insertions),
    // possibly impacting performance. If performance becomes a concern, consider how to implement such updates.
    private val mutableStations: MutableLiveData<HashMap<StationEntity, AirQualityTimeDataModel?>> by lazy {
        MutableLiveData<HashMap<StationEntity, AirQualityTimeDataModel?>>()
    }

    /**
     * [stations] is an up-to-date, hourly view mapping each station to its current measurements (if there are any.)
     */
    val stations: LiveData<HashMap<StationEntity, AirQualityTimeDataModel?>>
        get() = mutableStations

    /**
     * [loadStations] *may* update the [DailyForecastModel]'s internal measurements & list of stations and thereby
     * updating all views that depend on it through [stations].
     */
    suspend fun loadStations() {
        val repositoryMap = forecastRepository.list()
        val stationMap = HashMap<StationEntity, AirQualityTimeDataModel?>(repositoryMap.size)

        // Station list can be displayed without AQI values in order to allow quick user interface response times.

        for ((station, repository) in repositoryMap) {
            stationMap[station] = null
        }
        mutableStations.postValue(stationMap)

        // Lazily update each station's AQI values as they are available.

        for ((station, repository) in repositoryMap) {
            // TODO (julianho): Extract the current date & time in the correct time zone.
            val timeDataModel = repository.at(Date())
            stationMap[station] = timeDataModel
            mutableStations.postValue(stationMap)
        }
    }
}