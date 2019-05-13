package no.uio.ifi.in2000.gruppe55

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelStore
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
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

    internal inner class CustomInfoWindowAdapter : GoogleMap.InfoWindowAdapter {
        // These are both view groups containing an ImageView with id "badge" and two
        // TextViews with id "title" and "snippet"


        override fun getInfoWindow(marker: Marker): View? {
                return null
            }

        override fun getInfoContents(marker: Marker): View? {
            val contents: View = layoutInflater.inflate(R.layout.marker_layout, null)

            val title = contents.findViewById<TextView>(R.id.title)
            val distance = contents.findViewById<TextView>(R.id.snippet)

            title.text = marker.title

            val infoWindowData : InfoWindowData = marker.tag as InfoWindowData

            distance.text = infoWindowData.snippet

            return contents
        }

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter())

        //LatLng fra Forskningsparken
        val center = LatLng(59.94365597189331, 10.718679747209503)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 11.0f))
        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true

        //color
        val green = 130f
        val yellow = 2.05497e-38f
        val orange = 5.848787e-39f
        val red = 2.15118e-38f


        // Keep map fragment in sync with measurements from the list of stations.

        dailyForecastModel.stations.observe({ lifecycle }) { stationMap ->
            mMap.clear()

            for ((station, measurement) in stationMap ?: HashMap()) {
                if (station.latitude != null && station.longitude != null && station.name != null) {
                    val position = LatLng(station.latitude, station.longitude)
                    val value = "Current AQI: ${String.format("%.2f",measurement?.variables?.aqi?.value)}"
                    val currentLocation = LatLng(59.94365597189331, 10.718679747209503)
                    val distance = "Distance to location: ${String.format("%.2f",SphericalUtil.computeDistanceBetween(currentLocation, position)/1000)} km"
                    var markerColor = 0f
                    if (measurement?.variables?.aqi?.value!! >=4){
                        markerColor = BitmapDescriptorFactory.HUE_RED
                    }
                    else if (measurement?.variables?.aqi?.value!! >=3){
                        markerColor = BitmapDescriptorFactory.HUE_ORANGE
                    }
                    else if (measurement?.variables?.aqi?.value!! >=2){
                        markerColor = BitmapDescriptorFactory.HUE_YELLOW
                    }
                    else if (measurement?.variables?.aqi?.value!! >=1){
                        markerColor = BitmapDescriptorFactory.HUE_GREEN
                    }

                    activity?.runOnUiThread {
                        val extraSnippet = InfoWindowData(distance + "\n" + value)
                        val m  = mMap.addMarker(MarkerOptions().position(position).title(station.name))
                        m.setIcon(BitmapDescriptorFactory.defaultMarker(markerColor))
                        m.tag = extraSnippet
                        m.showInfoWindow()
                    }
                }
            }
        }
    }
}
