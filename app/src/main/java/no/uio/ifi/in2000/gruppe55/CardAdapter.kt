package no.uio.ifi.in2000.gruppe55

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import kotlinx.android.synthetic.main.card.view.*

class CardAdapter(val context: Context, val elementer: List<Element>) : RecyclerView.Adapter<CardAdapter.MyViewHolder>() {

    //Fargepalett:
    //1. Farger: Lilla: #8e3c97, Rød: #ea3e35, Oransje: #f68614, Gul: #dfc420.
    //2. Gråtoner: 5: 333333, 4: 555555, 3: 777777, 2: 999999, 1: bbbbbb.
    //3. Fargeblind-modus: 5: #63230e, 4: #571392, 3: #2f20d4, 2: #2063d4, 1: #20d0d4.
    //Obs, jobb videre med Fargeblind fargene.
    //Hvis du skal bytte layouten til elementene, husk å bytt xml filen i toppen.
    val farge = 1

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(element: Element?, pos: Int) {

            //Setting the text of the card
            itemView.an_element_text.text = element!!.tekst

            //Setting the AQI symbol and the color of the card
            if(farge == 1){
                when {
                    element.aqi >= 4 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_5)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#8e3c97"))
                    }
                    element.aqi >= 3 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_4)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#ea3e35"))
                    }
                    element.aqi >= 2 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_3)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#f68614"))
                    }
                    element.aqi >= 1 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_2)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#dfc420"))
                    }
                    element.aqi >= 0 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_1)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#0ab03f"))
                    }
                }
            }
            if(farge == 2){
                when {
                    element.aqi >= 4 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_5)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#333333"))
                    }
                    element.aqi >= 3 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_4)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#555555"))
                    }
                    element.aqi >= 2 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_3)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#777777"))
                    }
                    element.aqi >= 1 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_2)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#999999"))
                    }
                    element.aqi >= 0 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_2)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#bbbbbb"))
                    }
                }
            }
            if(farge == 3){
                when {
                    element.aqi >= 4 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_5)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#63230e"))
                    }
                    element.aqi >= 3 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_4)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#571392"))
                    }
                    element.aqi >= 2 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_3)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#2f20d4"))
                    }
                    element.aqi >= 1 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_2)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#2063d4"))
                    }
                    element.aqi >= 0 -> {
                        itemView.an_element_aqi.setImageResource(R.drawable.aqi_2)
                        itemView.an_element.setCardBackgroundColor(Color.parseColor("#20d0d4"))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card, parent, false)
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val element = elementer[position]
        holder.setData(element, position)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = elementer.size
}