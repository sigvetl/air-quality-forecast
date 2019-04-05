package no.uio.ifi.in2000.gruppe55

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kart)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
                    runOnUiThread {
                        if (timeList != null){
                            val aqi = "Current AQI: "
                            for (timestamp in timeList){
                                if (timestamp.from == "2019-03-27T13:00:00Z"){
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
