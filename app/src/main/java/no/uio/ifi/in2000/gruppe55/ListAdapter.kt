package no.uio.ifi.in2000.gruppe55
import android.content.Context
import android.graphics.Color
import android.support.v7.content.res.AppCompatResources.getDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.element.view.*
import android.widget.Toast





class ListAdapter(val context: Context?, val elements: List<Element>): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
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
            itemView.an_element_text.text = element!!.name

            //Setting the AQI symbol and the color of the card

            if(element.aqi != null){
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
    }



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.element, p0, false)

        view.an_element_star.setOnClickListener {
            if (context != null) {
                if (view.an_element_star.drawable.constantState ==
                    getDrawable(context, R.drawable.star_full)?.constantState) {
                    view.an_element_star.setImageResource(R.drawable.star_shell)
                } else {
                    view.an_element_star.setImageResource(R.drawable.star_full)
                }
            }
        }

        view.an_element.setOnClickListener {

        }

        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return elements.size
    }

    override fun onBindViewHolder(p0: ListAdapter.MyViewHolder, p1: Int) {
        val element = elements[p1]
        p0.setData(element, p1)

    }
}