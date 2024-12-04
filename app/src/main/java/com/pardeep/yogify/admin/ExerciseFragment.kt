package com.pardeep.yogify.admin

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pardeep.yogify.R
import com.pardeep.yogify.ThirdActivity
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
class ExerciseFragment : Fragment(), RecyclerInterface, CategoryRecyclerInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentExerciseBinding? = null
    var levelData = arrayListOf<LevelData>()
    var specificData = arrayListOf<SpecificData>()
    var categoryData = arrayListOf<CategoryData>()
    var levelAdp = LevelAdaptor(levelData, this)
    var thirtyMinAdp = SpecificCategory(specificData, this)
    var categoryAdapter = CategoryAdaptor(categoryData, this)
    var musicMeditationAdp = MusicMeditationAdp()
    lateinit var levelLinearLayoutManager: LinearLayoutManager
    lateinit var thirtyLinearLayoutManager: LinearLayoutManager
    lateinit var categoryLinearLayoutManager: LinearLayoutManager
    val imageList = ArrayList<SlideModel>()
    lateinit var linearSnapHelper: LinearSnapHelper
    private val TAG = "ExerciseFragment"
    lateinit var navController: NavController
    var thirdActivity: ThirdActivity? = null


    //------------------ dataBase Reference -------------------
    lateinit var categoryDatabaseReference: DatabaseReference

    //------------------ dataBase Reference -------------------


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


        //---------------init the dataBaseReference-----------------
        categoryDatabaseReference = FirebaseDatabase.getInstance().getReference("Category")
        //---------------init the dataBaseReference-----------------

        //------------------ category add in firebase ---------------

        //--------------------------------CategoryView Recycler view-----------------------------------------

        binding?.CategoryAdd?.setOnClickListener {
            Dialog(requireContext()).apply {
                setContentView(R.layout.custom_category_add)
                window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                val categoryEt = findViewById<EditText>(R.id.categoryEt)
                val addBtn = findViewById<Button>(R.id.AddBtn)

                addBtn.setOnClickListener {
                    if (categoryEt.text.trim().isNullOrEmpty()) {
                        categoryEt.error = "Field are mandatory"
                    } else {
                        dismiss()
                        val categoryID = categoryDatabaseReference.push().key
                        val categoryName = categoryEt.text.toString()
                        val newCategory = CategoryData(categoryID.toString(), categoryName)
                        categoryDatabaseReference.child(categoryID.toString()).setValue(newCategory)
                    }
                }
            }.show()
        }



        binding?.categoryRecyclerView?.adapter = categoryAdapter
        categoryLinearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.categoryRecyclerView?.layoutManager = categoryLinearLayoutManager


        //---------------------- fireBase curd--------------
        categoryDatabaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val categoryDataArray: CategoryData? = snapshot.getValue(CategoryData::class.java)
                categoryDataArray?.id = snapshot.key
                Log.d(TAG, "onChildAdded: ${snapshot.value}")

                if (categoryDataArray != null) {
                    categoryData.add(categoryDataArray)
                    categoryAdapter.notifyDataSetChanged()
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val categoryDataArray : CategoryData?=snapshot.getValue(CategoryData::class.java)
                categoryDataArray?.id = snapshot.key
                if (categoryDataArray!=null){
                    categoryData.forEachIndexed { index, categoryDataModel ->
                        if (categoryDataModel.id == categoryDataArray.id){
                            categoryData[index] = categoryDataArray
                            categoryAdapter.notifyDataSetChanged()
                        }
                    }
                }


            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val categoryDataArray : CategoryData?=snapshot.getValue(CategoryData::class.java)
                categoryDataArray?.id = snapshot.key
                if (categoryDataArray != null){
                    categoryData.remove(categoryDataArray)
                    categoryAdapter.notifyDataSetChanged()
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //---------------------- fireBase curd--------------


        //--------------------------------CategoryView Recycler view-----------------------------------------
        //------------------ category add in firebase ---------------


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

        levelData.add(LevelData(image = R.drawable.beginner_pose, title = "Beginner"))
        levelData.add(LevelData(image = R.drawable.intermediate_pose_imae, title = "Intermediate"))
        levelData.add(LevelData(image = R.drawable.advanced_pose_image, title = "Advance"))
        binding?.recyclerView1?.adapter = levelAdp
        levelLinearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.recyclerView1?.layoutManager = levelLinearLayoutManager


        // linear snap helper used for center snapping
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
            linearSnapHelper.findSnapView(levelLinearLayoutManager)?.let {
                val snapDistance = linearSnapHelper.calculateDistanceToFinalSnap(
                    levelLinearLayoutManager,
                    it
                )
                if (snapDistance != null) {
                    binding?.recyclerView1?.smoothScrollBy(snapDistance[0], snapDistance[1])
                }

                //--------------------------------------Recycler view 1----------------------------------------------


                //------------------------------- Specific Recycler view-----------------------------------------

                specificData.add(
                    SpecificData(
                        title = "Exercise 1",
                        image = R.drawable.standing_pose_bend
                    )
                )
                specificData.add(
                    SpecificData(
                        title = "Exercise 2",
                        image = R.drawable.standing_pose_bend
                    )
                )
                specificData.add(
                    SpecificData(
                        title = "Exercise 3",
                        image = R.drawable.standing_pose_bend
                    )
                )
                specificData.add(
                    SpecificData(
                        title = "Exercise 4",
                        image = R.drawable.standing_pose_bend
                    )
                )



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
        when (callFrom) {
            "LevelAdaptor" -> {
                Toast.makeText(requireContext(), "LevelAdaptorClick", Toast.LENGTH_SHORT).show()
                navController.navigate(
                    R.id.innerLevelFragment, bundleOf(
                        "Level" to position % levelData.size,
                        "title" to levelData[position % levelData.size].title,
                        "image" to levelData[position % levelData.size].image
                    )
                )

            }

            "categoryAdaptor" -> {
                Toast.makeText(requireContext(), "categoryAdaptorClick", Toast.LENGTH_SHORT).show()
                Toast.makeText(requireContext(), "${position}", Toast.LENGTH_SHORT).show()
                when(position){
                    0 -> {
                        binding?.recyclerView2?.adapter = thirtyMinAdp
                        thirtyLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        binding?.recyclerView2?.layoutManager = thirtyLinearLayoutManager

                        animateView(position)

                    }
                    1-> {
                        binding?.recyclerView2?.adapter = null
                        thirtyLinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        binding?.recyclerView2?.layoutManager = thirtyLinearLayoutManager

                        animateView(position)
                    }
                }

            }

            "specificAdaptor" -> {
                Toast.makeText(requireContext(), "specificAdaptor click", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.selectiveExerciseFragment , bundleOf(
                    "title" to specificData[position].title,
                    "image1" to specificData[position].image,
                    "image2" to specificData[position].image,
                    "image3" to specificData[position].image,
                    "image4" to specificData[position].image,
                ))

            }
        }
    }

    private fun animateView(position: Int) {
        for (i in 0 .. binding?.categoryRecyclerView?.childCount!!){
            val child = binding?.categoryRecyclerView?.getChildAt(i)
            child?.scaleX = 0.9f
            child?.scaleY = 0.9f
        }
        val clickedItem = binding?.categoryRecyclerView?.findViewHolderForAdapterPosition(position)?.itemView
        clickedItem?.scaleX = 1.1f
        clickedItem?.scaleY = 1.1f

    }

    override fun longClickListener(position: Int, callFrom: String) {
        when (callFrom) {
            "categoryAdaptor" -> {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("What do you want delete or update")
                    setPositiveButton("Delete") { _, _ ->
                        val categoryId = categoryData[position].id
                        categoryDatabaseReference.child(categoryId.toString()).removeValue()
                        categoryAdapter.notifyDataSetChanged()
                        Log.d(TAG, "longClickListener: ${categoryId}")
                    }

                    setNegativeButton("Update") { _, _ ->
                        Dialog(requireContext()).apply {
                            Dialog(requireContext()).apply {
                                setContentView(R.layout.custom_category_add)
                                window?.setLayout(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                                val categoryEt = findViewById<EditText>(R.id.categoryEt)
                                val categoryId = categoryData[position].id

                                val addBtn = findViewById<Button>(R.id.AddBtn)
                                addBtn.setText("update")
                                categoryEt.setText(categoryData[position].name)

                                addBtn.setOnClickListener {
                                    if (categoryEt.text.trim().isNullOrEmpty()) {
                                        categoryEt.error = "Field are mandatory"
                                    } else {
                                        dismiss()
                                        val newName = categoryEt.text.toString()
                                        val updateData = CategoryData(id = categoryData[position].id ,name = newName).toMap()
                                        categoryDatabaseReference.child(categoryId.toString()).updateChildren(updateData)
                                    }
                                }
                            }.show()
                        }

                    }
                }.show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        imageList.clear()
        levelData.clear()
        categoryData.clear()
        specificData.clear()

    }
}


