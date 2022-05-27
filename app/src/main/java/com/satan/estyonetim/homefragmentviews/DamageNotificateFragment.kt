package com.satan.estyonetim.homefragmentviews


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.R
import com.satan.estyonetim.adapters.ChatRecyclerAdapter
import com.satan.estyonetim.databinding.FragmentDamageNotificateBinding
import com.satan.estyonetim.model.ChatMessage
import java.util.*


class DamageNotificateFragment : Fragment() {
    private var _binding : FragmentDamageNotificateBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatRecyclerAdapter: ChatRecyclerAdapter
    var chatMessages = ArrayList<ChatMessage>()

    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDamageNotificateBinding.inflate(inflater,container,false)

        setHasOptionsMenu(true)

        // initalize database reference
        database = FirebaseDatabase.getInstance()
        reference = database.reference

        // bind recyclerview with layout
        val layoutManager = LinearLayoutManager(requireContext())   // select which our layout is look like
        binding.chatRecyclerView.layoutManager = layoutManager

        chatRecyclerAdapter = ChatRecyclerAdapter(chatMessages)     // bind data with adapter
        binding.chatRecyclerView.adapter = chatRecyclerAdapter

        getSnapshotData()

        return binding.root
    }

    fun getSnapshotData() {
        reference.child("Chats").orderByChild("time")
            .addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                chatMessages.clear()
                for (chatData in snapshot.children) {
                    val currentMessage = chatData.getValue(ChatMessage::class.java)
                    chatMessages.add(currentMessage!!)
                }
                    chatRecyclerAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),
                "Data getirilemedi : ${error.message}",
                Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_add_broken,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_broken) {
            Navigation.findNavController(requireView()).navigate(R.id.action_ChatFragment_to_addBrokenFragment)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}