package com.satan.estyonetim.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satan.estyonetim.databinding.AdminChatRowBinding
import com.satan.estyonetim.model.ChatMessage
import com.squareup.picasso.Picasso

class AdminChatRecyclerAdapter (var getMessages : ArrayList<ChatMessage>) :
    RecyclerView.Adapter<AdminChatRecyclerAdapter.AdminChatViewHolder>() {

    class AdminChatViewHolder(val binding : AdminChatRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminChatViewHolder {
        val binding = AdminChatRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdminChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminChatViewHolder, position: Int) {
        holder.binding.adminChatMessageText.text = getMessages[position].message
        holder.binding.AdmiMessageSenderEmail.text = getMessages[position].email
        holder.binding.AdminMessageTime.text = getMessages[position].time

        // picasso
        Picasso.get().load(getMessages[position].photoUrl).into(holder.binding.adminSelectedImage)


    }

    override fun getItemCount(): Int {
        return getMessages.size
    }
}