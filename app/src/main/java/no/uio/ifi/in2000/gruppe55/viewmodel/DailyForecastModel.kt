package no.uio.ifi.in2000.gruppe55.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.gruppe55.AirQualityLocationModel
import no.uio.ifi.in2000.gruppe55.Airqualityforecast
import no.uio.ifi.in2000.gruppe55.StationModel

// TODO (julianho): Replace ad-hoc singleton with *anything* more principled and idiomatic.
val dailyForecastModel: DailyForecastModel by lazy {
    ViewModelProvider.NewInstanceFactory().create(DailyForecastModel::class.java)
}

class DailyForecastModel : ViewModel() {
    // TODO (julianho): Observations currently have no way to only pick-up partial updates (such as insertions),
    // possibly impacting performance. If performance becomes a concern, consider how to implement such updates.
    private val mutableStations: MutableLiveData<HashMap<StationModel, AirQualityLocationModel?>> by lazy {
        MutableLiveData<HashMap<StationModel, AirQualityLocationModel?>>()
    }

    val stations: LiveData<HashMap<StationModel, AirQualityLocationModel?>>
        get() = mutableStations

    suspend fun loadStations() {
        val stationMap = HashMap<StationModel, AirQualityLocationModel?>()
        val stationList = Airqualityforecast.stations()

        // Station list can be displayed without AQI values in order to allow quick user interface response times.
        for (station in stationList) {
            stationMap[station] = null
        }
        mutableStations.postValue(stationMap)

        // Lazily update each station's AQI values as they are available.
        stationList.map { station ->
            // TODO (julianho): The asynchronous coroutines are executed in global scope, whereas they should be
            // executed in a nested scope of the suspensions coroutine.
            launch {
                val location = Airqualityforecast.main(lat = station.latitude, lon = station.longitude)
                stationMap[station] = location
                mutableStations.postValue(stationMap)
            }
        }.forEach { job -> job.join() }
    }
}