package com.pardeep.yogify.thirdActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentInnerLevelBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InnerLevelFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InnerLevelFragment : Fragment() , RecyclerInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var levelName : String? = ""
    var dayArray = arrayListOf<String>("Day1", "Day2" , "Day3" ,"Day4" , "Day5" , )
    var dayRecyclerView = DayRecyclerView(dayArray , this)
    var binding : FragmentInnerLevelBinding?=null
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            levelName = it.getString("Level")
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInnerLevelBinding.inflate(layoutInflater)
        return binding?.root
       // return inflater.inflate(R.layout.fragment_inner_level, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        gridLayoutManager = GridLayoutManager(requireContext() , 3 , LinearLayoutManager.VERTICAL ,false)
        binding?.dayRecyclerView?.layoutManager = gridLayoutManager
        binding?.dayRecyclerView?.adapter = dayRecyclerView

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InnerLevelFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InnerLevelFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int, callFrom: String) {
        if (callFrom == "DayRecycler"){
            navController.navigate(R.id.inner_day_fragment)

        }
    }
}