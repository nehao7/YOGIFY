package com.pardeep.yogify.presentation.setupScreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentSetupScreen1Binding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetupScreen_1.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetupScreen_1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentSetupScreen1Binding?= null
    lateinit var navController: NavController
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
        binding = FragmentSetupScreen1Binding.inflate(layoutInflater)
        return binding?.root
        //return inflater.inflate(R.layout.fragment_setup_screen_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.nextBtn?.setOnClickListener {
            navController  = findNavController()
            setupActivity.progressBarIncrement("SetupScreen1")
            navController.navigate(R.id.action_setupScreen_1_to_setupScreen2)

        }


        //checkbox condition to enable the next button
        val checkBoxListner = CompoundButton.OnCheckedChangeListener { _, _ ->
            binding?.nextBtn?.isEnabled = binding?.checkbox1?.isChecked !! || binding?.checkbox2?.isChecked!! || binding?.checkbox3?.isChecked!! ||
            binding?.checkbox4?.isChecked!! || binding?.checkbox5?.isChecked!! || binding?.checkbox6?.isChecked!! || binding?.checkbox7?.isChecked!!
        }

        binding?.checkbox1?.setOnCheckedChangeListener(checkBoxListner)
        binding?.checkbox2?.setOnCheckedChangeListener(checkBoxListner)
        binding?.checkbox3?.setOnCheckedChangeListener(checkBoxListner)
        binding?.checkbox4?.setOnCheckedChangeListener(checkBoxListner)
        binding?.checkbox5?.setOnCheckedChangeListener(checkBoxListner)
        binding?.checkbox6?.setOnCheckedChangeListener(checkBoxListner)
        binding?.checkbox7?.setOnCheckedChangeListener(checkBoxListner)



    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SetupScreen_1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetupScreen_1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}