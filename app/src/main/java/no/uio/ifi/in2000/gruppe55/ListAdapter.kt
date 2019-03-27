package no.uio.ifi.in2000.gruppe55
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.element.view.*
import android.widget.Toast





class ListAdapter(val context: Context, val elements: List<Element>): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(element: Element?, pos: Int) {
            itemView.txvName.text = element!!.name
            itemView.txvDesc.text = element!!.desc
        }
    }


    private val mOnClickListener = MyOnClickListener()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.element, p0, false)
        view.setOnClickListener(mOnClickListener)
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