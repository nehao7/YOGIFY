package com.pardeep.yogify.thirdActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentInnerDayFragmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [inner_day_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class inner_day_fragment : Fragment() , RecyclerInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var innerLevelAdaptor = InnerLevelAdaptor(this)
    lateinit var linearLayoutManager: LinearLayoutManager
    var binding : FragmentInnerDayFragmentBinding?= null
    lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding = FragmentInnerDayFragmentBinding.inflate(layoutInflater)
        return binding?.root
        //return inflater.inflate(R.layout.fragment_inner_day_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
        binding?.recyclerView?.layoutManager = linearLayoutManager
        binding?.recyclerView?.adapter = innerLevelAdaptor
        navController = findNavController()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment inner_day_fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            inner_day_fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int, callFrom: String) {
        if (callFrom == "InnerLevel"){
            when(position){
                0-> navController.navigate(R.id.selectiveExerciseFragment)
                1-> navController.navigate(R.id.selectiveExerciseFragment)
                2-> navController.navigate(R.id.selectiveExerciseFragment)
                3-> navController.navigate(R.id.selectiveExerciseFragment)
            }


        }
    }
}