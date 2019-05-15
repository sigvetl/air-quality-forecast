package no.uio.ifi.in2000.gruppe55

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelStore
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.element_parent.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.gruppe55.viewmodel.DailyForecastModel
import no.uio.ifi.in2000.gruppe55.viewmodel.FavoriteStationModel
import java.util.*
import kotlin.collections.HashMap
import kotlin.coroutines.coroutineContext

class ListFragment : Fragment() {

    // List of viewmodels relevant to this fragment.

    private lateinit var viewModelProvider: ViewModelProvider
    private lateinit var dailyForecastModel: DailyForecastModel
    private lateinit var favoriteStationModel: FavoriteStationModel

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Extract all the relevant viewmodels from the fragment's context.

        // TODO: Consider *when* in the Fragment's lifecycle that view models should be extracted.
        viewModelProvider = ViewModelProvider(
            activity?.viewModelStore ?: ViewModelStore(),
            ViewModelProvider.AndroidViewModelFactory(activity!!.application)
        )
        dailyForecastModel = viewModelProvider.get(DailyForecastModel::class.java)
        favoriteStationModel = viewModelProvider.get(FavoriteStationModel::class.java)

        linearLayoutManager = LinearLayoutManager(context)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        my_recycler_view.layoutManager = layoutManager
        my_recycler_view.setItemViewCacheSize(50)

        val adapter = ListAdapter(context, eListe.elementer, favoriteStationModel)
        my_recycler_view.adapter = adapter

        // Keep list adapter view in sync with measurements from the list of stations.

        dailyForecastModel.stations.observe({ this.lifecycle }) { stationMap ->
            eListe.elementer.clear()

            // Group stations under common area (e,g, county.)
            val areaMap = TreeMap<String, HashMap<StationModel, AirQualityTimeDataModel?>>()

            for ((station, location) in stationMap ?: hashMapOf()) {
                val area = station.kommune?.name

                if (area != null) {
                    if (areaMap.containsKey(area)) {
                        areaMap[area]?.put(station, location)
                    } else {
                        areaMap[area] = hashMapOf(Pair(station, location))
                    }
                }
            }

            // TODO (julianho): Comparator should handle null gracefully.
            val comparator: Comparator<StationModel> = Comparator { s, t -> s.name!!.compareTo(t.name!!) }

            for ((area, map) in areaMap) {
                var totalAqi: Double? = 0.0
                val stationList = mutableListOf<Element>()

                for ((station, location) in map.toSortedMap(comparator)) {
                    val aqi = location?.variables?.aqi?.value

                    if (totalAqi != null) {
                        totalAqi = if (aqi == null) { null } else { totalAqi + aqi }
                    }

                    stationList.add(Element(eListe.STATION, station.name, station.eoi, aqi))
                }

                val averageAqi = if (totalAqi == null) { null } else { totalAqi / stationList.size }

                // TODO: Should alone stations be shown under drop-down?
                //TODO: Legg til sationId
                eListe.elementer.add(Element(eListe.AREA, area, "", averageAqi, stationList))
            }

            launch {
                // Update the database when the list of elements has been added.
                // Necessary to synchronise observations that depend on the element list.
                // We're all going to hell for this.

                favoriteStationModel.favorite("")
            }

            adapter.notifyDataSetChanged()
        }

        // Keep list adapter view in sync with set of favorites.

        favoriteStationModel.favorites.observe({ lifecycle }) { favoriteSet ->
            for (element in eListe.elementer) {
                element.favorite = (favoriteSet ?: hashSetOf()).contains(element.name!!)
            }

            adapter.notifyDataSetChanged()
        }
    }

    //Old functions versions in Activity-version
    /*fun moreInfo(view: View){
        val intent = Intent(applicationContext, ListFragment::class.java)
        startActivity(intent)
    }

    fun changeStar(view: View){
        if(view.an_element_star.drawable.constantState == getDrawable(R.drawable.star_full).constantState){
            view.an_element_star.setImageResource(R.drawable.star_shell)
        }else{
            view.an_element_star.setImageResource(R.drawable.star_full)
        }

    }*/
}
