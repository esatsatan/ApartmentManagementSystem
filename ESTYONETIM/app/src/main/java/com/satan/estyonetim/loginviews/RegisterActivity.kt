package com.satan.estyonetim.loginviews



import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth

    val database = Firebase.firestore

    private lateinit var sharedPreferences: SharedPreferences


    private  var userEmail : String = ""
    private var userPassword : String = ""
    private var nameSurname : String = ""
    private var phoneNumber : String = ""
    private var apartmentNumber : String = ""
    private var roomNumber : String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth    // initialize authentication

        sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)


        binding.userRegisterButton.setOnClickListener {
            validateRegisterData()
        }




    }

    private fun registerUser() {

        userEmail = binding.inputRegisterEmail.text.toString()
        userPassword = binding.inputRegisterPassword.text.toString()
        nameSurname = binding.inputRegisterName.text.toString()
        phoneNumber = binding.inputRegisterPhoneNumber.text.toString()
        apartmentNumber = binding.inputRegisterApartmentNumber.text.toString()
        roomNumber = binding.inputRegisterRoomNumber.text.toString()


        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { it ->
            if (it.isSuccessful) {
                auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(applicationContext,"Kayıt başarılı.. ${auth.currentUser!!.email} adresine doğrulama linki gönderildi  ",
                            Toast.LENGTH_LONG).show()

                        val usersMap = hashMapOf<String,Any>()
                        usersMap["user_email"] = userEmail
                        usersMap["user_password"] = userPassword
                        usersMap["user_name"] = nameSurname
                        usersMap["user_phone"] = phoneNumber
                        usersMap["user_apartNo"] = apartmentNumber
                        usersMap["user_roomNo"] = roomNumber

                        database.collection("UsersInfo").add(usersMap).addOnCompleteListener {
                            if (it.isSuccessful) {
                               Toast.makeText(applicationContext,"Kullanıcı oluşturuldu",Toast.LENGTH_SHORT).show()
                                saveDataWithSharedPreferences()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(applicationContext,"Kullanıcı veritabanına kayıt edilemedi : ${it.localizedMessage}",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }?.addOnFailureListener {
                    Toast.makeText(applicationContext,"Doğrulama linki gönderilemedi ${it.localizedMessage}",
                        Toast.LENGTH_LONG).show()
                }
            }

        }.addOnFailureListener {
            Toast.makeText(applicationContext,"Kullanıcı oluşturulamadı .. (${it.localizedMessage})"
                ,Toast.LENGTH_LONG).show()
        }

    }



    private fun validateRegisterData() {
        val email = binding.inputRegisterEmail.text.toString()
        val password = binding.inputRegisterPassword.text.toString()
        val username = binding.inputRegisterName.text.toString()
        val phone = binding.inputRegisterPhoneNumber.text.toString()
        val apartmentNumber = binding.inputRegisterApartmentNumber.text.toString()
        val roomNumber = binding.inputRegisterRoomNumber.text.toString()


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.inputRegisterEmail.error = "Yanlış email formatı "

        } else if (TextUtils.isEmpty(email)) {
            binding.inputRegisterEmail.error = "email giriniz"

        }
        else if (TextUtils.isEmpty(password)) {
            binding.inputRegisterPassword.error = "Şifre giriniz"

        } else if (TextUtils.isEmpty(username)) {
            binding.inputRegisterName.error = "İsim Soyisim giriniz"

        } else if (TextUtils.isEmpty(phone)) {
            binding.inputRegisterPhoneNumber.error = "Telefon numarası giriniz"

        } else if (TextUtils.isEmpty(apartmentNumber)) {
            binding.inputRegisterApartmentNumber.error = "Blok numarası giriniz"

        } else if (TextUtils.isEmpty(roomNumber)) {
            binding.inputRegisterRoomNumber.error = "Daire numarası giriniz"
        }
        else if (password.length <=5) {
            binding.inputRegisterPassword.error = "şifre en az 6 karakterden oluşmalıdır . "
        }
        else {
            registerUser()
        }

    }


    private fun saveDataWithSharedPreferences() {

        val username : String = binding.inputRegisterName.text.toString()
        val phoneNumber : String = binding.inputRegisterPhoneNumber.text.toString()
        val apartmentNumber : String = binding.inputRegisterApartmentNumber.text.toString()
        val roomNumber : String = binding.inputRegisterRoomNumber.text.toString()


        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_USERNAME",username)
            putString("STRING_PHONE",phoneNumber)
            putString("STRING_APART",apartmentNumber)
            putString("STRING_ROOM",roomNumber)
        }.apply()

        Toast.makeText(applicationContext,"Sharedpreferences a Kaydedildi",Toast.LENGTH_SHORT).show()
    }










}















