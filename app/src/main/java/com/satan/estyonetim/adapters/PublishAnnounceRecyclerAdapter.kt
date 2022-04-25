package com.satan.estyonetim.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satan.estyonetim.adminhomeviews.PublishAnnouncementFragment
import com.satan.estyonetim.databinding.AdminAnnonunceRowBinding
import com.satan.estyonetim.model.Admin
import java.util.ArrayList

class PublishAnnounceRecyclerAdapter(private val postList: ArrayList<Admin>)
    : RecyclerView.Adapter<PublishAnnounceRecyclerAdapter.ViewHolder>() {


     class ViewHolder(val binding : AdminAnnonunceRowBinding) : RecyclerView.ViewHolder(binding.root) {


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = AdminAnnonunceRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.recyclerRowSubject.text = postList[position].title
        holder.binding.recyclerRowDescriptionArticle.text = postList[position].description

    }


    override fun getItemCount(): Int {
        return postList.size
    }



}