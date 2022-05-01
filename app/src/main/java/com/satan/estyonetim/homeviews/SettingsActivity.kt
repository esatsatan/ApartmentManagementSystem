package com.satan.estyonetim.homeviews

import android.widget.Toast



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingsBinding
    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth  // initialize
        database = Firebase.firestore

        binding.settingsSaveChanges.setOnClickListener {
            changeUserData()
        }


    }

    fun changeUserData() {

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