package no.uio.ifi.in2000.gruppe55.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.gruppe55.AirQualityLocationModel
import no.uio.ifi.in2000.gruppe55.Airqualityforecast
import no.uio.ifi.in2000.gruppe55.StationModel
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap

// TODO (julianho): Replace ad-hoc singleton with *anything* more principled and idiomatic.
val airqualityforecastModel: AirqualityforecastModel by lazy {
    ViewModelProvider.NewInstanceFactory().create(AirqualityforecastModel::class.java)
}

class AirqualityforecastModel : ViewModel() {
    private val mutableStations: MutableLiveData<List<StationModel>> by lazy {
        MutableLiveData<List<StationModel>>().also {
            launch {
                val stations = Airqualityforecast.stations()
                it.postValue(stations)
            }
        }
    }

    // TODO (julianho): Replace this ungodly bad implementation of location statistics with something coherent.
    private val mutableLocations: ConcurrentHashMap<StationModel, MutableLiveData<AirQualityLocationModel>> by lazy {
        val map = ConcurrentHashMap<StationModel, MutableLiveData<AirQualityLocationModel>>()
        mutableStations.observeForever { stations ->
            map.clear()
            for (station in stations ?: listOf()) {
                map[station] = MutableLiveData<AirQualityLocationModel>().also {
                    launch {
                        try {
                            val location = Airqualityforecast.main(lat = station.latitude, lon = station.longitude)
                            it.postValue(location)
                        } catch (exception: Exception) {
                            exception.printStackTrace()
                        }
                    }
                }
            }
        }
        map
    }

    val stations: LiveData<List<StationModel>>
        get() = mutableStations

    fun location(station: StationModel): LiveData<AirQualityLocationModel> {
        return mutableLocations.get(station) ?: MutableLiveData()
    }
}