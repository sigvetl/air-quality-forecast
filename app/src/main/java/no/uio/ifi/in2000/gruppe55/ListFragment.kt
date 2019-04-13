package no.uio.ifi.in2000.gruppe55

import android.annotation.TargetApi
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.*
import no.uio.ifi.in2000.gruppe55.viewmodel.airqualityforecastModel
import java.util.*
import kotlin.Comparator

class ListFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    @TargetApi(24)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        linearLayoutManager = LinearLayoutManager(context)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        my_recycler_view.layoutManager = layoutManager

        val adapter = ListAdapter(context, eListe.elementer)
        my_recycler_view.adapter = adapter

        airqualityforecastModel.stations.observe(
            { this.lifecycle },
            { stationMap ->
                eListe.elementer.clear()

                // TODO (julianho): Comparator should handle null gracefully.
                val comparator: Comparator<StationModel> = Comparator { s, t -> s.name!!.compareTo(t.name!!) }

                for ((station, location) in stationMap?.toSortedMap(comparator) ?: TreeMap()) {
                    // TODO (julianho): Extract variables from the current time, not the first in the day.
                    val aqi = location?.data?.time?.first()?.variables?.aqi?.value
                    eListe.elementer.add(Element(station.name, aqi))
                }

                adapter.notifyDataSetChanged()
            }
        )
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
