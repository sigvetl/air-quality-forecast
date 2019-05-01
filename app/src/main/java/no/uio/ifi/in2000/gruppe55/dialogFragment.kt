package no.uio.ifi.in2000.gruppe55


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [dialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class dialogFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var text1: String? = null
    private var text2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            text1 = it.getString(ARG_PARAM1)
            text2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val t1 = view!!.findViewById<TextView>(R.id.textView_dialog)
        val t2 = view!!.findViewById<TextView>(R.id.textView_dialog2)
        val b1 = view!!.findViewById<Button>(R.id.button_dismiss)

        t1.text = ARG_PARAM1
        t2.text = ARG_PARAM2

        b1.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param text1 Parameter 1.
         * @param text2 Parameter 2.
         * @return A new instance of fragment dialogFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(text1: String?, text2: String?) =
            dialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, text1)
                    putString(ARG_PARAM2, text2)
                }
            }
    }
}
