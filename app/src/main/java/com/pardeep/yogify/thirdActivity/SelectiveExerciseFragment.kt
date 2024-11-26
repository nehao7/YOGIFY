package com.pardeep.yogify.thirdActivity

import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.pardeep.yogify.ExerciseImageFragment
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentSelectiveExerciseBinding
import java.util.Locale

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
    var binding : FragmentSelectiveExerciseBinding?=null
    var currentPosition = 0
    val fragments = listOf(ExerciseImageFragment(), ExerciseImageFragment())
    lateinit var exerciseViewPager: ExerciseViewPager
    lateinit var textToSpeech: TextToSpeech
    val milliSecond : Long = 60000 // 1 min = 60000millisecond
    var countDownTimer : CountDownTimer?=null


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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectiveExerciseBinding.inflate(layoutInflater)
        return binding?.root
        //return inflater.inflate(R.layout.fragment_selective_exercise, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textToSpeech = TextToSpeech(requireContext() , object : TextToSpeech. OnInitListener{
            override fun onInit(status: Int) {
                if (status == TextToSpeech.SUCCESS){
                    if (status == TextToSpeech.LANG_NOT_SUPPORTED || status == TextToSpeech.LANG_MISSING_DATA){
                        Toast.makeText(requireContext(), "Language error", Toast.LENGTH_SHORT).show()
                    }else
                        textToSpeech.setSpeechRate(0.75f)
                }else{
                    Toast.makeText(requireContext(), "Error to play tts", Toast.LENGTH_SHORT).show()
                }
            }

        } )

        //------------------------- text to speech ---------------------------
        var click = 0
        binding?.play?.setOnClickListener{
            if (click == 0){
                binding?.play?.setImageResource(R.drawable.pause)
                startTimer()
                click++
                speak(binding?.instruction?.text.toString())
            }else{
                click = 0
                binding?.play?.setImageResource(R.drawable.play__3_)

                textToSpeech.stop()
            }

        }
        //------------------------- text to speech ---------------------------




        exerciseViewPager = ExerciseViewPager(lifecycle = lifecycle, fragments = fragments , fragmentManager = childFragmentManager )
        binding?.viewPager?.adapter = exerciseViewPager
        binding?.viewPager?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPosition = position
            }

        })

        binding?.dotIndicator?.attachTo(binding?.viewPager!!)
    }

    private fun speak(text : String) {
        textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null,null)
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
        fun newInstance(param1: String, param2: String) =
            SelectiveExerciseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //---------------count down-------------------------
    fun startTimer(){
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(milliSecond,1000) {
            override fun onTick(millisUntilFinished: Long) {

                val progress = ((milliSecond - millisUntilFinished) * 100 / milliSecond).toInt()
                binding?.linearProgress?.progress = progress
                println(progress)
            }

            override fun onFinish() {
                binding?.linearProgress?.progress =100
                binding?.play?.setImageResource(R.drawable.play__3_)
            }

        }.start()
    }

    //---------------count down-------------------------
}