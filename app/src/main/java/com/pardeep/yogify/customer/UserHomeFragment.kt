package com.pardeep.yogify.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.pardeep.yogify.R
import com.pardeep.yogify.admin.LevelAdaptor
import com.pardeep.yogify.admin.LevelData
import com.pardeep.yogify.admin.RecyclerInterface
import com.pardeep.yogify.databinding.FragmentUserHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserHomeFragment : Fragment(), RecyclerInterface {
    lateinit var binding:FragmentUserHomeBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var navController: NavController
    var levelData = arrayListOf<LevelData>()
    lateinit var levelLinearLayoutManager: LinearLayoutManager
    var levelAdp = LevelAdaptor(levelData, this)
    val imageList = ArrayList<SlideModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        navController = findNavController()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentUserHomeBinding.inflate(layoutInflater)        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        levelData.add(LevelData(image = R.drawable.beginner_pose, title = "Beginner"))
        levelData.add(LevelData(image = R.drawable.intermediate_pose_imae, title = "Intermediate"))
        levelData.add(LevelData(image = R.drawable.advanced_pose_image, title = "Advance"))
        binding.recyclerView1.adapter = levelAdp
        levelLinearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView1.layoutManager = levelLinearLayoutManager
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int, callFrom: String) {
        when (callFrom) {
            "LevelAdaptor" -> {
                Toast.makeText(requireContext(), "LevelAdaptorClick", Toast.LENGTH_SHORT).show()
                navController.navigate(
                    R.id.userLevelFilteredExrFragment, bundleOf(
                        "level" to position % levelData.size,
                        "title" to levelData[position % levelData.size].title,
                        "image" to levelData[position % levelData.size].image
                    )
                )

            }
        }
    }
}