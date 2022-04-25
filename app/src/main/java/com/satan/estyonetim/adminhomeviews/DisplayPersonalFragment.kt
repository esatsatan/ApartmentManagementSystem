package com.satan.estyonetim.adminhomeviews

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.R
import com.satan.estyonetim.adapters.StaffRecyclerAdapter
import com.satan.estyonetim.databinding.FragmentDisplayPersonalBinding
import com.satan.estyonetim.databinding.FragmentPublishAnnouncementBinding
import com.satan.estyonetim.model.Admin
import com.satan.estyonetim.model.Staff


class DisplayPersonalFragment : Fragment() {

    private var _binding : FragmentDisplayPersonalBinding? = null
    private val binding get() = _binding!!

    private lateinit var database : FirebaseFirestore

    companion object{
        lateinit var staffRecyclerAdapter : StaffRecyclerAdapter
    }


    var staffList = ArrayList<Staff>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDisplayPersonalBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)

        database = Firebase.firestore

        val layoutManager = LinearLayoutManager(requireContext())
        binding.displayStaffRecyclerView.layoutManager = layoutManager

        staffRecyclerAdapter = StaffRecyclerAdapter(staffList)
        binding.displayStaffRecyclerView.adapter = staffRecyclerAdapter

        getStaffFromDatabase()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.admin_add_staff,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addStaff) {
            Navigation.findNavController(requireView()).navigate(R.id.action_displayPersonalFragmentt_to_addStaffFragment)
        }
        return super.onOptionsItemSelected(item)

    }


    fun getStaffFromDatabase() {

            database.collection("staffs").addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Toast.makeText(requireContext(),"Veritabanından data alınamadı",Toast.LENGTH_SHORT).show()
                } else if (snapshot != null) {

                    if (!snapshot.isEmpty){

                        val documents = snapshot.documents

                        staffList.clear()

                        for (document in documents) {
                            val name = document.get("name") as String
                            val position = document.get("position") as String
                            val identity = document.get("identity") as String
                            val salary = document.get("salary") as String

                            val staffProperties = Staff(name,position,identity,salary,0)
                            staffList.add(staffProperties)
                        }

                        staffRecyclerAdapter.notifyDataSetChanged()

                    }

                }
            }
    }





}