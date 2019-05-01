package no.uio.ifi.in2000.gruppe55


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController


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


        //TODO: Consider using SafeArgs instead of bundle
        val testBundle = Bundle().apply {
            putString("Hello", "World")
        }

        val button = view!!.findViewById<Button>(R.id.button1)
        button.setOnClickListener {


            view!!.findNavController().navigate(R.id.action_homeFragment_to_dialogFragment, testBundle)

            //view!!.findNavController().navigate(R.id.action_homeFragment_to_blankFragment2, bundle)
        }
    }

}
