package com.satan.estyonetim.homeviews


import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.R
import com.satan.estyonetim.databinding.ActivityAdminHomeBinding
import com.satan.estyonetim.loginviews.AdminLoginActivity
import com.satan.estyonetim.loginviews.LoginActivity

class AdminHomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAdminHomeBinding
    private lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        checkCurrentUser()

        val adminBottomNavigationView = findViewById<BottomNavigationView>(R.id.adminBottomNavigationView)
        val navController = findNavController(R.id.adminFragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.adminHomeFragment,R.id.displayPersonalFragmentt,R.id.publishAnnouncementFragment))

        setupActionBarWithNavController(navController,appBarConfiguration)
        adminBottomNavigationView.setupWithNavController(navController)


    }

    private fun managerLogout() {
        auth.signOut()
        startActivity(Intent(this,AdminLoginActivity::class.java))
        finish()
    }

    private fun checkCurrentUser() {

        val firebaseUser = auth.currentUser
        if (firebaseUser != null ) {
            val email = firebaseUser.email

        } else {
            startActivity(Intent(this, AdminLoginActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_options_menu,menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.admin_logout_button -> managerLogout()

        }
        return super.onOptionsItemSelected(item)
    }




}