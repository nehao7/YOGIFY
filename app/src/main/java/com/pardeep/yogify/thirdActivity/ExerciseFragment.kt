package com.pardeep.yogify.thirdActivity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentExerciseBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentExerciseBinding?=null
    var levelAdp = LevelAdaptor()
    var thirtyMinAdp = ThirtyMinAdaptor()
    var musicMeditationAdp = MusicMeditationAdp()
    lateinit var linearLayoutManager: LinearLayoutManager
    val imageList = ArrayList<SlideModel>()
    lateinit var linearSnapHelper: LinearSnapHelper
    private val TAG = "ExerciseFragment"

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
        binding = FragmentExerciseBinding.inflate(layoutInflater)
        return binding?.root
        //return inflater.inflate(R.layout.fragment_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyclerView1?.adapter = levelAdp
        linearLayoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
        binding?.recyclerView1?.layoutManager = linearLayoutManager

        // linear snap helper used for center snaping
       linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(binding?.recyclerView1)

        // Post a runnable to get the snap position after layout is complete
        binding?.recyclerView1?.post {
            val snapView = linearSnapHelper.findSnapView(linearLayoutManager)
            if (snapView != null) {
                val snapPosition = linearLayoutManager.getPosition(snapView)
                // ... use snapPosition here ...
            } else {
                Log.e(TAG, "onViewCreated: snapView is null ",)
            }
        }

        binding?.recyclerView2?.adapter = thirtyMinAdp
        linearLayoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)
        binding?.recyclerView2?.layoutManager = linearLayoutManager


        //image slide view
        imageList.add(SlideModel(R.drawable.standing_pose_bend, "Standing Pose" , scaleType = ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.standing_pose_bend, "Standing Pose" , scaleType = ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.standing_pose_bend, "Standing Pose" , scaleType = ScaleTypes.CENTER_CROP))


        binding?.imageSlider?.setImageList(imageList)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExerciseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExerciseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}