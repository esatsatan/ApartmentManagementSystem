package com.satan.estyonetim.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satan.estyonetim.databinding.PaymentDetailRowBinding
import com.satan.estyonetim.model.PaymentAttributes
import java.util.ArrayList

class PaymentDetailRecyclerAdapter(var paymentInformation : ArrayList<PaymentAttributes>) :
    RecyclerView.Adapter<PaymentDetailRecyclerAdapter.PaymentDetailViewHolder>() {

    class PaymentDetailViewHolder(val binding : PaymentDetailRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentDetailViewHolder {
        val binding = PaymentDetailRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PaymentDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentDetailViewHolder, position: Int) {

        holder.binding.paymentDetailUserName.text = paymentInformation[position].payName
        holder.binding.paymentApartNo.text = paymentInformation[position].apartNo
        holder.binding.paymentRoomNo.text = paymentInformation[position].roomNo
        holder.binding.paymentDetailPayTime.text = paymentInformation[position].time

        holder.binding.paymentDetailPayStatus.text = "Ödendi"

    }

    override fun getItemCount(): Int {
        return paymentInformation.size
    }


}