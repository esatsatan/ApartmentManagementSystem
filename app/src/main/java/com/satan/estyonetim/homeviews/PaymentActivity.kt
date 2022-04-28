package com.satan.estyonetim.homeviews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.databinding.ActivityPaymentBinding
import com.satan.estyonetim.loginviews.LoginActivity
import com.satan.estyonetim.model.PaymentAttributes
import java.time.LocalDateTime

class PaymentActivity : AppCompatActivity() {

    private lateinit var database : FirebaseFirestore
    private lateinit var binding : ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.firestore

        binding.payButton.setOnClickListener {
           savePaymentInformation()
        }



    }

    private fun savePaymentInformation() {

        val name = binding.paymentUserName.text.toString()
        val apartmentNumber = binding.paymentUserApartmentNo.text.toString()
        val roomNumber = binding.paymentUserRoomNumber.text.toString()
        val time = Timestamp.now().toDate().toString()


        val addData = PaymentAttributes(name,apartmentNumber,roomNumber, time)

        database.collection("PaymentSubscription").document().set(addData)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext,"Ödeme yapıldı",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomePageActivity::class.java))
                }
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Hata : ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
            }



    }




}