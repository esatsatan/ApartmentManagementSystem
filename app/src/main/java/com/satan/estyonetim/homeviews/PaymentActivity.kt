package com.satan.estyonetim.homeviews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.databinding.ActivityPaymentBinding
import com.satan.estyonetim.loginviews.LoginActivity
import com.satan.estyonetim.model.PaymentAttributes
import com.satan.estyonetim.utils.PaymentViewModel
import java.time.LocalDateTime

class PaymentActivity : AppCompatActivity() {

    private lateinit var database : FirebaseFirestore
    private lateinit var binding : ActivityPaymentBinding

    private lateinit var paymentViewModel : PaymentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.firestore   // initialize database
        paymentViewModel = ViewModelProvider(this)[PaymentViewModel::class.java]    // initialize viewModel


        binding.payButton.setOnClickListener {
            savePaymentInformation()
        }



    }

    private fun savePaymentInformation() {

        val name = binding.paymentUserName.text.toString()
        val apartmentNumber = binding.paymentUserApartmentNo.text.toString()
        val roomNumber = binding.paymentUserRoomNumber.text.toString()
        val time = Timestamp.now().toDate().toString()

        val addData = PaymentAttributes(0,name,apartmentNumber,roomNumber, time)

        if (name.isNotEmpty() && apartmentNumber.isNotEmpty() && roomNumber.isNotEmpty() && time.isNotEmpty()) {

            database.collection("PaymentSubscription").document().set(addData)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(applicationContext,"Ödeme yapıldı",Toast.LENGTH_SHORT).show()

                    }
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext,"Hata : ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
                }

            // Add data to Sqlite (local database)
            paymentViewModel.addPayment(addData)
            Toast.makeText(applicationContext,"Veriler SQLİTE a kaydedildi .",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomePageActivity::class.java))


        } else {
            Toast.makeText(applicationContext,"Gerekli alanları doldurunuz !",Toast.LENGTH_SHORT).show()
        }







    }






}