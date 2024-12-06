package com.pardeep.yogify.admin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentProfileBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var binding: FragmentProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding?.root
        //return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("DayNightMode", Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()
        if (sharedPreferences.getBoolean("Night", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding?.iconView?.setImageResource(R.drawable.user_skill_gear_night)
            binding?.iconView2?.setImageResource(R.drawable.english_night)
            binding?.iconView3?.setImageResource(R.drawable.cloud_question_night)
            binding?.iconView4?.setImageResource(R.drawable.night_day_night)
            binding?.iconView5?.setImageResource(R.drawable.arrow_left_from_arc_night)
            binding?.endIcon?.setImageResource(R.drawable.right_arrow_night)
            binding?.endIcon2?.setImageResource(R.drawable.right_arrow_night)
            binding?.endIcon3?.setImageResource(R.drawable.right_arrow_night)
            binding?.endIcon4?.setImageResource(R.drawable.right_arrow_night)
            binding?.endIcon5?.setImageResource(R.drawable.right_arrow_night)

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding?.endIcon?.setImageResource(R.drawable.right_arrow)
            binding?.endIcon2?.setImageResource(R.drawable.right_arrow)
            binding?.endIcon3?.setImageResource(R.drawable.right_arrow)
            binding?.endIcon4?.setImageResource(R.drawable.right_arrow)
            binding?.endIcon5?.setImageResource(R.drawable.right_arrow)
            binding?.iconView?.setImageResource(R.drawable.user_skill_gear)
            binding?.iconView2?.setImageResource(R.drawable.english)
            binding?.iconView3?.setImageResource(R.drawable.cloud_question)
            binding?.iconView4?.setImageResource(R.drawable.night_day)
            binding?.iconView5?.setImageResource(R.drawable.arrow_left_from_arc)


        }
        binding?.modeBtn?.setOnClickListener {
            openAlertDialog("modeBtn")
        }

        binding?.helpBtn?.setOnClickListener {
            openAlertDialog("helpBtn")
        }

    }

    private fun openAlertDialog(s: String) {
        when (s) {
            "modeBtn" -> {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Are you sure you want to change theme")
                    setPositiveButton("Yes") { _, _ ->
                        if (sharedPreferences.getBoolean("Night", false)) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            editor.putBoolean("Night", false)
                            editor.commit()
                            editor.apply()
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            editor.putBoolean("Night", true)
                            editor.commit()
                            editor.apply()
                        }
                    }
                    setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                }.show()
            }

            "helpBtn" -> {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Send issue on either email or sms")
                    setPositiveButton("Email") { _, _ ->
                        val emailIntent = Intent(Intent.ACTION_SEND).apply {
                            data = Uri.parse("mailto:maheyp666@gmail.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Error occur in yogify app")
                        }
                        startActivity(emailIntent)
                    }
                    setNegativeButton("sms") { _, _ ->
                        val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("smsto:+918968531504")
                        }
                        startActivity(smsIntent)
                    }
                }.show()
            }
            }
        }

        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment ProfileFragment.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) = ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        }
    }