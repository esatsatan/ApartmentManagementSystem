package com.satan.estyonetim.adminhomeviews

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.R
import com.satan.estyonetim.databinding.FragmentAddStaffBinding
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class AddStaffFragment : Fragment() ,EasyPermissions.PermissionCallbacks {

    private lateinit var database : FirebaseFirestore

    companion object {
        const val PERMISSION_STORAGE_REQUEST_CODE = 1
    }

    private var _binding : FragmentAddStaffBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddStaffBinding.inflate(inflater,container,false)

        database = Firebase.firestore

        binding.selectImage.setOnClickListener {
            Toast.makeText(requireContext(),"Tıklandı",Toast.LENGTH_SHORT).show()
            if (hasGalleryPermision()) {
                // izin alındı yapılacak işlemler ..


            } else {
                // izin alınmadı
                requestGalleryPermission()
            }
        }

        binding.staffAddButton.setOnClickListener {
            saveStaffToDatabase()
        }



        return binding.root
    }

    private fun hasGalleryPermision() =
        EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    private fun requestGalleryPermission() {
        EasyPermissions.requestPermissions(
            this,
            "Fotoğraf seçimi için izin verilmelidir .",
            PERMISSION_STORAGE_REQUEST_CODE,
            Manifest.permission.READ_EXTERNAL_STORAGE

        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)

    }


    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, listOf(perms.first()))) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestGalleryPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(requireContext(),
            "İzin verildi",
            Toast.LENGTH_SHORT).show()

    }

    fun saveStaffToDatabase() {
        val name = binding.addedName.text.toString()
        val position = binding.addedPosition.text.toString()
        val identityNo = binding.addedIdentityNumber.text.toString()
        val salary = binding.addedSalary.text.toString()

        if (name.isNotEmpty() && position.isNotEmpty() && identityNo.isNotEmpty() && salary.isNotEmpty()) {

            val staff = hashMapOf(
                "name" to name,
                "position" to position,
                "identity" to identityNo,
                "salary" to salary
            )

            database.collection("staffs").document(identityNo).set(staff).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(),"Çalışan ,veritabanına eklendi .",Toast.LENGTH_SHORT).show()
                    binding.addedName.setText("")
                    binding.addedPosition.setText("")
                    binding.addedIdentityNumber.setText("")
                    binding.addedSalary.setText("")

                    Navigation.findNavController(requireView()).navigate(R.id.action_addStaffFragment_to_displayPersonalFragmentt)

                }

                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"Ekleme işlemi başarısız ! ( ${it.localizedMessage})",
                    Toast.LENGTH_SHORT).show()
                }
            }
         else {
            Toast.makeText(requireContext(),
                "Gerekli alanları doldurunuz !",
                Toast.LENGTH_SHORT).show()
        }
    }

}