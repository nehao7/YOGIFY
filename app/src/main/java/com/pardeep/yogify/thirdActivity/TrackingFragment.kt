package com.pardeep.yogify.thirdActivity

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.pardeep.yogify.R
import com.pardeep.yogify.databinding.FragmentTrackingBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TrackingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrackingFragment : Fragment() , RecyclerInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentTrackingBinding? = null
    val weekDayDataClasses = mutableListOf<DayDataClass>()
    val calenderAdp = CalenderAdapter(weekDayDataClasses , this)
    lateinit var linearLayoutManager: LinearLayoutManager
    val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
    val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    lateinit var linearSnapHelper: LinearSnapHelper


    private val TAG = "TrackingFragment"

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
        binding = FragmentTrackingBinding.inflate(layoutInflater)
        return binding?.root
//        return inflater.inflate(R.layout.fragment_tracking, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val (weekDayDataClasses, currentDatePosition) = getWeekDays()

        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.dayRecyclerView?.adapter = calenderAdp
        binding?.dayRecyclerView?.layoutManager = linearLayoutManager

        //


        // linear snap helper used for center snaping
        linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(binding?.dayRecyclerView)

        }


            // ----------------------- getting the week of day ------------------------------------
            fun getWeekDays(): Pair<List<DayDataClass>, Int> {
                val calendar = Calendar.getInstance()
                val dayOfWeek = Calendar.DAY_OF_WEEK
                val firstDayOfWeek = calendar.firstDayOfWeek
                val currentDate = dateFormat.format(calendar.time)
                Log.d(TAG, "onViewCreated: $dayOfWeek , $firstDayOfWeek")
                Log.d(TAG, "onViewCreated: ${calendar.time}")
                val data = calendar.set(dayOfWeek, firstDayOfWeek)


                var currentDatePosition = 0

                for (i in 0..6) {
                    val date = dateFormat.format(calendar.time)
                    val day = dayFormat.format(calendar.time).first().toString()
                    weekDayDataClasses.add(DayDataClass(date, day))


                    Log.d(TAG, "onViewCreated: ${date} ${day}")

                    if (date.equals(currentDate)) {
                        Log.d(TAG, "getWeekDays: 'Matched'")
                        currentDatePosition = i
                        Log.d(TAG, "currentDatePostion : $currentDatePosition ")
                        binding?.dayRecyclerView?.scrollToPosition(currentDatePosition)
                        binding?.dayRecyclerView?.post {
                            onItemClick(currentDatePosition)
                        }
                    }

                    calendar.add(Calendar.DAY_OF_WEEK, 1)
                }
                return Pair(weekDayDataClasses, currentDatePosition)
            }


                    // ----------------------- getting the week of day ------------------------------------

                    companion object {
                /**
                 * Use this factory method to create a new instance of
                 * this fragment using the provided parameters.
                 *
                 * @param param1 Parameter 1.
                 * @param param2 Parameter 2.
                 * @return A new instance of fragment TrackingFragment.
                 */
                // TODO: Rename and change types and number of parameters
                @JvmStatic
                fun newInstance(param1: String, param2: String) =
                    TrackingFragment().apply {
                        arguments = Bundle().apply {
                            putString(ARG_PARAM1, param1)
                            putString(ARG_PARAM2, param2)
                        }
                    }
            }

    override fun onItemClick(position: Int) {
        for (i in 0 until binding?.dayRecyclerView?.childCount!!){
            val child = binding?.dayRecyclerView?.getChildAt(i)
            child?.scaleX = 1.0f
            child?.scaleY = 1.0f
            child?.alpha = 1.0f
        }

        val clickedChild = binding?.dayRecyclerView?.findViewHolderForAdapterPosition(position)?.itemView
        clickedChild?.scaleX = 1.2f
        clickedChild?.scaleY = 1.2f
        clickedChild?.alpha = 1.0f


    }
}