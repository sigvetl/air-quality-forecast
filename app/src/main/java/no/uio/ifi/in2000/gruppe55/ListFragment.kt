package no.uio.ifi.in2000.gruppe55

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.*
import no.uio.ifi.in2000.gruppe55.viewmodel.airqualityforecastModel

class ListFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

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

                for ((station, location) in stationMap ?: HashMap()) {
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
