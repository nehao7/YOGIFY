package com.pardeep.yogify.customer

import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.pardeep.yogify.Constants
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentExrDetailsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [UserExrDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserExrDetailsFragment : Fragment() {
    lateinit var binding: FragmentExrDetailsBinding
    // TODO: Rename and change types of parameters
    private var name: String? = null
    private var des: String? = null
    private var imgurl: String? = null
    private var duration: String? = null
    private var exrId: String? = null
    val db = Firebase.firestore
    private var countDownTimer: CountDownTimer? = null
    private var timeInMillis: Long = 0
    private  val TAG = "UserExrDetailsFragment"
    var click = 0
    lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            des = it.getString("des")
            imgurl = it.getString("imgUrl")
            duration = it.getString("time")
            exrId = it.getString("id")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=
            FragmentExrDetailsBinding.inflate(layoutInflater)        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: $exrId")
        textToSpeech = TextToSpeech(requireContext(), object : TextToSpeech.OnInitListener {
            override fun onInit(status: Int) {
                if (status == TextToSpeech.SUCCESS) {
                    if (status == TextToSpeech.LANG_NOT_SUPPORTED || status == TextToSpeech.LANG_MISSING_DATA) {
                        Toast.makeText(requireContext(), "Language error", Toast.LENGTH_SHORT)
                            .show()
                    } else textToSpeech.setSpeechRate(0.75f)
                } else {
                    Toast.makeText(requireContext(), "Error to play tts", Toast.LENGTH_SHORT).show()
                }
            }

        })

        if (isAdded){
        Glide.with(requireContext())
            .load(imgurl)
            .into(binding.imageslider)
        binding.instruction.setText(des)
        binding.title.setText(name)
        binding.durationTv.setText(duration)}
        binding.play.setOnClickListener {
            // Get the time input from the user
            val inputTime = binding.durationTv.text.toString()

            // Check if the input is valid
            if (inputTime.isNotEmpty()) {
                try {
                    // Convert to seconds and then to milliseconds
//                    timeInMillis = inputTime.toLong() * 1000
                    val minutes = inputTime.toLong()
                    Log.d(TAG, "onViewCreated: $minutes")
                    timeInMillis = minutes * 60 * 1000
                    Log.d(TAG, "onViewCreated: $timeInMillis")
                    // Start the timer
                    if (click == 0){
                        click = 1
                        startTimer(timeInMillis)
                        binding.play.setImageResource(R.drawable.stop)
                        speak(des)
                    }
                    else if (click == 1){
                        stopTimer()
                        click =0
                        binding.play.setImageResource(R.drawable.play__3_)
                        textToSpeech.stop()
                    }

                } catch (e: NumberFormatException) {
                    // Handle invalid input
                    if (isAdded){
                    Toast.makeText(requireContext(), "Invalid time input!", Toast.LENGTH_SHORT).show()
                }
                }
            } else {
                if (isAdded){
                Toast.makeText(requireContext(), "Please enter a time", Toast.LENGTH_SHORT).show()
            }}
        }
    }

    private fun speak(des: String?) {
        textToSpeech.speak(des, TextToSpeech.QUEUE_FLUSH, null, null)

    }

    private fun stopTimer() {
        countDownTimer?.cancel()
    }

    private fun startTimer(timeInMillis: Long) {
        // Cancel any existing timer
        countDownTimer?.cancel()

        // Start a new CountDownTimer
        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                val minutesRemaining = secondsRemaining / 60
                val seconds = secondsRemaining % 60

                // Format minutes and seconds to always display two digits
                val formattedTime = String.format("%02d:%02d", minutesRemaining, seconds)
                if (isAdded){
//                binding.tvTimeRemaining.text = "Time Remaining: $formattedTime"
                    val total_time = timeInMillis
                    val progress = ((total_time - millisUntilFinished) * 100 / total_time).toInt()
                    binding.linearProgress.progress = progress
                    Log.d(TAG, "onTick: $formattedTime")
                }
//                // Update the UI every second
//                val secondsRemaining = millisUntilFinished / 1000
//                binding.tvTimeRemaining.text = "Time Remaining: $secondsRemaining"
            }

            override fun onFinish() {
                // Timer finished
                if (isAdded){
                exrId?.let { updateExerciseCompletionStatus(it) }
                findNavController().popBackStack()

            }
            }
        }

        // Start the countdown
        countDownTimer?.start()
    }

    private fun updateExerciseCompletionStatus(exrId: String) {
        // Get a reference to the Firestore collection

        // Reference to the document you want to update
        val exerciseRef = db.collection(Constants.exercises).document(exrId)

        // Set the completed field to true
        exerciseRef.update("completed", true)
            .addOnSuccessListener {
                // Successfully updated the document
                if (isAdded){
                Toast.makeText(requireContext(), "Exercise marked as completed", Toast.LENGTH_SHORT).show()
            }
            }
            .addOnFailureListener { e ->
                // Handle failure
                if (isAdded){
                Toast.makeText(requireContext(), "Failed to update status: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            }
    }


    override fun onPause() {
        super.onPause()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExrDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserExrDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}