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

class Kart : AppCompatActivity(), OnMapReadyCallback {

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
            val stasjoner = Airqualityforecast.stations()
            //Iterere over objektene og opprette markers med koordinater, navn, verdier etc
            runOnUiThread {
                for (stasjon in stasjoner) {
                    if (stasjon.longitude != null && stasjon.latitude != null && stasjon.name != null) {
                        val latLng = LatLng(stasjon.latitude, stasjon.longitude)
                        mMap.addMarker(MarkerOptions().position(latLng).title(stasjon.name))
                    }
                }
            }
        }

        val center = LatLng(64.0, 11.0)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center , 5.0f))
        val uiSettings = mMap.uiSettings
        uiSettings.setZoomControlsEnabled(true)
    }
}
