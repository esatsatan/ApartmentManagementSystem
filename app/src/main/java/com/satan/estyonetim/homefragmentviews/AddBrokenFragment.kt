package com.satan.estyonetim.homefragmentviews

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import coil.load
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.satan.estyonetim.R
import com.satan.estyonetim.databinding.FragmentAddBrokenBinding
import com.satan.estyonetim.model.ChatMessage
import java.lang.Exception
import java.util.*


class AddBrokenFragment : Fragment() {

    private var _binding: FragmentAddBrokenBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var storage : FirebaseStorage

    private val GALLERY_REQUEST_CODE = 2

    var selectedImage : Uri? = null
    var selectedBitmap : Bitmap? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBrokenBinding.inflate(inflater, container, false)

        // Realtime Database
        database = FirebaseDatabase.getInstance()
        reference = database.reference

        auth = Firebase.auth
        storage = FirebaseStorage.getInstance()

        getPhoto()

        binding.SaveBroken.setOnClickListener {
            sendMessage()
        }

        return binding.root
    }

    private fun sendMessage() {

        Navigation.findNavController(requireView())
            .navigate(R.id.action_addBrokenFragment_to_ChatFragment)

        savePhotoToStorage()


    }

    private fun getPhoto() {

        binding.setImage.setOnClickListener {

            Dexter.withContext(requireContext())
                .withPermission(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(object : PermissionListener{

                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        goToGallery()
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Toast.makeText(
                            requireContext(),
                            "Depolama iznine izin vermeniz gerekiyor !!",
                            Toast.LENGTH_SHORT
                        ).show()

                        showRotationalDialogForPermission()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        showRotationalDialogForPermission()
                    }

                }).onSameThread().check()
        }
    }

    private fun goToGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null ) {
            //       val bitmap = data?.extras?.get("data") as Bitmap

                selectedImage = data.data

            if (selectedImage != null) {

                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(activity?.contentResolver!!,selectedImage!!)
                    selectedBitmap = ImageDecoder.decodeBitmap(source)
                    binding.setImage.setImageBitmap(selectedBitmap)
                } else {
                    selectedBitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver,selectedImage)
                    binding.setImage.setImageBitmap(selectedBitmap)
                }
            }

        }


    }

    private fun savePhotoToStorage() {

        val uuid = UUID.randomUUID()
        val imageName = "${uuid}.jpg"
        val messageUid = uuid.toString()

        val reference = storage.reference
        val imageReference = reference.child("images").child(imageName)
        if (selectedImage != null) {
            imageReference.putFile(selectedImage!!).addOnSuccessListener {
                val uploadedImage = FirebaseStorage.getInstance().reference.child("images").child(imageName)
                uploadedImage.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl = uri.toString()
                    val email = auth.currentUser!!.email.toString()
                    val comment = binding.CreateChatMessageText.text.toString()
                    val time = Timestamp.now().toDate().toString()

                    // save image url to database
                    val saveData = ChatMessage(comment,email,downloadUrl,time,auth.uid.toString())

                    database.reference.child("Chats").child(messageUid).setValue(saveData).addOnSuccessListener {
                        Toast.makeText(requireContext(),
                        "Mesaj veritabanına Kaydedildi",
                        Toast.LENGTH_SHORT).show()
                    }
                        .addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                it.localizedMessage,Toast.LENGTH_SHORT,
                            ).show()
                        }


                }
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"Resim dosyası yüklenemedi : ${it.localizedMessage} ",Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(requireContext())
            .setMessage(
                "Bu özellik için gerekli izinleri"
                        + "kapatmışsınız gibi gözüküyor . Lütfen ayarlardan gerekli izinleri açın."
            )

            .setPositiveButton("Ayarlara git") { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", tag, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


