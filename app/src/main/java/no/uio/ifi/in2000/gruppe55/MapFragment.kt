package no.uio.ifi.in2000.gruppe55

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelStore
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
import no.uio.ifi.in2000.gruppe55.viewmodel.DailyForecastModel

class MapFragment : Fragment(), OnMapReadyCallback {

    // List of viewmodels relevant to this fragment.

    private lateinit var viewModelProvider: ViewModelProvider
    private lateinit var dailyForecastModel: DailyForecastModel

    private lateinit var mMap: GoogleMap


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Extract all the relevant viewmodels from the fragment's context.

        viewModelProvider = ViewModelProvider(
            activity?.viewModelStore ?: ViewModelStore(),
            ViewModelProvider.NewInstanceFactory()
        )
        dailyForecastModel = viewModelProvider.get(DailyForecastModel::class.java)
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

        // Keep map fragment in sync with measurements from the list of stations.

        dailyForecastModel.stations.observe({ lifecycle }) { stationMap ->
            mMap.clear()

            for ((station, measurement) in stationMap ?: HashMap()) {
                if (station.latitude != null && station.longitude != null && station.name != null) {
                    val position = LatLng(station.latitude, station.longitude)
                    val value = "Current AQI: ${measurement?.variables?.aqi?.value}"

                    activity?.runOnUiThread {
                        mMap.addMarker(MarkerOptions().position(position).title(station.name).snippet(value))
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
