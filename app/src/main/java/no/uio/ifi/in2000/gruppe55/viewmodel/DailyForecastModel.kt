package no.uio.ifi.in2000.gruppe55.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import no.uio.ifi.in2000.gruppe55.AirQualityTimeDataModel
import no.uio.ifi.in2000.gruppe55.StationModel
import java.util.*

class DailyForecastModel(private val forecastRepository: ForecastRepository = ForecastRepository()) : ViewModel() {

    // TODO (julianho): Observations currently have no way to only pick-up partial updates (such as insertions),
    // possibly impacting performance. If performance becomes a concern, consider how to implement such updates.
    private val mutableStations: MutableLiveData<HashMap<StationModel, AirQualityTimeDataModel?>> by lazy {
        MutableLiveData<HashMap<StationModel, AirQualityTimeDataModel?>>()
    }

    val stations: LiveData<HashMap<StationModel, AirQualityTimeDataModel?>>
        get() = mutableStations

    suspend fun loadStations() {
        val repositoryMap = forecastRepository.list()
        val stationMap = HashMap<StationModel, AirQualityTimeDataModel?>(repositoryMap.size)

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