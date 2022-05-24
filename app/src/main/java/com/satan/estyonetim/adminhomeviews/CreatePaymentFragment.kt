package com.satan.estyonetim.adminhomeviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.satan.estyonetim.R
import com.satan.estyonetim.databinding.FragmentCreatePaymentBinding


class CreatePaymentFragment : Fragment() {

    private var _binding : FragmentCreatePaymentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCreatePaymentBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}