package no.uio.ifi.in2000.gruppe55

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.gruppe55.R.*

class InfoFragment: Fragment(){
    private lateinit var linearLayoutManager: LinearLayoutManager
    //private var stationName = "NO0057A"
    //private var stationRef = "2019-05-01T01:00:00Z"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout.activity_info, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val stationName = arguments!!.getString("argument")

        //val intent = Intent(this, InfoActivity::class.java)
        // To pass any data to next activity
        //intent.putExtra("keyIdentifier", value)
        // start your next activity
        //startActivity(intent)
        //stationName = arguments!!.getString("argument")
        linearLayoutManager = LinearLayoutManager(context)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        my_recycler_view.layoutManager = layoutManager

        val adapter = InfoAdapter(context, activity, SListe.values)
        my_recycler_view.adapter = adapter
        var tempList = mutableListOf<Double?>()


        launch {
            val description = Airqualityforecast.aqiDescription()
            val refTimes = Airqualityforecast.reftimes()
            val stationModel = Airqualityforecast.main(station = stationName)
            var number = stationModel.data?.time?.size
            var stationRef = refTimes.reftimes!![0]
            if(number == null) number = 0
            for(i in 0..number - 1){
                tempList.add(stationModel.data!!.time!![i].variables!!.aqi!!.value)
                if(stationModel.data!!.time!![i].from == stationRef) {
                    val temp: AirQualityVariableDataModel? = stationModel.data.time?.get(i)?.variables
                    //TODO endre toString til riktig navn

                    SListe.values.add(
                        Value(
                            "aqi",
                            "AQI",
                            temp?.aqi?.value,
                            temp?.aqi?.units,
                            description.variables?.aqi?.aqis!!
                        )
                    )
                    SListe.values.add(Value(
                        "aqiNo2",
                        "AQI",
                        temp?.aqiNo2?.value,
                        temp?.aqiNo2?.units,
                        description.variables?.aqi?.aqis
                    ))
                    SListe.values.add(Value(
                        "aqiO3",
                        "AQI",
                        temp?.aqiO3?.value,
                        temp?.aqiO3?.units,
                        description.variables?.aqi?.aqis
                    ))
                    SListe.values.add(Value(
                        "aqiPm10",
                        "AQI",
                        temp?.aqiPm10?.value,
                        temp?.aqiPm10?.units,
                        description.variables?.aqi?.aqis
                    ))
                    SListe.values.add(Value(
                        "aqiPm25",
                        "AQI",
                        temp?.aqiPm25?.value,
                        temp?.aqiPm25?.units,
                        description.variables?.aqi?.aqis
                    ))
                    SListe.values.add(Value(
                        "no2Concentration",
                        "NO2",
                        temp?.no2Concentration?.value,
                        temp?.no2Concentration?.units,
                        description.variables?.no2Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "no2LocalFractionHeating",
                        "NO2",
                        temp?.no2LocalFractionHeating?.value,
                        temp?.no2LocalFractionHeating?.units,
                        description.variables?.no2Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "no2LocalFractionIndustry",
                        "NO2",
                        temp?.no2LocalFractionIndustry?.value,
                        temp?.no2LocalFractionIndustry?.units,
                        description.variables?.no2Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "no2LocalFractionShipping",
                        "NO2",
                        temp?.no2LocalFractionShipping?.value,
                        temp?.no2LocalFractionShipping?.units,
                        description.variables?.no2Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "no2LocalFractionTrafficExhaust",
                        "NO2",
                        temp?.no2LocalFractionTrafficExhaust?.value,
                        temp?.no2LocalFractionTrafficExhaust?.units,
                        description.variables?.no2Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "no2LocalFractionTrafficNonexhaust",
                        "NO2",
                        temp?.no2LocalFractionTrafficNonexhaust?.value,
                        temp?.no2LocalFractionTrafficNonexhaust?.units,
                        description.variables?.no2Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "no2NonlocalFraction",
                        "NO2",
                        temp?.no2NonlocalFraction?.value,
                        temp?.no2NonlocalFraction?.units,
                        description.variables?.no2Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "o3Concentration",
                        "Ozon",
                        temp?.o3Concentration?.value,
                        temp?.o3Concentration?.units,
                        description.variables?.o3Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "o3LocalFractionHeating",
                        "Ozon",
                        temp?.o3LocalFractionHeating?.value,
                        temp?.o3LocalFractionHeating?.units,
                        description.variables?.o3Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "o3LocalFractionIndustry",
                        "Ozon",
                        temp?.o3LocalFractionIndustry?.value,
                        temp?.o3LocalFractionIndustry?.units,
                        description.variables?.o3Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "o3LocalFractionShipping",
                        "Ozon",
                        temp?.o3LocalFractionShipping?.value,
                        temp?.o3LocalFractionShipping?.units,
                        description.variables?.o3Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "o3LocalFractionTrafficExhaust",
                        "Ozon",
                        temp?.o3LocalFractionTrafficExhaust?.value,
                        temp?.o3LocalFractionTrafficExhaust?.units,
                        description.variables?.o3Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "o3LocalFractionTrafficNonexhaust",
                        "Ozon",
                        temp?.o3LocalFractionTrafficNonexhaust?.value,
                        temp?.o3LocalFractionTrafficNonexhaust?.units,
                        description.variables?.o3Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "o3NonlocalFraction",
                        "Ozon",
                        temp?.o3NonlocalFraction?.value,
                        temp?.o3NonlocalFraction?.units,
                        description.variables?.o3Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "pm10Concentration",
                        "PM10",
                        temp?.pm10Concentration?.value,
                        temp?.pm10Concentration?.units,
                        description.variables?.pm10Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "pm10LocalFractionHeating",
                        "PM10",
                        temp?.pm10LocalFractionHeating?.value,
                        temp?.pm10LocalFractionHeating?.units,
                        description.variables?.pm10Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "pm10LocalFractionIndustry",
                        "PM10",
                        temp?.pm10LocalFractionIndustry?.value,
                        temp?.pm10LocalFractionIndustry?.units,
                        description.variables?.pm10Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "pm10LocalFractionShipping",
                        "PM10",
                        temp?.pm10LocalFractionShipping?.value,
                        temp?.pm10LocalFractionShipping?.units,
                        description.variables?.pm10Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "pm10LocalFractionTrafficExhaust",
                        "PM10",
                        temp?.pm10LocalFractionTrafficExhaust?.value,
                        temp?.pm10LocalFractionTrafficExhaust?.units,
                        description.variables?.pm10Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "pm10LocalFractionTrafficNonexhaust",
                        "PM10",
                        temp?.pm10LocalFractionTrafficNonexhaust?.value,
                        temp?.pm10LocalFractionTrafficNonexhaust?.units,
                        description.variables?.pm10Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "pm10NonlocalFraction",
                        "PM10",
                        temp?.pm10NonlocalFraction?.value,
                        temp?.pm10NonlocalFraction?.units,
                        description.variables?.pm10Concentration?.aqis!!
                    ))
                    SListe.values.add(Value(
                        "pm25Concentration",
                        "PM2.5",
                        temp?.pm25Concentration?.value,
                        temp?.pm25Concentration?.units,
                        description.variables?.pm25Concentration?.aqis!!))
                    SListe.values.add(Value(
                        "pm25LocalFractionHeating",
                        "PM2.5",
                        temp?.pm25LocalFractionHeating?.value,
                        temp?.pm25LocalFractionHeating?.units,
                        description.variables?.pm25Concentration?.aqis!!))
                    SListe.values.add(Value(
                        "pm25LocalFractionIndustry",
                        "PM2.5",
                        temp?.pm25LocalFractionIndustry?.value,
                        temp?.pm25LocalFractionIndustry?.units,
                        description.variables?.pm25Concentration?.aqis!!))
                    SListe.values.add(Value(
                        "pm25LocalFractionShipping",
                        "PM2.5",
                        temp?.pm25LocalFractionShipping?.value,
                        temp?.pm25LocalFractionShipping?.units,
                        description.variables?.pm25Concentration?.aqis!!))
                    SListe.values.add(Value(
                        "pm25NonlocalFraction",
                        "PM2.5",
                        temp?.pm25NonlocalFraction?.value,
                        temp?.pm25NonlocalFraction?.units,
                        description.variables?.pm25Concentration?.aqis!!))
                    SListe.values.add(Value(
                        "pm25LocalFractionTrafficNonexhaust",
                        "PM2.5",
                        temp?.pm25LocalFractionTrafficNonexhaust?.value,
                        temp?.pm25LocalFractionTrafficNonexhaust?.units,
                        description.variables?.pm25Concentration?.aqis!!))
                    SListe.values.add(Value(
                        "pm25LocalFractionTrafficExhaust",
                        "PM2.5",
                        temp?.pm25LocalFractionTrafficExhaust?.value,
                        temp?.pm25LocalFractionTrafficExhaust?.units,
                        description.variables?.pm25Concentration?.aqis!!))

                    activity!!.runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            Log.d("test", "Liste str i launch: " + tempList.size)
            setGraph(tempList)
        }
        Log.d("test", "Liste str utenfor launch: " + tempList.size)
        //this.setGraph(tempList)
    }

    private fun setGraph(tempList: MutableList<Double?>){
        val graph = view!!.findViewById<GraphView>(R.id.graph)

        val series = LineGraphSeries<DataPoint>(arrayOf<DataPoint>(

            DataPoint(0.0 , tempList[0]!!),
            DataPoint(1.0, tempList[1]!!),
            DataPoint(2.0, tempList[2]!!),
            DataPoint(3.0, tempList[3]!!),
            DataPoint(4.0, tempList[4]!!),
            DataPoint(5.0, tempList[5]!!),
            DataPoint(6.0, tempList[6]!!),
            DataPoint(7.0, tempList[7]!!),
            DataPoint(8.0, tempList[8]!!),
            DataPoint(9.0, tempList[9]!!),
            DataPoint(10.0 ,tempList[10]!!),
            DataPoint(11.0, tempList[11]!!)
        ))
        graph.gridLabelRenderer.numHorizontalLabels = 12
        graph.addSeries(series)

    }
}
