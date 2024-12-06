package com.pardeep.yogify.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            des = it.getString("des")
            imgurl = it.getString("imgUrl")
            duration = it.getString("time")
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