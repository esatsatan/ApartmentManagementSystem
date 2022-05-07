package com.satan.estyonetim.homefragmentviews

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

        fitnessRecyclerAdapter = FitnessDetailRecyclerAdapter(fitnessList)
        binding.fitnessRecyclerAdapter.adapter = fitnessRecyclerAdapter

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

    private fun getAppointmentData() {

        database.collection("fitnessAppointment").orderBy("appointmentHour",Query.Direction.ASCENDING).get()
            .addOnSuccessListener { results->
                if (results != null) {

                    for (result in results){
                        val name = result.get("userName") as String
                        val day = result.get("appointmentDay") as String
                        val hour = result.get("appointmentHour") as String
                        val minute = result.get("appointmentMinute") as String
                        val status = result.get("status") as String

                        val getData = Fitness(name,hour,minute,day,status)
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
        if(query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {

      return true
    }

    private fun searchDatabase(query : String) {


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}