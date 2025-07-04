package com.pardeep.yogify.presentation.setupScreens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentSetupScreen3Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetupScreen3.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetupScreen3 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentSetupScreen3Binding? = null
    lateinit var navController: NavController
    private  val TAG = "SetupScreen3"
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
        binding = FragmentSetupScreen3Binding.inflate(layoutInflater)
        return binding?.root
        //return inflater.inflate(R.layout.fragment_setup_screen3, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.nextBtn?.setOnClickListener {
            navController = findNavController()
            setupActivity.progressBarIncrement("SetupScreen3")
            navController.navigate(R.id.setupScreen5)
        }
        binding?.radioGroup?.setOnCheckedChangeListener{_, checkId ->
            when(checkId){
                R.id.radioBtn1 -> {
                    binding?.nextBtn?.isEnabled =true
                }

                R.id.radioBtn2 -> {
                    binding?.nextBtn?.isEnabled =true
                }
                else -> binding?.nextBtn?.isEnabled = false
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
         * @return A new instance of fragment SetupScreen3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetupScreen3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}