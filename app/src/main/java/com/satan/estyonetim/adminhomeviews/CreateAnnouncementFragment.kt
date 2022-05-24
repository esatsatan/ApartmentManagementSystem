package com.satan.estyonetim.adminhomeviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.databinding.FragmentCreateAnnouncementBinding
import java.time.LocalDateTime
import java.util.*


class CreateAnnouncementFragment : Fragment() {

    private var _binding : FragmentCreateAnnouncementBinding? = null
    private val binding get() = _binding!!

    private lateinit var database : FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCreateAnnouncementBinding.inflate(inflater,container,false)

        database = Firebase.firestore


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            share()
        }

    }


    fun share () {

        val title = binding.titleText.text.toString()
        val description = binding.descriptionText.text.toString()
        val time = Timestamp.now()


        if (title.isNotEmpty() && description.isNotEmpty()) {

            val postHashMap = hashMapOf<String ,Any>()
            postHashMap["title"] = title
            postHashMap.put("description",description)
            postHashMap.put("time",time)

            database.collection("Notifications").add(postHashMap).addOnCompleteListener {
                if (it.isSuccessful) {
                    val action = CreateAnnouncementFragmentDirections.actionCreateAnnouncementFragmentToPublishAnnouncementFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                }
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
                }


        } else {
            Toast.makeText(requireContext(),"Boş alanları doldurunuz",Toast.LENGTH_SHORT).show()
        }


    }


}