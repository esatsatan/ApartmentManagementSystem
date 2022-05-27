package com.satan.estyonetim.homefragmentviews

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.R
import com.satan.estyonetim.adapters.FitnessDetailRecyclerAdapter
import com.satan.estyonetim.databinding.FragmentFitnessBinding
import com.satan.estyonetim.model.Fitness


class FitnessFragment : Fragment() ,SearchView.OnQueryTextListener {

    private var _binding : FragmentFitnessBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var fitnessRecyclerAdapter : FitnessDetailRecyclerAdapter

    var fitnessList = ArrayList<Fitness>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFitnessBinding.inflate(inflater,container,false)

        database = Firebase.firestore
        auth = Firebase.auth

        setHasOptionsMenu(true)

        // specify layoutManager
        val layoutManager = LinearLayoutManager(requireContext())
        binding.fitnessRecyclerAdapter.layoutManager = layoutManager

        fitnessRecyclerAdapter = FitnessDetailRecyclerAdapter()
        binding.fitnessRecyclerAdapter.adapter = fitnessRecyclerAdapter

        fitnessRecyclerAdapter.getFitnessData(fitnessList)

        getAppointmentData()


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAppointmentData() {

        database.collection("fitnessAppointment").orderBy("appointmentHour",Query.Direction.ASCENDING).get()
            .addOnSuccessListener { results->
                if (results != null) {

                    for (result in results) {
                        val name = result.get("userName") as String
                        val day = result.get("appointmentDay") as String
                        val hour = result.get("appointmentHour") as String
                        val minute = result.get("appointmentMinute") as String
                        val status = result.get("status") as String
                        val photoUrl = result.get("photoUrl") as String

                        val getData = Fitness(name,hour,minute,day,status,photoUrl)
                        fitnessList.add(getData)
                        Toast.makeText(requireContext(),"Veriler alındı",Toast.LENGTH_SHORT).show()
                    }

                    fitnessRecyclerAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Hata ! veri alınamadı dosya yok !",Toast.LENGTH_SHORT).show()
            }

    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return if(query != null) {
            searchDatabase(query)
            true
        } else {
            false
        }

    }

    override fun onQueryTextChange(text: String?): Boolean {
        if (text != null) {
            searchDatabase(text)
        }
        return true
    }

    private fun searchDatabase(query : String) {

        fitnessList.clear()

        database.collection("fitnessAppointment").whereEqualTo("appointmentHour",query).get()
            .addOnSuccessListener { documents->
                for (document in documents) {
                    val hour = document.get("appointmentHour") as String
                    val minute = document.get("appointmentMinute") as String
                    val day = document.get("appointmentDay") as String
                    val name = document.get("userName") as String

                    val data = Fitness(name,hour, minute,day,"yapildi","null")

                    fitnessList.add(data)
                    fitnessRecyclerAdapter.getFitnessData(fitnessList)
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Veri alınamadı !!",Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}