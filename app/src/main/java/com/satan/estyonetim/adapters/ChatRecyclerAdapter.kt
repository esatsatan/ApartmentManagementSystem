package com.satan.estyonetim.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satan.estyonetim.databinding.ChatRowBinding
import com.satan.estyonetim.model.ChatMessage
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ChatRecyclerAdapter(var messages : ArrayList<ChatMessage> ) :
    RecyclerView.Adapter<ChatRecyclerAdapter.ChatViewHolder>() {

    class ChatViewHolder(val binding : ChatRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.binding.chatMessageText.text = messages[position].message
        holder.binding.messageSenderEmail.text = messages[position].email
        holder.binding.messageTime.text = messages[position].time

        // set image to imageview using picasso library
        Picasso.get().load(messages[position].photoUrl).into(holder.binding.selectedImage)


    }

    override fun getItemCount(): Int {
        return messages.size
    }


}

