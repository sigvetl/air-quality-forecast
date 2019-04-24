package no.uio.ifi.in2000.gruppe55

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.coroutines.launch
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint


class InfoActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var stationName = "NO0057A"
    private var stationRef = "2019-04-16T01:00:00Z"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        linearLayoutManager = LinearLayoutManager(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        my_recycler_view.layoutManager = layoutManager

        launch {
            val stationModel = Airqualityforecast.met(stationRef, stationName)
            var number = stationModel.data?.time?.size
            if(number == null) number = 0
            for(i in 0..number){
                if(stationModel.data!!.time!![i].from == stationRef) {
                    val temp: AirQualityVariableDataModel? = stationModel.data.time?.get(i)?.variables
                    //TODO endre toString til riktig navn
                    SListe.values.add(Value(temp?.aqi.toString(), temp?.aqi?.value, temp?.aqi?.units))
                    SListe.values.add(Value(temp?.aqiNo2.toString(), temp?.aqiNo2?.value, temp?.aqiNo2?.units))
                    SListe.values.add(Value(temp?.aqiO3.toString(), temp?.aqiO3?.value, temp?.aqiO3?.units))
                    SListe.values.add(Value(temp?.aqiPm10.toString(), temp?.aqiPm10?.value, temp?.aqiPm10?.units))
                    SListe.values.add(Value(temp?.aqiPm25.toString(), temp?.aqiPm25?.value, temp?.aqiPm25?.units))
                    SListe.values.add(Value(temp?.no2Concentration.toString(), temp?.no2Concentration?.value, temp?.no2Concentration?.units))
                    SListe.values.add(Value(temp?.no2LocalFractionHeating.toString(), temp?.no2LocalFractionHeating?.value, temp?.no2LocalFractionHeating?.units))
                    SListe.values.add(Value(temp?.no2LocalFractionIndustry.toString(), temp?.no2LocalFractionIndustry?.value, temp?.no2LocalFractionIndustry?.units))
                    SListe.values.add(Value(temp?.no2LocalFractionShipping.toString(), temp?.no2LocalFractionShipping?.value, temp?.no2LocalFractionShipping?.units))
                    SListe.values.add(Value(temp?.no2LocalFractionTrafficExhaust.toString(), temp?.no2LocalFractionTrafficExhaust?.value, temp?.no2LocalFractionTrafficExhaust?.units))
                    SListe.values.add(Value(temp?.no2LocalFractionTrafficNonexhaust.toString(), temp?.no2LocalFractionTrafficNonexhaust?.value, temp?.no2LocalFractionTrafficNonexhaust?.units))
                    SListe.values.add(Value(temp?.no2NonlocalFraction.toString(), temp?.no2NonlocalFraction?.value, temp?.no2NonlocalFraction?.units))
                    SListe.values.add(Value(temp?.o3Concentration.toString(), temp?.o3Concentration?.value, temp?.o3Concentration?.units))
                    SListe.values.add(Value(temp?.o3LocalFractionHeating.toString(), temp?.o3LocalFractionHeating?.value, temp?.o3LocalFractionHeating?.units))
                    SListe.values.add(Value(temp?.o3LocalFractionIndustry.toString(), temp?.o3LocalFractionIndustry?.value, temp?.o3LocalFractionIndustry?.units))
                    SListe.values.add(Value(temp?.o3LocalFractionShipping.toString(), temp?.o3LocalFractionShipping?.value, temp?.o3LocalFractionShipping?.units))
                    SListe.values.add(Value(temp?.o3LocalFractionTrafficExhaust.toString(), temp?.o3LocalFractionTrafficExhaust?.value, temp?.o3LocalFractionTrafficExhaust?.units))
                    SListe.values.add(Value(temp?.o3LocalFractionTrafficNonexhaust.toString(), temp?.o3LocalFractionTrafficNonexhaust?.value, temp?.o3LocalFractionTrafficNonexhaust?.units))
                    SListe.values.add(Value(temp?.o3NonlocalFraction.toString(), temp?.o3NonlocalFraction?.value, temp?.o3NonlocalFraction?.units))
                    SListe.values.add(Value(temp?.pm10Concentration.toString(), temp?.pm10Concentration?.value, temp?.pm10Concentration?.units))
                    SListe.values.add(Value(temp?.pm10LocalFractionHeating.toString(), temp?.pm10LocalFractionHeating?.value, temp?.pm10LocalFractionHeating?.units))
                    SListe.values.add(Value(temp?.pm10LocalFractionIndustry.toString(), temp?.pm10LocalFractionIndustry?.value, temp?.pm10LocalFractionIndustry?.units))
                    SListe.values.add(Value(temp?.pm10LocalFractionShipping.toString(), temp?.pm10LocalFractionShipping?.value, temp?.pm10LocalFractionShipping?.units))
                    SListe.values.add(Value(temp?.pm10LocalFractionTrafficExhaust.toString(), temp?.pm10LocalFractionTrafficExhaust?.value, temp?.pm10LocalFractionTrafficExhaust?.units))
                    SListe.values.add(Value(temp?.pm10LocalFractionTrafficNonexhaust.toString(), temp?.pm10LocalFractionTrafficNonexhaust?.value, temp?.pm10LocalFractionTrafficNonexhaust?.units))
                    SListe.values.add(Value(temp?.pm10NonlocalFraction.toString(), temp?.pm10NonlocalFraction?.value, temp?.pm10NonlocalFraction?.units))
                    SListe.values.add(Value(temp?.pm25Concentration.toString(), temp?.pm25Concentration?.value, temp?.pm25Concentration?.units))
                    SListe.values.add(Value(temp?.pm25LocalFractionHeating.toString(), temp?.pm25LocalFractionHeating?.value, temp?.pm25LocalFractionHeating?.units))
                    SListe.values.add(Value(temp?.pm25LocalFractionIndustry.toString(), temp?.pm25LocalFractionIndustry?.value, temp?.pm25LocalFractionIndustry?.units))
                    SListe.values.add(Value(temp?.pm25LocalFractionShipping.toString(), temp?.pm25LocalFractionShipping?.value, temp?.pm25LocalFractionShipping?.units))
                    SListe.values.add(Value(temp?.pm25NonlocalFraction.toString(), temp?.pm25NonlocalFraction?.value, temp?.pm25NonlocalFraction?.units))
                    SListe.values.add(Value(temp?.pm25LocalFractionTrafficNonexhaust.toString(), temp?.pm25LocalFractionTrafficNonexhaust?.value, temp?.pm25LocalFractionTrafficNonexhaust?.units))
                    SListe.values.add(Value(temp?.pm25LocalFractionTrafficExhaust.toString(), temp?.pm25LocalFractionTrafficExhaust?.value, temp?.pm25LocalFractionTrafficExhaust?.units))
                }
                break
            }
        }

        val adapter = ListAdapter(this, SListe.values)
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