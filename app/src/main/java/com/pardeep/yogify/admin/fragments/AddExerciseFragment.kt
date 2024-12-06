package com.pardeep.yogify.admin.fragments

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.firestore
import com.pardeep.yogify.Constants
import com.pardeep.yogify.R
import com.pardeep.yogify.ThirdActivity
import com.pardeep.yogify.admin.ClickType
import com.pardeep.yogify.admin.adapters.ExerciseListAdapter
import com.pardeep.yogify.admin.clickInterface
import com.pardeep.yogify.databinding.AddCategoryDialogBinding
import com.pardeep.yogify.databinding.AddExerciseDialogBinding
import com.pardeep.yogify.databinding.FragmentAddExerciseBinding
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.UploadStatus
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.uploadAsFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    var collectionName = Constants.exercises
    var uriFilePath: String? = null
    var uriContent: Uri? = null
    var downloadUri: Uri? = null
    var adapterPosition = -1
    var imgCandle: ImageView? = null
    lateinit var dialogBinding: AddExerciseDialogBinding
    var categoryid = ""
    var dialog: Dialog?=null
    private val PERMISSION_REQUEST_CODE = 100
    private val MANAGE_EXTERNAL_STORAGE_REQUEST_CODE = 101
    private lateinit var supabaseClient: SupabaseClient
    var isBeginnerSelected = false
    var isIntermediateSelected = false
    var isAdvancedSelected = false
    var level=-1

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
            dialogBinding.imgAdd.setImageURI(uriContent)
//            imgCandle?.setImageURI(uriContent)
        } else {
            // An error occurred.
            val exception = result.error
        }
    }

    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryid=it.getString("id","")
        }
        Log.d("TAG", "onCreate:$categoryid ")
        adminActivity=activity as ThirdActivity
        db.collection(collectionName).whereEqualTo("catId",categoryid)
            .addSnapshotListener { snapshots, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges) {
                val userModel = convertObject(snapshot.document)

                when (snapshot.type) {
                    DocumentChange.Type.ADDED -> {
                        userModel?.let { categoriesList.add(it) }
                        Log.e("", "userModelList ${categoriesList.size}")
                        Log.e("", "userModelList ${categoriesList}")

                    }

                    DocumentChange.Type.MODIFIED -> {
                        userModel?.let {
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
        supabaseClient = (adminActivity.applicationContext as MyApplication).supabaseClient
        checkAndRequestPermissions()

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
        exerciseAdapter = ExerciseListAdapter(requireContext(), categoriesList, FirebaseAuth.getInstance() ,object :
            clickInterface {
            override fun onClick(position: Int, clickType: ClickType?): Boolean {
                when (clickType) {
                    ClickType.AddSub -> {
                    }

                    ClickType.OnViewClick -> {
                        adminActivity.navController.navigate(R.id.exrDetailsFragment,
                            bundleOf("imgUrl" to categoriesList[position].exrImgUri,
                                "des" to categoriesList[position].description,
                            "name" to categoriesList[position].exrName,
                            "id" to categoriesList[position].exrId,
                            "time" to categoriesList[position].duration)
                            )
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
                        showAddExerciseDialog(position)
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
//        layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCategory.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCategory.adapter = exerciseAdapter
        binding.fabAdd.setOnClickListener {
            showAddExerciseDialog()
        }



    }
    private fun checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For Android 10 (API level 29) and above, request MANAGE_EXTERNAL_STORAGE if needed
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // For Android 11 (API level 30) and above, check for external storage manager permission
                if (Environment.isExternalStorageManager()) {
                    // Permission granted, proceed
                } else {
                    // Ask for permission
                    requestManageExternalStoragePermission()
                }
            } else {
                // For Android 10 (API level 29), request MANAGE_EXTERNAL_STORAGE if needed
                if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Request permission
                    requestManageExternalStoragePermission()
                }
            }
        } else {
            // For devices below Android 10, we need the read permission
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Request permission
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
            }
        }





    }
    private fun requestManageExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 (API level 30) and above
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                startActivityForResult(intent, PERMISSION_REQUEST_CODE)
            } catch (e: ActivityNotFoundException) {
                Log.e("PermissionRequest", "Activity not found for the permission intent.")
            }
        } else {
            Log.e("PermissionRequest", "The permission is only available on Android 11 (API level 30) and above.")
        }
//
    }

    fun showAddExerciseDialog(position: Int = -1) {
        dialogBinding = AddExerciseDialogBinding.inflate(layoutInflater)
        dialog = Dialog(requireContext()).apply {
            setContentView(dialogBinding.root)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            if (position>-1){
                dialogBinding.btnsave.setText("Update")
            }else{
                dialogBinding.btnsave.setText("Add")

            }
            show()
        }
//        dialogBinding.position = position
        dialogBinding.imgAdd.setOnClickListener {
            Toast.makeText(requireContext(), "imgClicked", Toast.LENGTH_SHORT).show()
            checkPermissions()
        }

        dialogBinding.edtitems.doOnTextChanged { text, _, _, _ ->
            var textLength = text?.length ?: 0
            if (textLength > 0) {
                dialogBinding.tilitemName.isErrorEnabled = false
            } else {
                dialogBinding.tilitemName.isErrorEnabled = true
                dialogBinding.tilitemName.error = "Enter Category"
            }
        }
        if (position > -1) {
            dialogBinding.categoriesModel = categoriesList[position]
            dialogBinding.beginner.isChecked=categoriesList[position].beginnner
            dialogBinding.intermediate.isChecked=categoriesList[position].intermediate
            dialogBinding.advanced.isChecked=categoriesList[position].advance

        } else {
            dialogBinding.categoriesModel =
                ExerciseListModel()
        }
        if (position > -1) {
            Glide.with(requireContext())
                .load(categoriesList[position].exrImgUri)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(dialogBinding.imgAdd)
        }

        dialogBinding.rdGroup.setOnCheckedChangeListener { group, checkedId ->
            // Reset all boolean values to false initially
            isBeginnerSelected = false
            isIntermediateSelected = false
            isAdvancedSelected = false

            // Check which RadioButton is selected and update the corresponding boolean variable
            when (checkedId) {
                R.id.beginner ->{
                    isBeginnerSelected = true
                    level=0
                }
                R.id.intermediate -> {
                    isIntermediateSelected = true
                    level=1
                }
                R.id.advanced -> {
                    isAdvancedSelected = true
                    level=2
                }
            }

            // You can now use these boolean variables, for example:
            Log.d("RadioButtonSelection", "Beginner: $isBeginnerSelected")
            Log.d("RadioButtonSelection", "Intermediate: $isIntermediateSelected")
            Log.d("RadioButtonSelection", "Advanced: $isAdvancedSelected")
        }


        dialogBinding.btnsave.setOnClickListener {
            dialogBinding.pbar.visibility = View.VISIBLE
            if (dialogBinding.edtitems.text.toString().isNullOrEmpty()) {
                dialogBinding.tilitemName.isErrorEnabled = true
                dialogBinding.tilitemName.error = "Enter Name"
            }
            else if (dialogBinding.edtDes.text.toString().isNullOrEmpty()) {
                dialogBinding.tilDes.isErrorEnabled = true
                dialogBinding.tilDes.error = "Enter Name"
            }
            else if (dialogBinding.edtDuration.text.toString().isNullOrEmpty()) {
                dialogBinding.tilDuration.isErrorEnabled = true
                dialogBinding.tilDuration.error = "Enter Name"
            } else if (uriContent != null && position == -1) {
                uplaodImage(position)
            } else if (uriContent != null && position > -1) {
                uplaodImage(position)
            } else if (position > -1) {
                updateData(position, categoriesList[position].exrImgUri ?: "", dialog!!,isBeginnerSelected,isIntermediateSelected,isAdvancedSelected)
            } else {

                Toast.makeText(requireContext(), "Fill the form correctly", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    fun uplaodImage(position: Int? = -1) {


        uriContent?.let { uploadImageToSupabase(position,it) }

    }
    private fun uploadImageToSupabase(position: Int?=-1,uri: Uri) {
        val byteArray = uriToByteArray(requireContext(), uri)
        val fileName = "uploads/${System.currentTimeMillis()}.jpg"

        val bucket = supabaseClient.storage.from("yogify_storage") // Choose your bucket name

        // Use lifecycleScope for safe coroutine usage
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Upload image and handle the response
                bucket.uploadAsFlow(fileName, byteArray).collect { status ->
                    withContext(Dispatchers.Main) {
                        when (status) {
                            is UploadStatus.Progress -> {
//                                val progress = (status.totalBytesSent.toFloat() / status.contentLength * 100)
                                Log.d("Upload", "Progress%")
                            }
                            is UploadStatus.Success -> {
                                Log.d("Upload ", "Upload Success")
                                val imageUrl = bucket.publicUrl(fileName)
                                Log.d("Upload", "$imageUrl")
//                                var img:ImageView=findViewById(R.id.img)
                                if (position != null) {
                                    dialog?.let { updateData(position,imageUrl, it,isBeginnerSelected,isIntermediateSelected,isAdvancedSelected) }
                                }

//                                Glide.with(requireContext())
//                                    .load(imageUrl)
//                                    .placeholder(R.drawable.ic_launcher_background)
//                                    .into(img);

                                Toast.makeText(requireContext(), "Upload Successful!", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("Upload", "Error uploading image: ${e.message}")
                    Toast.makeText(requireContext(), "Error uploading image: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun uriToByteArray(context: Context, uri: Uri): ByteArray {
        val inputStream = context.contentResolver.openInputStream(uri)
        return inputStream?.readBytes() ?: ByteArray(0)
    }

    fun updateData(position: Int = -1, imageUrl: String = "", dialog: Dialog,begin:Boolean,Intermediate:Boolean,Advanced:Boolean) {
        dialogBinding.pbar.visibility = View.VISIBLE

        var exerciseListModel =ExerciseListModel(
            exrName = dialogBinding.edtitems.text.toString(),
            description = dialogBinding.edtDes.text.toString(),
            duration = dialogBinding.edtDuration.text.toString(),
            beginnner = begin,
            intermediate = Intermediate,
            level = level,
            advance = Advanced,
            exrImgUri = imageUrl,
            catId = categoryid
        )
        if (position > -1) {
            exerciseListModel.exrId = categoriesList[position].exrId
            db.collection(collectionName).document(categoriesList[position].exrId ?: "").set(
                exerciseListModel
            ).addOnCompleteListener {
                dialogBinding.pbar.visibility = View.GONE
                dialog.dismiss()
            }

        } else {
            //add in firestore
            println("Else Part")
            db.collection(collectionName).add(exerciseListModel).addOnCompleteListener {
                if (it.isSuccessful) {
                    println("Data Saved: ${it.result}")
                    dialogBinding.pbar.visibility = View.GONE
                    dialog.dismiss()

                }
            }
        }
    }


    fun convertObject(snapshot: QueryDocumentSnapshot): ExerciseListModel? {
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