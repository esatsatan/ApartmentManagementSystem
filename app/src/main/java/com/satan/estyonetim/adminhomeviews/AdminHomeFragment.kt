package com.satan.estyonetim.adminhomeviews

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.core.QueryListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.R
import com.satan.estyonetim.adapters.AdminHomePageRecyclerAdapter
import com.satan.estyonetim.databinding.FragmentAdminHomeBinding
import com.satan.estyonetim.loginviews.LoginActivity
import com.satan.estyonetim.model.User
import java.util.ArrayList


class AdminHomeFragment : Fragment() {

    private var _binding : FragmentAdminHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : FirebaseFirestore
    private lateinit var adminHomeRecyclerAdapter : AdminHomePageRecyclerAdapter

    var userList = ArrayList<User>()

    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAdminHomeBinding.inflate(inflater,container,false)

        database = Firebase.firestore

        val layoutManager = LinearLayoutManager(requireContext())
        binding.adminHomePageRecyclerView.layoutManager = layoutManager

        adminHomeRecyclerAdapter = AdminHomePageRecyclerAdapter(userList)
        binding.adminHomePageRecyclerView.adapter = adminHomeRecyclerAdapter

        spinnerItemSelected()




        return binding.root

    }

    private fun spinnerItemSelected() {

        val customList = listOf("A BLOK","B BLOK","C BLOK","D BLOK")
        val adapter = ArrayAdapter(requireContext(),R.layout.admin_dropdown_item,customList)
        binding.adminHomeSpinner.adapter = adapter

        binding.adminHomeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                if(adapterView?.getItemAtPosition(position) == customList[0]){

                    database.collection("UsersInfo").whereEqualTo("apartmentNumber","A BLOK")
                        .get().addOnSuccessListener { documents ->
                                if (!documents.isEmpty) {

                                    userList.clear()

                                    for (document in documents) {
                                        val userName = document.get("name") as String
                                        val userApartNo = document.get("apartmentNumber") as String
                                        val userPhone = document.get("phoneNumber") as String
                                        val userRoomNo = document.get("roomNumber") as String

                                        val userDetails = User(userName,userApartNo,userPhone,userRoomNo)
                                        userList.add(userDetails)
                                        Toast.makeText(requireContext(),"veriler alındı.",Toast.LENGTH_SHORT).show()
                                    }
                                    adminHomeRecyclerAdapter.notifyDataSetChanged()

                                } else {
                                    Toast.makeText(requireContext(),"C blokta oturan kimse yok !",Toast.LENGTH_SHORT).show()
                                }

                        }

                } else if (adapterView!!.selectedItem.toString().equals("B BLOK")){

                    database.collection("UsersInfo").whereEqualTo("apartmentNumber","B BLOK")
                        .get().addOnSuccessListener { documents ->
                            if (!documents.isEmpty) {

                                userList.clear()

                                for (document in documents) {
                                    val userName = document.get("name") as String
                                    val userApartNo = document.get("apartmentNumber") as String
                                    val userPhone = document.get("phoneNumber") as String
                                    val userRoomNo = document.get("roomNumber") as String

                                    val userDetails = User(userName,userApartNo,userPhone,userRoomNo)
                                    userList.add(userDetails)
                                    Toast.makeText(requireContext(),"veriler alındı.",Toast.LENGTH_SHORT).show()
                                }
                                adminHomeRecyclerAdapter.notifyDataSetChanged()

                            } else {
                                Toast.makeText(requireContext(),"B blokta oturan kimse yok !",Toast.LENGTH_SHORT).show()
                            }

                        }




                    adminHomeRecyclerAdapter.notifyDataSetChanged()

                } else if (adapterView.selectedItem.toString().equals("C BLOK")) {

                    database.collection("UsersInfo").whereEqualTo("apartmentNumber","C BLOK")
                        .get().addOnSuccessListener { documents ->
                            if (!documents.isEmpty) {

                                userList.clear()

                                for (document in documents) {
                                    val userName = document.get("name") as String
                                    val userApartNo = document.get("apartmentNumber") as String
                                    val userPhone = document.get("phoneNumber") as String
                                    val userRoomNo = document.get("roomNumber") as String

                                    val userDetails = User(userName,userApartNo,userPhone,userRoomNo)
                                    userList.add(userDetails)
                                    Toast.makeText(requireContext(),"veriler alındı.",Toast.LENGTH_SHORT).show()
                                }
                                adminHomeRecyclerAdapter.notifyDataSetChanged()

                            } else {
                                Toast.makeText(requireContext(),"C blokta oturan kimse yok !",Toast.LENGTH_SHORT).show()
                            }

                        }

                }
                else if (adapterView.selectedItem.toString().equals("D BLOK")) {

                    database.collection("UsersInfo").whereEqualTo("apartmentNumber","D BLOK")
                        .get().addOnSuccessListener { documents ->
                            if (!documents.isEmpty) {

                                userList.clear()

                                for (document in documents) {
                                    val userName = document.get("name") as String
                                    val userApartNo = document.get("apartmentNumber") as String
                                    val userPhone = document.get("phoneNumber") as String
                                    val userRoomNo = document.get("roomNumber") as String

                                    val userDetails = User(userName,userApartNo,userPhone,userRoomNo)
                                    userList.add(userDetails)
                                    Toast.makeText(requireContext(),"veriler alındı.",Toast.LENGTH_SHORT).show()
                                }
                                adminHomeRecyclerAdapter.notifyDataSetChanged()

                            } else {
                                Toast.makeText(requireContext(),"C blokta oturan kimse yok !",Toast.LENGTH_SHORT).show()
                            }

                        }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }


        }
    }






    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}