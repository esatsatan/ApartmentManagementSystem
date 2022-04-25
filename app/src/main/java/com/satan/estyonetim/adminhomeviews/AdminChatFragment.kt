package com.satan.estyonetim.adminhomeviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.satan.estyonetim.adapters.AdminChatRecyclerAdapter
import com.satan.estyonetim.databinding.FragmentAdminChatBinding
import com.satan.estyonetim.model.ChatMessage


class AdminChatFragment : Fragment() {

    private var _binding : FragmentAdminChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var reference : DatabaseReference
    private lateinit var database : FirebaseDatabase

    private lateinit var adminChatRecyclerAdapter : AdminChatRecyclerAdapter
    var getChatMessages = ArrayList<ChatMessage>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAdminChatBinding.inflate(inflater,container,false)

        database = FirebaseDatabase.getInstance()
        reference = database.reference

        // bind recyclerView Adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.adminChatRecyclerView.layoutManager = layoutManager

        adminChatRecyclerAdapter = AdminChatRecyclerAdapter(getChatMessages)
        binding.adminChatRecyclerView.adapter = adminChatRecyclerAdapter

        getChatData()

        return binding.root
    }

    private fun getChatData() {
        reference.child("Chats").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                getChatMessages.clear()
                for (getChatData in snapshot.children) {
                    val currentMessage = getChatData.getValue(ChatMessage::class.java)
                    getChatMessages.add(currentMessage!!)
                }
                adminChatRecyclerAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),error.message,Toast.LENGTH_LONG).show()
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}