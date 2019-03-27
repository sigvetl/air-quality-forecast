package no.uio.ifi.in2000.gruppe55

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.edit_text_layout.*
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.gruppe55.eListe.elementer

class ListActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        linearLayoutManager = LinearLayoutManager(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        my_recycler_view.layoutManager = layoutManager

        launch {
            val stations = Airqualityforecast.stations()
            for (station in stations) {
                eListe.elementer.add(Element(station.name, station.eoi))
            }
        }

        val adapter = ListAdapter(this, eListe.elementer)
        my_recycler_view.adapter = adapter


        fun onClick(view: View) {
            val itemPosition = my_recycler_view.getChildLayoutPosition(view)
            val item = elementer[itemPosition]
            //Toast.makeText(this, item, Toast.LENGTH_LONG).show()
        }


        val myFab = findViewById<FloatingActionButton>(R.id.fab)
        myFab.setOnClickListener{
            val myAlertDialog = AlertDialog.Builder(this).create()
            val editView = layoutInflater.inflate(R.layout.edit_text_layout, null)


            myAlertDialog.setView(editView)

            myAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
                val text1 = myAlertDialog.alert_dialog_edit_text1.text.toString()
                val text2 = myAlertDialog.alert_dialog_edit_text2.text.toString()
                eListe.elementer.add(Element(text1, text2))
                my_recycler_view.adapter = ListAdapter(this, eListe.elementer)
            }

            myAlertDialog.show()
        }
    }
}
