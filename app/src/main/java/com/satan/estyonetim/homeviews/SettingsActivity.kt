package com.satan.estyonetim.homeviews


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.satan.estyonetim.R
import com.satan.estyonetim.databinding.ActivitySettingsBinding
import com.squareup.picasso.Picasso
import java.util.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingsBinding
    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var storage : FirebaseStorage

    var imageUri : Uri? = null
    var selectedBitmap : Bitmap? = null
    var photoURL : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth  // initialize
        database = Firebase.firestore

        storage = FirebaseStorage.getInstance()

        binding.settingsSaveChanges.setOnClickListener {
            changeUserData()
        }

        // go to gallery
        val resultLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {

            binding.settingsImageView.setImageURI(it)
            imageUri = it

            if(imageUri != null) {

                val inputStream = contentResolver.openInputStream(it)
                selectedBitmap = BitmapFactory.decodeStream(inputStream)
                binding.settingsImageView.setImageBitmap(selectedBitmap)
            }

        }

        binding.settingsImageView.setOnClickListener {
            resultLauncher.launch("image/*")
        }

        binding.saveProfilePhoto.setOnClickListener {
            savePhoto()
        }

        getUserPhotoFromDatabase()

    }

    private fun savePhoto() {

        val currentUser = "${auth.currentUser!!.email.toString()}.jpg"
        val document = auth.currentUser!!.email.toString()


        val imageReference = storage.reference.child("UserPhotos").child(currentUser)
        if (imageUri != null) {
            imageReference.putFile(imageUri!!).addOnSuccessListener {
                val uploadedImage = FirebaseStorage.getInstance().reference.child("UserPhotos").child(currentUser)
                    uploadedImage.downloadUrl.addOnSuccessListener { uri ->

                        photoURL = uri.toString()

                        database.collection("UsersInfo").document(document)
                            .update("photoUrl",photoURL).addOnSuccessListener {
                                Toast.makeText(
                                    applicationContext, "Fotoğraf url i güncellendi",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Hata Fotoğraf eklenemedi : ${it.localizedMessage}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        savePhotoToFitnessAppointment()
                    }
            }
        }
    }

    private fun getUserPhotoFromDatabase() {
        val userDocument = auth.currentUser!!.email.toString()

        database.collection("UsersInfo").document(userDocument).get()
            .addOnSuccessListener { documents ->
                if (documents != null) {
                    val getUserPhoto = documents.get("photoUrl").toString()
                    Picasso.get().load(getUserPhoto).into(binding.settingsImageView)
                }
                else {
                    Toast.makeText(applicationContext,"Böyle bir döküman yok",
                    Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                    Toast.makeText(applicationContext,"Hata Dosya alınamadı",
                    Toast.LENGTH_SHORT).show()
            }
    }

    private fun savePhotoToFitnessAppointment() {
        val currentUser = auth.currentUser!!.email.toString()

        database.collection("fitnessAppointment")
            .document(currentUser).update("photoUrl",photoURL).addOnSuccessListener {
                Toast.makeText(applicationContext,"fitness bölümüne url eklendi",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Hata fitnesa foto eklenemedi : ${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
            }
    }

    private fun changeUserData() {

        val userName = binding.changedName.text.toString()
        val phoneNumber = binding.changedPhoneNo.text.toString()
        val apartmentNumber = binding.changedApartmentNo.text.toString()
        val roomNumber = binding.changedRoomNo.text.toString()

        val userEmail = auth.currentUser!!.email!!

        if (userName.isNotEmpty()) {
            database.collection("UsersInfo").document(userEmail).update(
                "name",userName
            )
                .addOnSuccessListener {
                    Toast.makeText(applicationContext,"İsim değişikliği başarılı.",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext,"Hata ! İsim değiştirilemedi : ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
                }
        }
        else if (phoneNumber.isNotEmpty()){
            database.collection("UsersInfo").document(userEmail).update(
                "phoneNumber",phoneNumber
            )
                .addOnSuccessListener {
                    Toast.makeText(applicationContext,"Telefon numarası değiştirildi.",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext,"Hata ! numara değiştirilemedi : ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
                }
        }
        else if (apartmentNumber.isNotEmpty()) {
            database.collection("UsersInfo").document(userEmail).update(
                "apartmentNumber",apartmentNumber
            )
                .addOnSuccessListener {
                    Toast.makeText(applicationContext,"Blok numarası değiştirildi.",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext,"Hata ! blok değiştirilemedi : ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
                }
        }
        else if (roomNumber.isNotEmpty()) {
            database.collection("UsersInfo").document(userEmail).update(
                "roomNumber",roomNumber
            )
                .addOnSuccessListener {
                    Toast.makeText(applicationContext,"Daire numarası değiştirildi.",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext,"Hata ! daire no değiştirilemedi : ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
                }
        }
        else {
            Toast.makeText(applicationContext,"Kullanıcı bilgisi değiştirilmedi.",Toast.LENGTH_SHORT).show()
        }

    }
}