package com.satan.estyonetim.adminhomeviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.R
import com.satan.estyonetim.adminhomeviews.DisplayPersonalFragment.Companion.staffRecyclerAdapter
import com.satan.estyonetim.databinding.FragmentPublishAnnouncementBinding
import com.satan.estyonetim.databinding.FragmentStaffDetailBinding
import com.satan.estyonetim.model.Staff


class StaffDetailFragment : Fragment() {

    private var _binding : FragmentStaffDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : FirebaseFirestore

    val args : StaffDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStaffDetailBinding.inflate(inflater,container,false)

        database = Firebase.firestore

        getDataFromNavArgs()

        binding.staffDeleteButton.setOnClickListener {
            deleteStaffFromDatabase()
        }

        return binding.root


    }


    private fun getDataFromNavArgs() {
        binding.staffDetailName.text = args.argsName
        binding.staffDetailPosition.text = args.argsPosition
        binding.staffDetailIdentityNo.text = args.argsIdentity
        binding.staffDetailSalary.text = args.argsSalary.toString()
    }

    private fun deleteStaffFromDatabase() {

        database.collection("staffs").document(args.argsIdentity).delete()
            .addOnCompleteListener {
                Toast.makeText(requireContext(),"${binding.staffDetailName.text} veritabanından silindi",
                Toast.LENGTH_SHORT).show()

                // navigate display personal screen
                Navigation.findNavController(requireView()).navigate(R.id.action_staffDetailFragment_to_displayPersonalFragmentt)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Silme işlemi başarısız : ${it.localizedMessage}",
                Toast.LENGTH_SHORT).show()
            }
    }





}