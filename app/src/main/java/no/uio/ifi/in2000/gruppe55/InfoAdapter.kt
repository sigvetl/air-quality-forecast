package no.uio.ifi.in2000.gruppe55
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.design.resources.MaterialResources.getDrawable
import android.support.v7.content.res.AppCompatResources.getDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.element_parent.view.*
import kotlinx.coroutines.launch

class InfoAdapter(val context: Context?, val values: MutableList<Value>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //Fargepalett:
    //1. Farger: Lilla: #8e3c97, Rød: #ea3e35, Oransje: #f68614, Gul: #dfc420.
    //2. Gråtoner: 5: 333333, 4: 555555, 3: 777777, 2: 999999, 1: bbbbbb.
    //3. Fargeblind-modus: 5: #63230e, 4: #571392, 3: #2f20d4, 2: #2063d4, 1: #20d0d4.
    //Obs, jobb videre med Fargeblind fargene.

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView = itemView.element_text
        var aqiIcon = itemView.element_aqi
        var setColor = itemView.element_color
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var view: View? = null

        view = inflater.inflate(R.layout.element_child, parent, false)
        return MyViewHolder(view!!)
    }


    override fun getItemCount(): Int {
        return values.size
    }

    //override fun getItemViewType(position: Int): Int = values[position].type

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val valueHolder = holder as? MyViewHolder
        val value = values[position]

        launch{
            val aqiDesc = Airqualityforecast.aqiDescription()
            when {
                value.nameClass == aqiDesc.variables?.o3Concentration?.nameno!! -> {
                    val aqiValues = aqiDesc.variables.o3Concentration.aqis as List<AQIIntervalModel>
                    setCard(valueHolder, aqiValues, value)
                }
                value.nameClass == aqiDesc.variables?.so2Concentration?.nameno!! -> {
                    val aqiValues = aqiDesc.variables.so2Concentration.aqis as List<AQIIntervalModel>
                    setCard(valueHolder, aqiValues, value)
                }
                value.nameClass == aqiDesc.variables?.pm25Concentration?.nameno!! ->{
                    val aqiValues = aqiDesc.variables.pm25Concentration.aqis as List<AQIIntervalModel>
                    setCard(valueHolder, aqiValues, value)
                }
                value.nameClass == aqiDesc.variables?.pm10Concentration?.nameno!! ->{
                    val aqiValues = aqiDesc.variables.pm10Concentration.aqis as List<AQIIntervalModel>
                    setCard(valueHolder, aqiValues, value)
                }
                value.nameClass == aqiDesc.variables?.no2Concentration?.nameno!! ->{
                    val aqiValues = aqiDesc.variables.no2Concentration.aqis as List<AQIIntervalModel>
                    setCard(valueHolder, aqiValues, value)
                }
                else -> {
                    //ERROR
                }
            }
        }


    }

    private fun setCard(valueHolder: InfoAdapter.MyViewHolder?, aqis: List<AQIIntervalModel>, value: Value){
        valueHolder?.let{
            it.aqiIcon.let{
                when {
                    //TODO riktig aqi nummer
                    value.value != null && value.value >= aqis[3].from!! -> {
                        it.setImageResource(R.drawable.aqi_5)
                    }
                    value.value != null && value.value >= aqis[2].from!! -> {
                        it.setImageResource(R.drawable.aqi_4)
                    }
                    value.value != null && value.value >= aqis[1].from!! -> {
                        it.setImageResource(R.drawable.aqi_3)
                    }
                    value.value != null && value.value >= 0 -> {
                        it.setImageResource(R.drawable.aqi_2)
                    }
                    else -> {
                        it.setImageResource(R.drawable.aqi_1)
                    }
                }
            }
            it.setColor.let{
                when {
                    value.value != null && value.value >= aqis[3].from!! -> {
                        it.setBackgroundColor(Color.parseColor("#8e3c97"))
                    }
                    value.value != null && value.value >= aqis[2].from!! -> {
                        it.setBackgroundColor(Color.parseColor("#ea3e35"))
                    }
                    value.value != null && value.value >= aqis[1].from!! -> {
                        it.setBackgroundColor(Color.parseColor("#dfc420"))
                    }
                    value.value != null && value.value >= 0 -> {
                        it.setBackgroundColor(Color.parseColor("#0ab03f"))
                    }
                    else -> {
                        it.setBackgroundColor(Color.parseColor("#bbbbbb"))
                    }
                }
            }
            it.textView.text = value.name
        }
    }
}
