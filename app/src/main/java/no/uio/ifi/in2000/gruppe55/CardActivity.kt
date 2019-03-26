package no.uio.ifi.in2000.gruppe55

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.card.view.*

class CardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        val mAdapter = CardAdapter(this, eListe.elementer)
        recyclerView.adapter = mAdapter
    }

    fun changeStar(view: View){
        if(view.an_element_star.drawable.constantState == getDrawable(R.drawable.star_full).constantState){
            view.an_element_star.setImageResource(R.drawable.star_shell)
        }else{
            view.an_element_star.setImageResource(R.drawable.star_full)
        }
    }
}
