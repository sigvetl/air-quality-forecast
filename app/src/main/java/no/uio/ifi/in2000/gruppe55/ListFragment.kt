package no.uio.ifi.in2000.gruppe55

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.element.view.*
import kotlinx.coroutines.launch

class ListFragment : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_list)
        linearLayoutManager = LinearLayoutManager(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        my_recycler_view.layoutManager = layoutManager

        launch {
            val stations = Airqualityforecast.stations()
            for (station in stations) {
                eListe.elementer.add(Element(station.name, station.height))
            }
        }

        val adapter = ListAdapter(this, eListe.elementer)
        my_recycler_view.adapter = adapter


        }
    fun moreInfo(view: View){
        val intent = Intent(applicationContext, ListFragment::class.java)
        startActivity(intent)
    }

    fun changeStar(view: View){
        if(view.an_element_star.drawable.constantState == getDrawable(R.drawable.star_full).constantState){
            view.an_element_star.setImageResource(R.drawable.star_shell)
        }else{
            view.an_element_star.setImageResource(R.drawable.star_full)
        }

    }
}
