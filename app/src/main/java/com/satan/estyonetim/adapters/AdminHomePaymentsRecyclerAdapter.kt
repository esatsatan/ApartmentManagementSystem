package com.satan.estyonetim.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satan.estyonetim.databinding.PaymentDetailRowBinding
import com.satan.estyonetim.model.PaymentAttributes

class AdminHomePaymentsRecyclerAdapter(var paymentList : ArrayList<PaymentAttributes>)
    : RecyclerView.Adapter<AdminHomePaymentsRecyclerAdapter.AdminPaymentViewHolder>() {

    class AdminPaymentViewHolder(val binding : PaymentDetailRowBinding)  : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminPaymentViewHolder {
        val binding = PaymentDetailRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdminPaymentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminPaymentViewHolder, position: Int) {
        holder.binding.paymentDetailUserName.text = paymentList[position].payName
        holder.binding.paymentDetailPayTime.text = paymentList[position].time
        holder.binding.paymentApartNo.text = paymentList[position].apartNo
        holder.binding.paymentRoomNo.text = paymentList[position].roomNo
    }

    override fun getItemCount(): Int {
       return paymentList.size
    }
}