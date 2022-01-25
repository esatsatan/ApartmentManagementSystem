package com.satan.estyonetim.homeviews

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.R
import com.satan.estyonetim.databinding.ActivityHomePageBinding
import com.satan.estyonetim.databinding.NavigationHeaderBinding
import com.satan.estyonetim.loginviews.LoginActivity

class HomePageActivity : AppCompatActivity() {


     private lateinit var binding: ActivityHomePageBinding
     private lateinit var auth : FirebaseAuth
     private lateinit var toggle : ActionBarDrawerToggle
     lateinit var view : View

     private lateinit var sharedPreferences: SharedPreferences

     
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth    // initialize
        checkCurrentUser()

        // bottom navigation bar setups
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.paymenttFragment,R.id.settingsFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)



        sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        getSharedPreferencesData()

        toggle = ActionBarDrawerToggle(this,binding.drawerLayout, R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

          binding.navigationView.setNavigationItemSelectedListener {
              when(it.itemId) {

                  R.id.nav_login -> userSignOut()
              }
              true
          }





    }

     private fun getSharedPreferencesData() {

         val viewHeader = binding.navigationView.getHeaderView(0) // access navigationview header values
         val navViewHeaderBinding : NavigationHeaderBinding = NavigationHeaderBinding.bind(viewHeader)
         // bind navHeader with view binding and access variables.


         val savedName = sharedPreferences.getString("STRING_USERNAME","")
         navViewHeaderBinding.loggedUserName.text = savedName

         val savedPhone = sharedPreferences.getString("STRING_PHONE","")
         navViewHeaderBinding.loggedUserPhone.text = savedPhone

         val savedApartNo = sharedPreferences.getString("STRING_APART","")
         navViewHeaderBinding.blockNumber.text = savedApartNo

        val savedRoomNo = sharedPreferences.getString("STRING_ROOM","")
         navViewHeaderBinding.roomNumber.text = savedRoomNo

     }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkCurrentUser() {

        val firebaseUser = auth.currentUser
        if (firebaseUser != null ) {
            val email = firebaseUser.email

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun userSignOut() {
        auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
    }






}






