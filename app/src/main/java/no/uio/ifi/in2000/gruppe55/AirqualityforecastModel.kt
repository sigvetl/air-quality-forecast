package no.uio.ifi.in2000.gruppe55

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class AirqualityforecastModel : ViewModel() {
    private val mutableStationList: MutableLiveData<List<StationModel>> by lazy {
        val data = MutableLiveData<List<StationModel>>()
        data.value = ArrayList<StationModel>() +
                StationModel(
                    height = 0.3,
                    name = "Porsgrunn",
                    delomrade = null,
                    eoi = null,
                    grunnkrets = null,
                    kommune = null,
                    latitude = null,
                    longitude = null
                ) +
                StationModel(
                    height = 1.4,
                    name = "Bergen",
                    delomrade = null,
                    eoi = null,
                    grunnkrets = null,
                    kommune = null,
                    latitude = null,
                    longitude = null
                ) +
                StationModel(
                    height = 2.2,
                    name = "Trondheim",
                    delomrade = null,
                    eoi = null,
                    grunnkrets = null,
                    kommune = null,
                    latitude = null,
                    longitude = null
                ) +
                StationModel(
                    height = 3.4,
                    name = "Oslo",
                    delomrade = null,
                    eoi = null,
                    grunnkrets = null,
                    kommune = null,
                    latitude = null,
                    longitude = null
                ) +
                StationModel(
                    height = 5.3,
                    name = "Skien",
                    delomrade = null,
                    eoi = null,
                    grunnkrets = null,
                    kommune = null,
                    latitude = null,
                    longitude = null
                )
        data
    }

    val stationList: LiveData<List<StationModel>>
        get() = mutableStationList
}