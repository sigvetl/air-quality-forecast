package no.uio.ifi.in2000.gruppe55

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

// TODO (julianho): Replace ad-hoc singleton with *anything* more principled and idiomatic.
val airqualityforecastModel: AirqualityforecastModel by lazy {
    ViewModelProvider.NewInstanceFactory().create(AirqualityforecastModel::class.java)
}

class AirqualityforecastModel : ViewModel() {
    private val mutableStations: MutableLiveData<List<StationModel>> by lazy {
        MutableLiveData<List<StationModel>>()
    }

    val stations: LiveData<List<StationModel>>
        get() = mutableStations

    suspend fun loadStations() {
        val stations = Airqualityforecast.stations()
        mutableStations.postValue(stations)
    }
}