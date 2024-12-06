package com.pardeep.yogify.customer

import android.os.Bundle
import android.os.CountDownTimer
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
        Glide.with(requireContext())
            .load(imgurl)
            .into(binding.imageslider)
        binding.instruction.setText(des)
        binding.title.setText(name)
        binding.durationTv.setText(duration)
        binding.play.setOnClickListener {
            // Get the time input from the user
            val inputTime = binding.durationTv.text.toString()

            // Check if the input is valid
            if (inputTime.isNotEmpty()) {
                try {
                    // Convert to seconds and then to milliseconds
//                    timeInMillis = inputTime.toLong() * 1000
                    val minutes = inputTime.toLong()
                    timeInMillis = minutes * 60 * 1000

                    // Start the timer
                    startTimer(timeInMillis)

                } catch (e: NumberFormatException) {
                    // Handle invalid input
                    Toast.makeText(requireContext(), "Invalid time input!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter a time", Toast.LENGTH_SHORT).show()
            }
        }
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
                binding.tvTimeRemaining.text = "Time Remaining: $formattedTime"
//                // Update the UI every second
//                val secondsRemaining = millisUntilFinished / 1000
//                binding.tvTimeRemaining.text = "Time Remaining: $secondsRemaining"
            }

            override fun onFinish() {
                // Timer finished
                binding.tvTimeRemaining.text = "Time's up!"
                exrId?.let { updateExerciseCompletionStatus(it) }
                findNavController().popBackStack()

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
                Toast.makeText(requireContext(), "Exercise marked as completed", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // Handle failure
                Toast.makeText(requireContext(), "Failed to update status: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Cancel the timer when the activity is destroyed
        countDownTimer?.cancel()
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