package no.uio.ifi.in2000.gruppe55


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_blank.*


/**
 * A simple [Fragment] subclass.
 *
 */
class BlankFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val button = view!!.findViewById<Button>(R.id.button1)

        //TODO: Consider using SafeArgs instead of bundle
        val testBundle = Bundle().apply {
            putString("text1", "Hello")
            putString("text2", "World")
        }

        button.setOnClickListener {
            view!!.findNavController().navigate(R.id.action_homeFragment_to_dialogFragment, testBundle)
        }

        val tv = view!!.findViewById<TextView>(R.id.blank_text)
        tv.text = arguments!!.getString("argument")
    }
}
