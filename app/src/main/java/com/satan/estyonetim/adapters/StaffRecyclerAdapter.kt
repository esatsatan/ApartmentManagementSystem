package com.satan.estyonetim.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.satan.estyonetim.R
import com.satan.estyonetim.adminhomeviews.DisplayPersonalFragmentDirections
import com.satan.estyonetim.databinding.AddPersonalRowBinding
import com.satan.estyonetim.model.Staff
import java.util.ArrayList

 class StaffRecyclerAdapter(val staffList : ArrayList<Staff>) : RecyclerView.Adapter<StaffRecyclerAdapter.StaffViewHolder>() {

    class StaffViewHolder(val binding : AddPersonalRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
        val binding = AddPersonalRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StaffViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {

        holder.binding.staffName.text = staffList[position].name
        holder.binding.staffPosition.text = staffList[position].position
        holder.binding.staffIdentityNo.text = staffList[position].identity
        holder.binding.staffSalary.text = staffList[position].salary

        holder.binding.recyclerItemRow.setOnClickListener {
            val action = DisplayPersonalFragmentDirections
                .actionDisplayPersonalFragmenttToStaffDetailFragment(staffList[position].name,
                staffList[position].position,
                staffList[position].identity,
                staffList[position].salary.toInt())
            Navigation.findNavController(it).navigate(action)
        }


        // GÖRSEL EKLENECEK

    }

    override fun getItemCount(): Int {
            return staffList.size
    }

}