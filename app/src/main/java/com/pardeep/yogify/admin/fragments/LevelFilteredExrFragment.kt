package com.pardeep.yogify.admin.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.firestore
import com.pardeep.yogify.Constants
import com.pardeep.yogify.R
import com.pardeep.yogify.ThirdActivity
import com.pardeep.yogify.admin.ClickType
import com.pardeep.yogify.admin.adapters.ExerciseListAdapter
import com.pardeep.yogify.admin.clickInterface
import com.pardeep.yogify.databinding.AddExerciseDialogBinding
import com.pardeep.yogify.databinding.FragmentAddExerciseBinding
import com.pardeep.yogify.databinding.FragmentLevelFilteredExrBinding
import io.github.jan.supabase.SupabaseClient


class LevelFilteredExrFragment : Fragment() {
    lateinit var binding:FragmentLevelFilteredExrBinding
    // TODO: Rename and change types of parameters
    lateinit var adminActivity: ThirdActivity
    lateinit var exerciseAdapter: ExerciseListAdapter
    var categoriesList = arrayListOf<ExerciseListModel>()
    val db = Firebase.firestore
    var collectionName = Constants.exercises

    var imgCandle: ImageView? = null
    lateinit var dialogBinding: AddExerciseDialogBinding
    var level=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            level=it.getInt("level",-1)
        }
        Log.d("TAG", "onCreatelvl:$level ")
        adminActivity=activity as ThirdActivity
        db.collection(collectionName).whereEqualTo("level", level)
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
                exerciseAdapter.notifyDataSetChanged()
                Log.d("LevelFilteredExr", "Categories List: $categoriesList")
            }
//        levelFilterList(level)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentLevelFilteredExrBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exerciseAdapter = ExerciseListAdapter(requireContext(), categoriesList, object :
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
        binding.recyclerCategory.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCategory.adapter = exerciseAdapter
        binding.fabAdd.setOnClickListener {
//            showAddExerciseDialog()
        }
    }
    fun levelFilterList(level:Int){
        println("levelfn")
        db.collection(collectionName).whereEqualTo("level",level)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (snapshot in snapshots!!.documentChanges) {
                    val userModel = convertObject(snapshot.document)

                    when (snapshot.type) {
                        DocumentChange.Type.ADDED -> {
                            userModel.let { categoriesList.add(it) }
                            Log.e("", "userModelList ${categoriesList.size}")
                            Log.e("", "userModelList ${categoriesList}")

                        }

                        DocumentChange.Type.MODIFIED -> {
                            userModel.let {
                                var index = getIndex(userModel)
                                if (index > -1)
                                    categoriesList.set(index, it)
                            }
                        }

                        DocumentChange.Type.REMOVED -> {
                            userModel?.let {
                                var index = getIndex(userModel)
                                if (index > -1)
                                    categoriesList.removeAt(index)
                            }
                        }
                    }
                }
                exerciseAdapter.notifyDataSetChanged()
            }

        println("levelfn$categoriesList")

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
         * @return A new instance of fragment LevelFilteredExrFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LevelFilteredExrFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}