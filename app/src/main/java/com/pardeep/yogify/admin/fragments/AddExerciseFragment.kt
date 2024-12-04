package com.pardeep.yogify.admin.fragments

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.pardeep.yogify.Constants
import com.pardeep.yogify.R
import com.pardeep.yogify.ThirdActivity
import com.pardeep.yogify.admin.adapters.ExerciseListAdapter
import com.pardeep.yogify.databinding.AddExerciseDialogBinding
import com.pardeep.yogify.databinding.FragmentAddExerciseBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddExerciseFragment : Fragment() {
    lateinit var binding:FragmentAddExerciseBinding
    // TODO: Rename and change types of parameters
    lateinit var adminActivity: ThirdActivity
    lateinit var exerciseAdapter: ExerciseListAdapter
    var categoriesList = arrayListOf<ExerciseListModel>()
    val db = Firebase.firestore
    var collectionName = Constants.drawings
    var uriFilePath: String? = null
    var uriContent: Uri? = null
    var downloadUri: Uri? = null
    var adapterPosition = -1
    var imgCandle: ImageView? = null
//    lateinit var dialogBinding: AddExerciseDialogBinding
    var categoryid = ""
    var dialog: Dialog?=null

    private var mediaPermission = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S)
        Manifest.permission.READ_EXTERNAL_STORAGE
    else {
        Manifest.permission.READ_MEDIA_IMAGES
    }

    private var getImagePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it)
                launchCropImage()
        }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                mediaPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            getImagePermission.launch(mediaPermission)
        } else {
            launchCropImage()
        }
    }

    fun launchCropImage() {
        cropImage.launch(
            CropImageContractOptions(
                uri = null,
                cropImageOptions = CropImageOptions(
                    imageSourceIncludeCamera = false,
                    imageSourceIncludeGallery = true,
                ),
            )
        )
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // Use the returned uri.
            uriContent = result.uriContent
            uriFilePath = result.getUriFilePath(requireContext()) // optional usage
//            dialogBinding.imgAdd.setImageURI(uriContent)
//            imgCandle?.setImageURI(uriContent)
        } else {
            // An error occurred.
            val exception = result.error
        }
    }

    private var param1: String? = null
    private var param2: String? = null
    var catId=""

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
    ): View{
        binding= FragmentAddExerciseBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddExerciseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddExerciseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}