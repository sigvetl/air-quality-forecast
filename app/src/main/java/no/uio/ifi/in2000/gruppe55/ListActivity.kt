package no.uio.ifi.in2000.gruppe55

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.edit_text_layout.*
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.gruppe55.Supplier.elements
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory



class ListActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        linearLayoutManager = LinearLayoutManager(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        my_recycler_view.layoutManager = layoutManager

        val adapter = ListAdapter(this, Supplier.elements)
        my_recycler_view.adapter = adapter

        launch {
            val stations = Airqualityforecast.stations()
            for (station in stations) {
                Supplier.elements.add(Element(station.name, station.name))
            }
        }

        fun onClick(view: View) {
            val itemPosition = my_recycler_view.getChildLayoutPosition(view)
            val item = elements[itemPosition]
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
                Supplier.elements.add(Element(text1, text2))
                my_recycler_view.adapter = ListAdapter(this, Supplier.elements)
            }

            myAlertDialog.show()
        }
    }

}
