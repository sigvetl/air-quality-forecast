package no.uio.ifi.in2000.gruppe55


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
        var bundle = Bundle().apply {
            putString("pressedText", "From blank1 fragment")
        }

        val button = view!!.findViewById<Button>(R.id.button1)
        button.setOnClickListener {
            view!!.findNavController().navigate(R.id.action_homeFragment_to_dialogFragment)

            //view!!.findNavController().navigate(R.id.action_homeFragment_to_blankFragment2, bundle)
        }
    }

}
