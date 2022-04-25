package com.satan.estyonetim.loginviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityResetPasswordBinding
    private lateinit var auth : FirebaseAuth

    private var userEmail : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.resetPasswordButton.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        userEmail = binding.inputResetText.text.toString()

        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            binding.inputResetText.error = "Geçerli bir email adresi giriniz !!"

        } else {
            sendResetEmailLink()
        }

    }

    private fun sendResetEmailLink() {
        val userEmail = binding.inputResetText.text.toString()

        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(applicationContext,"Şifre sıfırlama linki Mail adresine gönderildi",
                    Toast.LENGTH_SHORT).show()
            }
        }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Parola Resetleme linki gönderilemedi : ${it.localizedMessage}",
                    Toast.LENGTH_LONG).show()
            }
    }

}