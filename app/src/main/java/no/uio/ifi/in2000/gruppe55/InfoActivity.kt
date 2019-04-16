package no.uio.ifi.in2000.gruppe55

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.info_page.*
import kotlinx.coroutines.launch
import com.jjoe64.graphview.series.LineGraphSeries
import android.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint


class InfoActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_page)
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

        setGraph()
    }

    private fun setGraph(){
        val graph = findViewById(R.id.graph) as GraphView
        val series = LineGraphSeries<DataPoint>(arrayOf<DataPoint>(
            DataPoint(0.0, 1.0),
            DataPoint(1.0, 5.0),
            DataPoint(2.0, 3.0)))
        graph.addSeries(series)
    }
}