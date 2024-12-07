package com.pardeep.yogify.admin

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.firestore
import com.pardeep.yogify.Constants
import com.pardeep.yogify.R
import com.pardeep.yogify.admin.fragments.ExerciseListModel
import com.pardeep.yogify.customer.CustomerActivity
import com.pardeep.yogify.customer.UserExerciseListAdapter
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
class inner_day_fragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var customerActivity: CustomerActivity
    private var param1: String? = null
    private var param2: String? = null
    var totalDuration : Int =0
    var image: Int = 0
    var exerciseName : String? = ""
    var exerlevel : String? =""
    var duration : Int =0
    var itemArray = arrayListOf<ExerciseListModel>()
    var innerLevelAdaptor : InnerLevelAdaptor?=null
    val db = Firebase.firestore
    var collectionName = Constants.exercises
    var level = -1
    lateinit var linearLayoutManager: LinearLayoutManager
    var binding: FragmentInnerDayFragmentBinding? = null
    lateinit var navController: NavController
    var imgCandle : ImageView?= null
    private  val TAG = "inner_day_fragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            level = it.getInt("level" , -1)
            image = it.getInt("image")
            exerciseName = it.getString("title")
            exerlevel = it.getString("level")
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        customerActivity = activity as CustomerActivity
        if (level.equals(0)){
            customerActivity.supportActionBar?.title="Beginner"
        }else if (level.equals(1)){
            customerActivity.supportActionBar?.title="Intermediate"
        } else if (level.equals(2)){
            customerActivity.supportActionBar?.title="Advanced"
        }


    }

    private fun convertObject(snapshot: QueryDocumentSnapshot): ExerciseListModel {
        val exerciseListModel:ExerciseListModel =
            snapshot.toObject(ExerciseListModel::class.java)
        exerciseListModel.exrId = snapshot.id ?: ""
        return exerciseListModel
    }

    private fun getIndex(exerciseListModel: ExerciseListModel): Int {
        var index = -1
        index = itemArray.indexOfFirst { element ->
            element.exrId?.equals(exerciseListModel.exrId) == true
        }
        return index
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInnerDayFragmentBinding.inflate(layoutInflater)
        return binding?.root
        //return inflater.inflate(R.layout.fragment_inner_day_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db.collection(collectionName).whereEqualTo("level", level)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (snapshot in snapshots!!.documentChanges) {
                    val userModel = convertObject(snapshot.document)

                    when (snapshot.type) {
                        DocumentChange.Type.ADDED -> {
                            userModel?.let { itemArray.add(it) }
                            Log.d("LevelFilteredExr", "Added: ${userModel?.exrId}")
                            Log.d(TAG, "onViewCreated: count${itemArray.count()}")
                            binding?.packs?.setText("${itemArray.count()} packs")




                        }

                        DocumentChange.Type.MODIFIED -> {
                            userModel?.let {
                                val index = getIndex(it)
                                if (index > -1) {itemArray[index] = it
                                    binding?.packs?.setText("${itemArray.count()} packs")}

                            }
                        }

                        DocumentChange.Type.REMOVED -> {
                            userModel?.let {
                                val index = getIndex(it)
                                if (index > -1) {itemArray.removeAt(index)
                                    binding?.packs?.setText("${itemArray.count()} packs")}
                            }
                        }
                    }
                }
                Log.d(TAG, "count: ${itemArray.count()}")
                itemArray.forEachIndexed { index, exerciseListModel ->
                    totalDuration += itemArray[index].duration.toString().toInt()
                    Log.d(TAG, "onViewCreated: Duration :${itemArray[index].duration}")
                }
                binding?.totalDuration?.setText("${totalDuration} min")
                // Notify adapter after data changes
                innerLevelAdaptor?.notifyDataSetChanged()
                Log.d("LevelFilteredExr", "Categories List: $itemArray")
            }

        Log.d(TAG, "count: ${itemArray.count()}")
        itemArray.forEachIndexed { index, exerciseListModel ->
            totalDuration += itemArray[index].duration.toString().toInt()
            Log.d(TAG, "onViewCreated: Duration :${itemArray[index].duration}")
        }

        innerLevelAdaptor = InnerLevelAdaptor(requireContext(), itemArray, object :
            clickInterface {
            override fun onClick(position: Int, clickType: ClickType?): Boolean {
                when (clickType) {
                    ClickType.AddSub -> {
                    }

                    ClickType.OnViewClick -> {
                        customerActivity.navController.navigate(
                            R.id.userExrDetailsFragment,
                            bundleOf(
                                "imgUrl" to itemArray[position].exrImgUri,
                                "des" to itemArray[position].description,
                                "name" to itemArray[position].exrName,
                                "time" to itemArray[position].duration,
                                "id" to itemArray[position].exrId
                            )
                        )
//
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
                                    .document(itemArray[position].exrId ?: "").delete()
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
                        .load(Uri.parse(itemArray[position].exrImgUri))
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(it1)
                }
//                imageView.setImageURI(Uri.parse(categoriesList[position].categoryImgUri))
            }

        })
        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.recyclerView?.layoutManager = linearLayoutManager
        binding?.recyclerView?.adapter = innerLevelAdaptor
        binding?.imageView?.setImageResource(image)
        binding?.imageView?.cropToPadding
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
        fun newInstance(param1: String, param2: String) = inner_day_fragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        itemArray.clear()
        totalDuration =0
        innerLevelAdaptor?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        innerLevelAdaptor?.notifyDataSetChanged()

    }
}