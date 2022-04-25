package com.satan.estyonetim.homefragmentviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.adapters.PaymentDetailRecyclerAdapter
import com.satan.estyonetim.databinding.FragmentPaymentDetailBinding
import com.satan.estyonetim.model.PaymentAttributes
import java.util.ArrayList


class PaymentDetailFragment : Fragment() {

    private var _binding: FragmentPaymentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var paymentDetailRecyclerAdapter : PaymentDetailRecyclerAdapter

     var paymentDetails = ArrayList<PaymentAttributes>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?):
            View{
        // Inflate the layout for this fragment
        _binding = FragmentPaymentDetailBinding.inflate(inflater,container,false)

        // initialize database and authentication
        database = Firebase.firestore
        auth = Firebase.auth


        // bind layout manager
        val layoutManager = LinearLayoutManager(requireContext())
        binding.paymentDetailRecyclerView.layoutManager = layoutManager
        // bind adapters
        paymentDetailRecyclerAdapter = PaymentDetailRecyclerAdapter(paymentDetails)
        binding.paymentDetailRecyclerView.adapter = paymentDetailRecyclerAdapter

        return binding.root
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}