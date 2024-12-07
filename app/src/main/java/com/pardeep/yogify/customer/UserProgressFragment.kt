package com.pardeep.yogify.customer

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.firestore
import com.pardeep.yogify.Constants
import com.pardeep.yogify.R
import com.pardeep.yogify.admin.CalenderAdapter
import com.pardeep.yogify.admin.ClickType
import com.pardeep.yogify.DayDataClass
import com.pardeep.yogify.admin.RecyclerInterface
import com.pardeep.yogify.admin.adapters.ExerciseListAdapter
import com.pardeep.yogify.admin.clickInterface
import com.pardeep.yogify.admin.fragments.ExerciseListModel
import com.pardeep.yogify.databinding.AddExerciseDialogBinding
import com.pardeep.yogify.databinding.ExerciseListItemBinding
import com.pardeep.yogify.databinding.FragmentUserProgressBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserProgressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProgressFragment : Fragment() ,RecyclerInterface {
    lateinit var binding: FragmentUserProgressBinding
    var exerciseAdapter: ExerciseListAdapter?=null
    var categoriesList = arrayListOf<ExerciseListModel>()
    val db = Firebase.firestore
    var collectionName = Constants.exercises
    var imgCandle: ImageView? = null
    var dialogBinding : AddExerciseDialogBinding?=null
    var level=-1
    val weekDayDataClasses = mutableListOf<DayDataClass>()
    val calenderAdp = CalenderAdapter(weekDayDataClasses, this)
    lateinit var linearLayoutManager: LinearLayoutManager
    val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
    val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    lateinit var linearSnapHelper: LinearSnapHelper
    private  val TAG = "UserProgressFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            level=it.getInt("level",-1)
        }
        Log.d("TAG", "onCreatelvl:$level ")
        db.collection(collectionName).whereEqualTo("completed", true)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (snapshot in snapshots!!.documentChanges) {
                    val userModel = convertObject(snapshot.document)

                    when (snapshot.type) {
                        DocumentChange.Type.ADDED -> {
                            userModel?.let { categoriesList.add(it) }
                            Log.d("LevelFilteredExr", "Added: ${userModel?.exrId}")
                        }

                        DocumentChange.Type.MODIFIED -> {
                            userModel?.let {
                                val index = getIndex(it)
                                if (index > -1) categoriesList[index] = it
                            }
                        }

                        DocumentChange.Type.REMOVED -> {
                            userModel?.let {
                                val index = getIndex(it)
                                if (index > -1) categoriesList.removeAt(index)
                            }
                        }
                    }
                }
                // Notify adapter after data changes
                exerciseAdapter?.notifyDataSetChanged()
                Log.d("LevelFilteredExr", "Categories List: $categoriesList")
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentUserProgressBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exerciseAdapter = ExerciseListAdapter(requireContext(), categoriesList, firebaseAuth = FirebaseAuth.getInstance(), object :
            clickInterface {
            override fun onClick(position: Int, clickType: ClickType?): Boolean {
                when (clickType) {
                    ClickType.AddSub -> {
                    }

                    ClickType.OnViewClick -> {
//                        startActivity(
//                            Intent(adminActivity,MydrawingActivity::class.java)
//                            .putExtra("screen",1)
//                            .putExtra("image",categoriesList[position].drawingImgUri)
//                        )
//                        mainActivity.navController.navigate(
//                            R.id.newsListFragment,
//                            bundleOf(
//                                Constants.id to categoriesList[position].categoryId,
//                                "catName" to categoriesList[position].categoryName
//                            )
//                        )

                    }

                    ClickType.Delete -> {
                        AlertDialog.Builder(requireContext()).apply {
                            setTitle(resources.getString(R.string.delete_alert))
                            setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                                //deleting the particular collection from firestore
                                db.collection(collectionName)
                                    .document(categoriesList[position].exrId ?: "").delete()
                            }
                            setNegativeButton(resources.getString(R.string.no)) { _, _ -> }
                            show()
                        }
                    }

                    ClickType.Update -> {
//                        showAddExerciseDialog(position)
                    }

                    else -> {}
                }
                return true
            }

            override fun view(position: Int, imageView: ImageView) {
                imgCandle = imageView
                imageView?.let { it1 ->
                    Glide
                        .with(requireContext())
                        .load(Uri.parse(categoriesList[position].exrImgUri))
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(it1)
                }
//                imageView.setImageURI(Uri.parse(categoriesList[position].categoryImgUri))
            }

        })
        binding.completedExercise.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.completedExercise.adapter = exerciseAdapter


        getWeekDays()

        binding.userDayRecyclerView.adapter = calenderAdp
        linearLayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.userDayRecyclerView.layoutManager = linearLayoutManager

        linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(binding.userDayRecyclerView)
//        binding.fabAdd.setOnClickListener {
//            showAddExerciseDialog()
//        }
    }

        // ----------------------- getting the week of day ------------------------------------
        fun getWeekDays(): Pair<List<DayDataClass>, Int> {
            val calendar = Calendar.getInstance()
            val dayOfWeek = Calendar.DAY_OF_WEEK
            val firstDayOfWeek = calendar.firstDayOfWeek
            val currentDate = dateFormat.format(calendar.time)
            Log.d(TAG, "onViewCreated: $dayOfWeek , $firstDayOfWeek")
            Log.d(TAG, "onViewCreated: ${calendar.time}")
            var currentDatePosition = 0


            for (i in 0..6) {
                val date = dateFormat.format(calendar.time)
                val day = dayFormat.format(calendar.time).first().toString()
                weekDayDataClasses.add(DayDataClass(date, day))


                Log.d(TAG, "onViewCreated1: ${date} ${day}")

                if (date.equals(currentDate)) {
                    Log.d(TAG, "getWeekDays: 'Matched'")
                    currentDatePosition = i
                    Log.d(TAG, "currentDatePosition : $currentDatePosition")
                    binding?.userDayRecyclerView?.scrollToPosition(currentDatePosition)
                    binding?.userDayRecyclerView?.post {
                        onItemClick(currentDatePosition , "calenderAdapter")
                    }
                }
                calendar.add(Calendar.DAY_OF_WEEK, 1)
            }
            return Pair(weekDayDataClasses, currentDatePosition)
        }


    fun convertObject(snapshot: QueryDocumentSnapshot): ExerciseListModel {
        val exerciseListModel:ExerciseListModel =
            snapshot.toObject(ExerciseListModel::class.java)
        exerciseListModel.exrId = snapshot.id ?: ""
        return exerciseListModel
    }

    fun getIndex(exerciseListModel: ExerciseListModel): Int {
        var index = -1
        index = categoriesList.indexOfFirst { element ->
            element.exrId?.equals(exerciseListModel.exrId) == true
        }
        return index
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserProgressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserProgressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int, callFrom: String) {
        for (i in 0 until binding.userDayRecyclerView.childCount){
            val child = binding.userDayRecyclerView.getChildAt(i)
            child?.scaleX = 0.85f
            child?.scaleY = 0.85f
            child?.alpha = 0.85f
        }

        val clickedChild = binding.userDayRecyclerView.findViewHolderForAdapterPosition(position)?.itemView
        clickedChild?.scaleX = 1.1f
        clickedChild?.scaleY = 1.1f
        clickedChild?.alpha = 1.0f



    }
}