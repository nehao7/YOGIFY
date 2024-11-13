package com.pardeep.yogify.thirdActivity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.pardeep.yogify.CustomLayout
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentExerciseBinding
import java.util.Locale.Category

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseFragment : Fragment() , RecyclerInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentExerciseBinding? = null
    var levelData = arrayListOf<LevelData>()
    var specificData = arrayListOf<SpecificData>()
    var categoryData = arrayListOf<CategoryData>()
    var levelAdp = LevelAdaptor(levelData,this)
    var thirtyMinAdp = SpecificCategory(specificData , this)
    var categoryAdapter = CategoryAdaptor(categoryData,this)
    var musicMeditationAdp = MusicMeditationAdp()
    lateinit var linearLayoutManager: LinearLayoutManager
    val imageList = ArrayList<SlideModel>()
    lateinit var linearSnapHelper: LinearSnapHelper
    private val TAG = "ExerciseFragment"
    lateinit var navController: NavController
    var thirdActivity: ThirdActivity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thirdActivity = activity as ThirdActivity
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
        navController = findNavController()

        //---------------------------------------searchBar-------------------------------------
        binding?.searchBar?.setOnClickListener {
            binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
            })
        }

        //---------------------------------------searchBar-------------------------------------


//--------------------------------------Recycler view 1----------------------------------------------

        levelData.add(LevelData(image = R.drawable.beginner_pose , title = "Beginner"))
        levelData.add(LevelData(image = R.drawable.intermediate_pose_imae , title = "Intermediate"))
        levelData.add(LevelData(image = R.drawable.advanced_pose_image , title = "Advance"))
        binding?.recyclerView1?.adapter = levelAdp
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.recyclerView1?.layoutManager = linearLayoutManager





        // linear snap helper used for center snaping
        linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(binding?.recyclerView1)

        // Set initial position to the middle to allow scrolling in both directions
        val initialPosition = Int.MAX_VALUE / 2
        binding?.recyclerView1?.scrollToPosition(initialPosition - initialPosition % levelData.size)

        binding?.recyclerView1?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                for (i in 0 until recyclerView.childCount) {
                    val child = recyclerView.getChildAt(i)
                    val childCenter = (child.left + child.right) / 2.0f
                    val center = recyclerView.width / 2.0f
                    val d = Math.min(1.0f, Math.abs(center - childCenter) / center)
                    val scale = 1 - d * 0.3f
                    child.scaleX = scale
                    child.scaleY = scale
                    child.alpha = scale
                }
            }
        })


        // Post a runnable to get the snap position after layout is complete
        binding?.recyclerView1?.post {
            linearSnapHelper.findSnapView(linearLayoutManager)?.let {
                val snapDistance = linearSnapHelper.calculateDistanceToFinalSnap(
                    linearLayoutManager,
                    it
                )
                if (snapDistance != null) {
                    binding?.recyclerView1?.smoothScrollBy(snapDistance[0], snapDistance[1])
                }

                //--------------------------------------Recycler view 1----------------------------------------------


                //--------------------------------CategoryView Recycler view-----------------------------------------

                categoryData.add(CategoryData("Arms"))
                categoryData.add(CategoryData("Arms" ))
                categoryData.add(CategoryData("Arms" ))
                categoryData.add(CategoryData("Arms" ))
                categoryData.add(CategoryData("Arms" ))
                categoryData.add(CategoryData("Arms" ))
                categoryData.add(CategoryData("Arms" ))
                categoryData.add(CategoryData("Arms" ))
                binding?.categoryRecyclerView?.adapter = categoryAdapter
                linearLayoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding?.categoryRecyclerView?.layoutManager = linearLayoutManager


                //--------------------------------CategoryView Recycler view-----------------------------------------

                //------------------------------- Specific Recycler view-----------------------------------------

                specificData.add(SpecificData(title = "Exercise 1" , image = R.drawable.standing_pose_bend))
                specificData.add(SpecificData(title = "Exercise 2" , image = R.drawable.standing_pose_bend))
                specificData.add(SpecificData(title = "Exercise 3" , image = R.drawable.standing_pose_bend))
                specificData.add(SpecificData(title = "Exercise 4" , image = R.drawable.standing_pose_bend))

                binding?.recyclerView2?.adapter = thirtyMinAdp
                linearLayoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding?.recyclerView2?.layoutManager = linearLayoutManager

                //--------------------------------specific Recycler view-----------------------------------------


                //---------------------------------image slide view------------------------------------------------
                imageList.add(
                    SlideModel(
                        R.drawable.standing_pose_bend,
                        "Standing Pose",
                        scaleType = ScaleTypes.CENTER_CROP
                    )
                )
                imageList.add(
                    SlideModel(
                        R.drawable.standing_pose_bend,
                        "Standing Pose",
                        scaleType = ScaleTypes.CENTER_CROP
                    )
                )
                imageList.add(
                    SlideModel(
                        R.drawable.standing_pose_bend,
                        "Standing Pose",
                        scaleType = ScaleTypes.CENTER_CROP
                    )
                )


                binding?.imageSlider?.setImageList(imageList)
            }
        }
    }

    //---------------------------------image slide view------------------------------------------------


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


    override fun onItemClick(position: Int, callFrom: String) {
        when(callFrom){
            "LevelAdaptor" -> {
                Toast.makeText(requireContext(), "LevelAdaptorClick", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.innerLevelFragment , bundleOf("Level" to position %levelData.size,
                    "title" to levelData[position%levelData.size].title))

            }
            "categoryAdaptor" -> Toast.makeText(requireContext(), "categoryAdaptorClick", Toast.LENGTH_SHORT).show()
            "specificAdaptor" -> Toast.makeText(requireContext(), "specificAdaptorclick", Toast.LENGTH_SHORT).show()
        }
    }
}