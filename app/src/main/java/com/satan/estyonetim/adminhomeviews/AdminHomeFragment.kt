package com.satan.estyonetim.adminhomeviews

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.R
import com.satan.estyonetim.adapters.AdminHomePageRecyclerAdapter
import com.satan.estyonetim.adapters.AdminHomePaymentsRecyclerAdapter
import com.satan.estyonetim.databinding.FragmentAdminHomeBinding
import com.satan.estyonetim.model.PaymentAttributes
import com.satan.estyonetim.model.User
import kotlin.collections.ArrayList


class AdminHomeFragment : Fragment() {

    private var _binding : FragmentAdminHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : FirebaseFirestore
    private lateinit var adminHomeRecyclerAdapter : AdminHomePageRecyclerAdapter
    private lateinit var paymentSubscriptionRecyclerAdapter : AdminHomePaymentsRecyclerAdapter

    var userList = ArrayList<User>()
    var paymentList = ArrayList<PaymentAttributes>()

    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAdminHomeBinding.inflate(inflater,container,false)


        // initialize database
        database = Firebase.firestore

        // connection userList recyclerView with admin Home page
        val layoutManager = LinearLayoutManager(requireContext())
        binding.adminHomePageRecyclerView.layoutManager = layoutManager

        adminHomeRecyclerAdapter = AdminHomePageRecyclerAdapter(userList)
        binding.adminHomePageRecyclerView.adapter = adminHomeRecyclerAdapter

        // bind payment detail recyclerView to admin Home page
        val paymentLayoutManager = LinearLayoutManager(requireContext())
        binding.paymentSubscriptionRecyclerView.layoutManager = paymentLayoutManager

        paymentSubscriptionRecyclerAdapter = AdminHomePaymentsRecyclerAdapter(paymentList)
        binding.paymentSubscriptionRecyclerView.adapter = paymentSubscriptionRecyclerAdapter


        spinnerItemSelected()
        getPaymentData()

        searchUser()

        return binding.root

    }

    private fun searchUser() {
        binding.adminHomeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (query != null) {
                    searchDataFromFirebase(query)
                    true
                } else {
                    getPaymentData()
                    true
                }
            }
            override fun onQueryTextChange(newText: String?): Boolean {

               return true
            }

        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchDataFromFirebase(query : String) {
        database.collection("PaymentSubscription").whereEqualTo("apartNo",query)
            .get().addOnSuccessListener { documents ->

                paymentList.clear()

                for (document in documents.documents) {
                    val getUserName = document.get("payName").toString()
                    val getApartNo = document.get("apartNo").toString()
                    val getRoomNo = document.get("roomNo").toString()
                    val getTime = document.get("time").toString()

                    val data = PaymentAttributes(1,getUserName,getApartNo,getRoomNo,getTime)
                    paymentList.add(data)
                }
                paymentSubscriptionRecyclerAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Hata : ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getPaymentData() {
        database.collection("PaymentSubscription").addSnapshotListener { snapshot, error ->
            if (error == null) {
                if (snapshot != null) {

                    paymentList.clear()

                   for (document in snapshot.documents) {
                       val getUserName = document.get("payName").toString()
                       val getApartNo = document.get("apartNo").toString()
                       val getRoomNo = document.get("roomNo").toString()
                       val getTime = document.get("time").toString()

                       val saveData = PaymentAttributes(1,getUserName,getApartNo,getRoomNo,getTime)
                       paymentList.add(saveData)
                   }
                    paymentSubscriptionRecyclerAdapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(requireContext(),"Veri alınamadı ! (${error.localizedMessage})",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun spinnerItemSelected() {

        val customList = listOf("A BLOK","B BLOK","C BLOK","D BLOK")
        val adapter = ArrayAdapter(requireContext(),R.layout.admin_dropdown_item,customList)
        binding.adminHomeSpinner.adapter = adapter

        binding.adminHomeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            @SuppressLint("NotifyDataSetChanged")
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
                                    Toast.makeText(requireContext(),"A blokta oturan kimse yok !",Toast.LENGTH_SHORT).show()
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
                                Toast.makeText(requireContext(),"D blokta oturan kimse yok !",Toast.LENGTH_SHORT).show()
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