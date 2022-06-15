package com.satan.estyonetim.adminhomeviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.R
import com.satan.estyonetim.adapters.PublishAnnounceRecyclerAdapter
import com.satan.estyonetim.databinding.FragmentPublishAnnouncementBinding
import com.satan.estyonetim.model.Admin
import java.util.ArrayList

class PublishAnnouncementFragment : Fragment() {

    private var _binding : FragmentPublishAnnouncementBinding? = null
    private val binding get() = _binding!!

    private lateinit var database : FirebaseFirestore
    private lateinit var recyclerAdapter: PublishAnnounceRecyclerAdapter

    var postList = ArrayList<Admin>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPublishAnnouncementBinding.inflate(inflater,container,false)

        database = Firebase.firestore

        val layoutManager = LinearLayoutManager(requireContext())

        binding.AnnouncementRecyclerView.layoutManager = layoutManager

        recyclerAdapter = PublishAnnounceRecyclerAdapter(postList)

        binding.AnnouncementRecyclerView.adapter = recyclerAdapter


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_publishAnnouncementFragment_to_createAnnouncementFragment)
        }

        getDatas()


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun getDatas() {
        database.collection("Notifications").orderBy("time", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_LONG).show()
            } else {
               if (snapshot != null) {
                   if (!snapshot.isEmpty) {

                       val documents = snapshot.documents   // liste içerisinde verilen dökümanlar

                       postList.clear()

                       for (document in documents) {
                           val descriptionTitle = document.get("title") as String
                           val description = document.get("description") as String
                           val getTime = document.get("time") as String

                           val posts = Admin(descriptionTitle,description,getTime)
                           postList.add(posts)

                       }
                       recyclerAdapter.notifyDataSetChanged()
                   }
               }
            }
        }
    }





}