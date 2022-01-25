package com.satan.estyonetim.loginviews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.databinding.ActivityAdminLoginBinding
import com.satan.estyonetim.homeviews.AdminHomeActivity
import com.satan.estyonetim.homeviews.HomePageActivity

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminLoginBinding
    private lateinit var auth : FirebaseAuth

    private var adminEmail : String = ""
    private var adminPassword : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.userAdminLoginButton.setOnClickListener {
            validateAdminData()
        }

    }

    private fun adminLogin() {
        adminEmail = binding.inputAdminLoginEmail.text.toString()
        adminPassword = binding.inputAdminLoginPassword.text.toString()

        if (adminEmail == "adminest@gmail.com") {

            auth.signInWithEmailAndPassword(adminEmail,adminPassword).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext,"yönetici girişi başarılı",
                        Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this,AdminHomeActivity::class.java))
                    finishAffinity()
                }
            }
                .addOnFailureListener {
                    Toast.makeText(applicationContext,"Giriş başarısız : ${it.localizedMessage}",
                        Toast.LENGTH_SHORT).show()
                }


        }  else {
            Toast.makeText(applicationContext,"Yönetici yetkisine sahip değilsiniz!",
            Toast.LENGTH_SHORT).show()
        }



    }

    private fun validateAdminData() {

        val email = binding.inputAdminLoginEmail.text.toString()
        val password = binding.inputAdminLoginPassword.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.inputAdminLoginEmail.error = "Geçerli bir email giriniz.."

        } else if (password.isEmpty()) {
            binding.inputAdminLoginPassword.error = "Şifre giriniz "

        } else {
            adminLogin()
        }

    }











}













