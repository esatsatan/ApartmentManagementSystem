package com.satan.estyonetim


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.satan.estyonetim.loginviews.LoginActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}