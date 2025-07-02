package com.pardeep.yogify.presentation.mainScreen.trackingScreen

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.pardeep.yogify.databinding.FragmentTrackingBinding
import com.pardeep.yogify.presentation.mainScreen.trackingScreen.uiModel.DayDataClass
import com.pardeep.yogify.thirdActivity.RecyclerInterface
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
    val calenderAdp = CalenderAdapter(weekDayDataClasses, this)
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

        getWeekDays()

        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.dayRecyclerView?.adapter = calenderAdp
        binding?.dayRecyclerView?.layoutManager = linearLayoutManager


        //


        // linear snap helper used for center snap
        linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(binding?.dayRecyclerView)
        binding?.datePicker?.setOnClickListener {
            setupDatePicker()
        }

    }

    private fun setupDatePicker() {
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayofmonth ->
                val calender = Calendar.getInstance()
                calender.set(year, month, dayofmonth)
                var formatDate = dateFormat.format(calender.time)
                Toast.makeText(requireContext(), "${formatDate}", Toast.LENGTH_SHORT).show()
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONDAY),
            Calendar.getInstance().get(Calendar.DATE),
        ).show()

    }

    private fun findFirstDayOfWeek(calendar: Calendar) {
        calendar.set(Calendar.DAY_OF_WEEK , calendar.firstDayOfWeek)

        //find first day of week

        for (i in 0..6){
            val calendar = Calendar.getInstance()
            Log.d(TAG, "calender: ${calendar} ")
            val firstDayOfWeek = calendar.time
            Log.d(TAG, "findFirstDayOfWeek:${firstDayOfWeek} ")
            calendar.add(Calendar.DAY_OF_WEEK ,1)
        }


    }


    // ----------------------- getting the week of day ------------------------------------
    fun getWeekDays(): Pair<List<DayDataClass>, Int> {
        val calendar = Calendar.getInstance()
        val dayOfWeek = Calendar.DAY_OF_WEEK
        val firstDayOfWeek = calendar.firstDayOfWeek
        val currentDate = dateFormat.format(calendar.time)
        Log.d(TAG, "onViewCreated: $dayOfWeek , $firstDayOfWeek")
        Log.d(TAG, "onViewCreated: ${calendar.time}")
        var currentDatePosition = 0


        for (i in 0..6) {
            val date = dateFormat.format(calendar.time)
            val day = dayFormat.format(calendar.time).first().toString()
            weekDayDataClasses.add(DayDataClass(date, day))


            Log.d(TAG, "onViewCreated1: ${date} ${day}")

            if (date.equals(currentDate)) {
                Log.d(TAG, "getWeekDays: 'Matched'")
                currentDatePosition = i
                Log.d(TAG, "currentDatePosition : $currentDatePosition")
                binding?.dayRecyclerView?.scrollToPosition(currentDatePosition)
                binding?.dayRecyclerView?.post {
                    onItemClick(currentDatePosition , "calenderAdapter")
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

    override fun onItemClick(position: Int ,callFrom: String) {
        for (i in 0 until binding?.dayRecyclerView?.childCount!!){
            val child = binding?.dayRecyclerView?.getChildAt(i)
            child?.scaleX = 0.85f
            child?.scaleY = 0.85f
            child?.alpha = 0.85f
        }

        val clickedChild = binding?.dayRecyclerView?.findViewHolderForAdapterPosition(position)?.itemView
        clickedChild?.scaleX = 1.1f
        clickedChild?.scaleY = 1.1f
        clickedChild?.alpha = 1.0f


    }
}