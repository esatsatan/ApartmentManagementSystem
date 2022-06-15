package com.satan.estyonetim.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.databinding.AdminHomeRecyclerRowBinding
import com.satan.estyonetim.model.User
import java.util.ArrayList

class AdminHomePageRecyclerAdapter(var getUsers : ArrayList<User>) :
    RecyclerView.Adapter<AdminHomePageRecyclerAdapter.AdminHomeViewHolder>() {

    private  var database : FirebaseFirestore = Firebase.firestore

      class AdminHomeViewHolder(val binding : AdminHomeRecyclerRowBinding ) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHomeViewHolder {
        val binding = AdminHomeRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdminHomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminHomeViewHolder, position: Int) {
        holder.binding.adminHomeRecyclerUserName.text = getUsers[position].userName
        holder.binding.adminHomeRecyclerUserApartmentNo.text = getUsers[position].apartNo
        holder.binding.adminHomeRecyclerUserPhone.text = getUsers[position].userPhone
        holder.binding.adminHomeRecyclerUserRoomNo.text = getUsers[position].userRoomNo

    }




    override fun getItemCount(): Int {
        return getUsers.size
    }
}