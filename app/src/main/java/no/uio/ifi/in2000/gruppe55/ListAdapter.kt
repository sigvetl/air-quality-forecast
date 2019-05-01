package no.uio.ifi.in2000.gruppe55
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.resources.MaterialResources.getDrawable
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.content.res.AppCompatResources.getDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.element_parent.view.*
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.gruppe55.viewmodel.FavoriteStationModel

class ListAdapter(
    val context: Context?,
    val elements: MutableList<Element>,
    val favoriteStationModel: FavoriteStationModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //Fargepalett:
    //1. Farger: Lilla: #8e3c97, Rød: #ea3e35, Oransje: #f68614, Gul: #dfc420.
    //2. Gråtoner: 5: 333333, 4: 555555, 3: 777777, 2: 999999, 1: bbbbbb.
    //3. Fargeblind-modus: 5: #63230e, 4: #571392, 3: #2f20d4, 2: #2063d4, 1: #20d0d4.
    //Obs, jobb videre med Fargeblind fargene.
    val PARENT = 0
    val CHILD = 1

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView = itemView.element_text
        var aqiIcon = itemView.element_aqi
        var expandButton = itemView.element_expand
        var setColor = itemView.element_color
        var setStar = itemView.element_star
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var view: View? = null

        when (viewType){
            PARENT -> view = inflater.inflate(R.layout.element_parent, parent, false)
            CHILD -> view = inflater.inflate(R.layout.element_child, parent, false)
        }

        return MyViewHolder(view!!)
    }


    override fun getItemCount(): Int {
        return elements.size
    }

    override fun getItemViewType(position: Int): Int = elements[position].type

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val elementHolder = holder as? MyViewHolder
        val element = elements[position]

        elementHolder?.let{

            it.setStar?.let { imageView ->
                val icon = if (element.favorite) { R.drawable.star_full } else { R.drawable.star_shell }

                imageView.setImageResource(icon)

                imageView.setOnClickListener {
                    launch {
                        if (!element.favorite) {
                            favoriteStationModel.favorite(element.name!!)
                        } else {
                            favoriteStationModel.defavorite(element.name!!)
                        }
                    }
                }
            }

            it.expandButton?.let { imageView ->
                if(element.children != null){
                    imageView.setImageResource(R.drawable.expand_more)
                    imageView.setOnClickListener { view ->
                        val start = elements.indexOf(element) + 1
                        if(element.children == null){
                            var count = 0
                            var nextHeader = elements.indexOf(elements.find{ an_element ->
                                (count++ >=start) && (an_element.type == element.type)
                            })
                            if(nextHeader == -1) nextHeader = elements.size
                            element.children = elements.slice(start until nextHeader)

                            val end = element.children!!.size
                            if(end > 0) elements.removeAll(element.children!!)

                            view.animate().rotation(0.0F).start()
                            notifyItemRangeRemoved(start, end)
                        } else {
                            element.children?.let { list ->
                                elements.addAll(start, list)
                                view.animate().rotation(180.0F).start()
                                notifyItemRangeInserted(start, list.size)
                                element.children = null
                            }
                        }
                    }
                }
            }

            it.aqiIcon.let{ imageView ->
                when {
                    element.aqi == null -> {
                        imageView.setImageResource(R.drawable.aqi_loading)
                    }
                    element.aqi!! >= 4 -> {
                        imageView.setImageResource(R.drawable.aqi_sh)
                    }
                    element.aqi!! >= 3 -> {
                        imageView.setImageResource(R.drawable.aqi_h)
                    }
                    element.aqi!! >= 2 -> {
                        imageView.setImageResource(R.drawable.aqi_m)
                    }
                    element.aqi!! >= 1 -> {
                        imageView.setImageResource(R.drawable.aqi_l)
                    }
                }
            }
            it.setColor.let{ view ->
                when {
                    element.aqi == null -> {
                        view.setBackgroundColor(Color.parseColor("#bbbbbb"))
                    }
                    element.aqi!! >= 4 -> {
                        view.setBackgroundColor(Color.parseColor("#8e3c97"))
                    }
                    element.aqi!! >= 3 -> {
                        view.setBackgroundColor(Color.parseColor("#ea3e35"))
                    }
                    element.aqi!! >= 2 -> {
                        view.setBackgroundColor(Color.parseColor("#dfc420"))
                    }
                    element.aqi!! >= 1 -> {
                        view.setBackgroundColor(Color.parseColor("#0ab03f"))
                    }
                }
            }
            it.textView.text = element.name

            if(element.children == null){
                it.setColor.let { view ->
                    view.setOnClickListener {
                        val bundle = Bundle().apply {
                            putString("stationId", element.stationId)
                            putString("stationName", element.name)
                        }
                        view!!.findNavController().navigate(R.id.action_listFragment_to_infoFragment, bundle)
                    }
                }
            }
        }
    }
}