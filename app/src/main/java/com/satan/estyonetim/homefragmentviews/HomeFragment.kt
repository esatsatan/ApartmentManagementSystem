package com.satan.estyonetim.homefragmentviews

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.adapters.BasketballRecyclerAdapter
import com.satan.estyonetim.adminhomeviews.CreateBasketballGameFragment.Companion.currentPersonCount
import com.satan.estyonetim.databinding.FragmentHomeBinding
import com.satan.estyonetim.model.Basketball

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var basketBallRecyclerAdapter : BasketballRecyclerAdapter
    private lateinit var database : FirebaseFirestore

    var getBasketballDatas = ArrayList<Basketball>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        database = Firebase.firestore

        val layoutManager = LinearLayoutManager(requireContext())
        binding.basketballRecyclerView.layoutManager = layoutManager

        basketBallRecyclerAdapter = BasketballRecyclerAdapter(getBasketballDatas)
        binding.basketballRecyclerView.adapter = basketBallRecyclerAdapter

        getBasketballActivityData()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getBasketballActivityData() {
        database.collection("BasketballActivity").addSnapshotListener { snapshots, error ->
            if (snapshots != null && !snapshots.isEmpty) {

                getBasketballDatas.clear()

                for(documents in snapshots.documents) {

                    val activityDay = documents.get("gameDay").toString()
                    val activityHour = documents.get("gameHour").toString()
                    val activityMinute = documents.get("gameMinute").toString()
                    val organizer = documents.get("organizer").toString()
                    val personCount = documents.get("personCount").toString()

                    val getData = Basketball(organizer,activityDay,activityHour,activityMinute,personCount,currentPersonCount)
                    getBasketballDatas.add(getData)

                }
                basketBallRecyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(),"Hata ! veri getirilemedi : $error",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun searchData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}