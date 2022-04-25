package com.satan.estyonetim.loginviews



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.databinding.ActivityLoginBinding
import com.satan.estyonetim.homeviews.AdminHomeActivity
import com.satan.estyonetim.homeviews.HomePageActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth : FirebaseAuth

    private  var userloginEmail : String = ""
     private var userloginPassword : String = ""

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        checkCurrentUser()


        hideSystemBars()

        binding.toRegisterPageText.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        binding.userLoginButton.setOnClickListener {
            validateLoginData()
        }

        binding.ForgotPasswordText.setOnClickListener {
            startActivity(Intent(this,ResetPasswordActivity::class.java))
        }

         binding.toAdminLogin.setOnClickListener {
             startActivity(Intent(this,AdminLoginActivity::class.java))
         }



    }

     private fun hideSystemBars() {
         val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView) ?: return
         windowInsetsController.systemBarsBehavior =
             WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
         windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
     }


     private fun loginUser() {

         userloginEmail = binding.inputUserLoginEmail.text.toString()
         userloginPassword = binding.inputUserLoginPassword.text.toString()

         auth.signInWithEmailAndPassword(userloginEmail,userloginPassword).addOnCompleteListener {
             if (it.isSuccessful) {
                 if (auth.currentUser!!.isEmailVerified) {

                     val currentUser = auth.currentUser?.email
                     Toast.makeText(applicationContext,"Giriş yapıldı : $currentUser",
                         Toast.LENGTH_SHORT).show()

                     startActivity(Intent(this, HomePageActivity::class.java))
                     finish()

                 } else {
                     Toast.makeText(applicationContext,"Lütfen Mail adresinizi doğrulayın !!",
                         Toast.LENGTH_LONG).show()
                 }

             }

         }.addOnFailureListener {
             Toast.makeText(applicationContext,"Giriş başarısız (${it.localizedMessage})",
             Toast.LENGTH_LONG).show()
         }

     }



     private fun validateLoginData() {

         val loginEmail = binding.inputUserLoginEmail.text.toString()
         val loginPassword = binding.inputUserLoginPassword.text.toString()

         if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) {
             binding.inputUserLoginEmail.error = "geçerli bir mail giriniz"

         } else if (TextUtils.isEmpty(loginPassword)) {
             binding.inputUserLoginPassword.error = "Şifre giriniz"

         } else {
             loginUser()
         }


     }

     private fun checkCurrentUser() {
         val currentUser = auth.currentUser
         if (currentUser != null && currentUser.email == "adminest@gmail.com") {
             startActivity(Intent(this, AdminHomeActivity::class.java))
             finish()
         } else if (currentUser != null && auth.currentUser!!.isEmailVerified) {
             startActivity(Intent(this,HomePageActivity::class.java))
             finish()
         }
     }


}