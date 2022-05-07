package com.satan.estyonetim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satan.estyonetim.databinding.FitnessDetailRowBinding
import com.satan.estyonetim.model.Fitness

class FitnessDetailRecyclerAdapter(var getData : ArrayList<Fitness>) : RecyclerView.Adapter<FitnessDetailRecyclerAdapter.FitnessViewHolder>() {

    class FitnessViewHolder(val binding : FitnessDetailRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessViewHolder {
        val binding = FitnessDetailRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FitnessViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FitnessViewHolder, position: Int) {
        holder.binding.fitnessDetailUserName.text = getData[position].userName
        holder.binding.fitnessDetailDate.text = "Gün =${getData[position].appointmentDay} Saat = ${getData[position].appointmentHour} : " +
                "${getData[position].appointmentMinute} "
        holder.binding.fitnessDetailStatus.text = "Yapildi"
    }

    override fun getItemCount(): Int {
        return getData.size
    }
}