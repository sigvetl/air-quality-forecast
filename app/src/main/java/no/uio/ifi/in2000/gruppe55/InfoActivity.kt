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
    private var stationRef = "2019-04-24T01:00:00Z"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        //REMOVE!!!!
        //val intent = Intent(this, InfoActivity::class.java)
        // To pass any data to next activity
        //intent.putExtra("keyIdentifier", value)
        // start your next activity
        //startActivity(intent)
        //stationName = arguments!!.getString("argument")
        linearLayoutManager = LinearLayoutManager(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        my_recycler_view.layoutManager = layoutManager

        val adapter = InfoAdapter(this, this, SListe.values)
        my_recycler_view.adapter = adapter

        launch {
            val stationModel = Airqualityforecast.main(station = stationName)
            var number = stationModel.data?.time?.size
            if(number == null) number = 0
            for(i in 0..number){
                if(stationModel.data!!.time!![i].from == stationRef) {
                    val temp: AirQualityVariableDataModel? = stationModel.data.time?.get(i)?.variables
                    //TODO endre toString til riktig navn
                    SListe.values.add(Value("aqi", "AQI", temp?.aqi?.value, temp?.aqi?.units))
                    SListe.values.add(Value("aqiNo2", "AQI", temp?.aqiNo2?.value, temp?.aqiNo2?.units))
                    SListe.values.add(Value("aqiO3", "AQI", temp?.aqiO3?.value, temp?.aqiO3?.units))
                    SListe.values.add(Value("aqiPm10", "AQI", temp?.aqiPm10?.value, temp?.aqiPm10?.units))
                    SListe.values.add(Value("aqiPm25", "AQI", temp?.aqiPm25?.value, temp?.aqiPm25?.units))
                    SListe.values.add(Value("no2Concentration", "NO2", temp?.no2Concentration?.value, temp?.no2Concentration?.units))
                    SListe.values.add(Value("no2LocalFractionHeating", "NO2", temp?.no2LocalFractionHeating?.value, temp?.no2LocalFractionHeating?.units))
                    SListe.values.add(Value("no2LocalFractionIndustry", "NO2", temp?.no2LocalFractionIndustry?.value, temp?.no2LocalFractionIndustry?.units))
                    SListe.values.add(Value("no2LocalFractionShipping", "NO2", temp?.no2LocalFractionShipping?.value, temp?.no2LocalFractionShipping?.units))
                    SListe.values.add(Value("no2LocalFractionTrafficExhaust", "NO2", temp?.no2LocalFractionTrafficExhaust?.value, temp?.no2LocalFractionTrafficExhaust?.units))
                    SListe.values.add(Value("no2LocalFractionTrafficNonexhaust", "NO2", temp?.no2LocalFractionTrafficNonexhaust?.value, temp?.no2LocalFractionTrafficNonexhaust?.units))
                    SListe.values.add(Value("no2NonlocalFraction", "NO2", temp?.no2NonlocalFraction?.value, temp?.no2NonlocalFraction?.units))
                    SListe.values.add(Value("o3Concentration", "Ozon", temp?.o3Concentration?.value, temp?.o3Concentration?.units))
                    SListe.values.add(Value("o3LocalFractionHeating", "Ozon", temp?.o3LocalFractionHeating?.value, temp?.o3LocalFractionHeating?.units))
                    SListe.values.add(Value("o3LocalFractionIndustry", "Ozon", temp?.o3LocalFractionIndustry?.value, temp?.o3LocalFractionIndustry?.units))
                    SListe.values.add(Value("o3LocalFractionShipping", "Ozon", temp?.o3LocalFractionShipping?.value, temp?.o3LocalFractionShipping?.units))
                    SListe.values.add(Value("o3LocalFractionTrafficExhaust", "Ozon", temp?.o3LocalFractionTrafficExhaust?.value, temp?.o3LocalFractionTrafficExhaust?.units))
                    SListe.values.add(Value("o3LocalFractionTrafficNonexhaust", "Ozon", temp?.o3LocalFractionTrafficNonexhaust?.value, temp?.o3LocalFractionTrafficNonexhaust?.units))
                    SListe.values.add(Value("o3NonlocalFraction", "Ozon", temp?.o3NonlocalFraction?.value, temp?.o3NonlocalFraction?.units))
                    SListe.values.add(Value("pm10Concentration", "PM10", temp?.pm10Concentration?.value, temp?.pm10Concentration?.units))
                    SListe.values.add(Value("pm10LocalFractionHeating", "PM10", temp?.pm10LocalFractionHeating?.value, temp?.pm10LocalFractionHeating?.units))
                    SListe.values.add(Value("pm10LocalFractionIndustry", "PM10", temp?.pm10LocalFractionIndustry?.value, temp?.pm10LocalFractionIndustry?.units))
                    SListe.values.add(Value("pm10LocalFractionShipping", "PM10", temp?.pm10LocalFractionShipping?.value, temp?.pm10LocalFractionShipping?.units))
                    SListe.values.add(Value("pm10LocalFractionTrafficExhaust", "PM10", temp?.pm10LocalFractionTrafficExhaust?.value, temp?.pm10LocalFractionTrafficExhaust?.units))
                    SListe.values.add(Value("pm10LocalFractionTrafficNonexhaust", "PM10", temp?.pm10LocalFractionTrafficNonexhaust?.value, temp?.pm10LocalFractionTrafficNonexhaust?.units))
                    SListe.values.add(Value("pm10NonlocalFraction", "PM10", temp?.pm10NonlocalFraction?.value, temp?.pm10NonlocalFraction?.units))
                    SListe.values.add(Value("pm25Concentration", "PM2.5", temp?.pm25Concentration?.value, temp?.pm25Concentration?.units))
                    SListe.values.add(Value("pm25LocalFractionHeating", "PM2.5", temp?.pm25LocalFractionHeating?.value, temp?.pm25LocalFractionHeating?.units))
                    SListe.values.add(Value("pm25LocalFractionIndustry", "PM2.5", temp?.pm25LocalFractionIndustry?.value, temp?.pm25LocalFractionIndustry?.units))
                    SListe.values.add(Value("pm25LocalFractionShipping", "PM2.5", temp?.pm25LocalFractionShipping?.value, temp?.pm25LocalFractionShipping?.units))
                    SListe.values.add(Value("pm25NonlocalFraction", "PM2.5", temp?.pm25NonlocalFraction?.value, temp?.pm25NonlocalFraction?.units))
                    SListe.values.add(Value("pm25LocalFractionTrafficNonexhaust", "PM2.5", temp?.pm25LocalFractionTrafficNonexhaust?.value, temp?.pm25LocalFractionTrafficNonexhaust?.units))
                    SListe.values.add(Value("pm25LocalFractionTrafficExhaust", "PM2.5", temp?.pm25LocalFractionTrafficExhaust?.value, temp?.pm25LocalFractionTrafficExhaust?.units))
                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                }
                break
            }
        }

        this.setGraph()
    }

    private fun setGraph(){
        val graph = findViewById<GraphView>(R.id.graph)
        val series = LineGraphSeries<DataPoint>(arrayOf<DataPoint>(
            DataPoint(0.0, 1.0),
            DataPoint(1.0, 5.0),
            DataPoint(2.0, 3.0)))
        graph.addSeries(series)
    }
}