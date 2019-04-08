package no.uio.ifi.in2000.gruppe55

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return rootView
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        launch {
            val stations = Airqualityforecast.stations()
            //Iterere over objektene og opprette markers med koordinater, navn, verdier etc
            for (station in stations) {
                if (station.longitude != null && station.latitude != null && station.name != null) {
                    val latLng = LatLng(station.latitude, station.longitude)
                    val stationdata = Airqualityforecast.main(station = station.eoi)
                    val timeList = stationdata.data?.time
                    activity?.runOnUiThread {
                        if (timeList != null){
                            val aqi = "Current AQI: "
                            for (timestamp in timeList){
                                if (timestamp.from == "2019-04-09T13:00:00Z"){
                                    val aqiValue = timestamp.variables?.aqi?.value.toString()
                                    val value = aqi+aqiValue
                                    mMap.addMarker(MarkerOptions().position(latLng).title(station.name).snippet(value))
                                }
                            }

                        }
                    }

                }
            }
        }

        //landssentrert
        val center = LatLng(64.0, 11.0)
        //Oslo-sentrert, zoom level 11 +
        //val center2 = LatLng(59.91, 10.75)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center , 5.0f))
        val uiSettings = mMap.uiSettings
        uiSettings.setZoomControlsEnabled(true)
    }
}
