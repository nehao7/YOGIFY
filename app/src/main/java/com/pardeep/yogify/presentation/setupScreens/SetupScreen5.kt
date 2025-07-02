package com.pardeep.yogify.presentation.setupScreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentSetupScreen5Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetupScreen5.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetupScreen5 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentSetupScreen5Binding?=null
    var selectValue = 0
    lateinit var navController: NavController
    val itemList = listOf("feet" , "centimeter")
    lateinit var setupActivity: SetupActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivity = activity as SetupActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetupScreen5Binding.inflate(layoutInflater)
        return binding?.root
       // return inflater.inflate(R.layout.fragment_setup_screen5, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.nextBtn?.setOnClickListener {
            navController = findNavController()
            navController.navigate(R.id.action_setupScreen5_to_setupScreen6)
            Toast.makeText(requireContext(), "$selectValue", Toast.LENGTH_SHORT).show()
            setupActivity.progressBarIncrement("SetupScreen5")

        }

        val spinnerAdapter = ArrayAdapter(requireContext() , android.R.layout.simple_list_item_1 , itemList)

        binding?.spinner?.adapter = spinnerAdapter

        binding?.heightScale?.setOnValueChangedListener { _, _, newVal ->
            selectValue = newVal
        }

        binding?.spinner?.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(requireContext(), "$id", Toast.LENGTH_SHORT).show()
                if (id.toInt() == 1){
                    binding?.heightScale?.minValue = 140
                    binding?.heightScale?.maxValue = 210
                }
                else{
                    binding?.heightScale?.minValue = 4.0.toInt()
                    binding?.heightScale?.maxValue = 7.0.toInt()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SetupScreen5.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetupScreen5().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}