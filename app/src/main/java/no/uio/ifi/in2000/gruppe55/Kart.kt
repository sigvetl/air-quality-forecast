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
        
        //Liste til å holde på retrofit-objektene
        var storbyer = arrayListOf(LatLng(59.91, 10.758), LatLng(60.39, 5.32), LatLng(63.43, 10.395),
            LatLng(58.97, 5.733), LatLng(69.649, 18.955), LatLng(59.744, 10.204), LatLng(58.16, 8.018)
        )
        val center = LatLng(64.0, 11.0)
        //Iterere over objektene og opprette markers med koordinater, navn, verdier etc
        for (e in storbyer){
            mMap.addMarker(MarkerOptions().position(e))
        }
        //mMap.addMarker(MarkerOptions().position(oslo).title("Oslo"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center , 5.0f))
        val uiSettings = mMap.uiSettings
        uiSettings.setZoomControlsEnabled(true)
    }
}
