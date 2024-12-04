package com.pardeep.yogify.admin

import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentSelectiveExerciseBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectiveExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectiveExerciseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentSelectiveExerciseBinding? = null
    var image1 = 0
    var image2 = 0
    var image3 = 0
    var image4 = 0
    var title: String? = ""
    var description : String?=""
    var poseLevel :String?=""
    var duration : Int ?=0

    var imageList = arrayListOf<SlideModel>()
    lateinit var textToSpeech: TextToSpeech
    val milliSecond: Long = 60000 // 1 min = 60000millisecond
    var countDownTimer: CountDownTimer? = null
            var click = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString("title")
            image1 = it.getInt("image1")
            image2 = it.getInt("image2")
            image3 = it.getInt("image3")
            image4 = it.getInt("image4")
            duration = it.getInt("duration")
            poseLevel = it.getString("poseLevel")
            description = it.getString("description")
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectiveExerciseBinding.inflate(layoutInflater)
        return binding?.root
        //return inflater.inflate(R.layout.fragment_selective_exercise, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), "${duration} ,${title},${description +poseLevel}", Toast.LENGTH_SHORT).show()
        binding?.title?.setText(title)
        binding?.durationTv?.setText(duration.toString())
        binding?.poseLevelTv?.setText(poseLevel)
        binding?.instruction?.setText(description)
        Toast.makeText(requireContext(), "${description}", Toast.LENGTH_SHORT).show()

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
        binding?.title?.setText(title)

        //------------------------- text to speech ---------------------------
        binding?.play?.setOnClickListener {
            if (click == 0) {
                binding?.play?.setImageResource(R.drawable.pause)
                startTimer()
                click++
                speak(binding?.instruction?.text.toString())
            } else {
                click = 0
                binding?.play?.setImageResource(R.drawable.play__3_)
                binding?.linearProgress?.progress=0

                textToSpeech.stop()
            }

        }
        //------------------------- text to speech ----------------------

        imageList.add(
            SlideModel(
                image1,  scaleType = ScaleTypes.CENTER_CROP
            )
        )
        imageList.add(
            SlideModel(
                image2,  scaleType = ScaleTypes.CENTER_CROP
            )
        )
        imageList.add(
            SlideModel(
                image3,

                scaleType = ScaleTypes.CENTER_CROP
            )
        )
        imageList.add(
            SlideModel(
                image4,  scaleType = ScaleTypes.CENTER_CROP
            )
        )
        binding?.imageSlider?.setImageList(imageList)


    }



    private fun speak(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onPause() {
        super.onPause()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SelectiveExerciseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = SelectiveExerciseFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }

    //---------------count down-------------------------
    fun startTimer() {
        countDownTimer = object : CountDownTimer(milliSecond, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = ((milliSecond - millisUntilFinished) * 100 / milliSecond).toInt()
                if (click == 1){
                    binding?.linearProgress?.progress = progress
                    println(progress)
                }else{
                    countDownTimer?.cancel()
                    binding?.linearProgress?.progress = progress
                }

            }

            override fun onFinish() {
                binding?.linearProgress?.progress = 100
                binding?.play?.setImageResource(R.drawable.play__3_)
                
            }

        }.start()
    }

    //---------------count down-------------------------
}