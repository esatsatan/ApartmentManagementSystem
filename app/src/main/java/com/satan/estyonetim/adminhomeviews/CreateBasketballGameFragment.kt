package com.satan.estyonetim.adminhomeviews

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.satan.estyonetim.databinding.FragmentCreateBasketballGameBinding
import com.satan.estyonetim.model.Basketball
import com.squareup.picasso.Picasso
import java.util.*


class CreateBasketballGameFragment : Fragment(),DatePickerDialog.OnDateSetListener ,TimePickerDialog.OnTimeSetListener {

    private var _binding : FragmentCreateBasketballGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var storage : FirebaseStorage

    var imageUri : Uri? = null
    var selectedBitmap : Bitmap? = null
    var photoURL : String = ""

    companion object {
        var currentPersonCount = 0
    }

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCreateBasketballGameBinding.inflate(inflater,container,false)

        database = Firebase.firestore
        auth = Firebase.auth
        storage = Firebase.storage

        binding.organizer.text = auth.currentUser!!.email.toString()

        pickData()

        binding.createBasketballButton.setOnClickListener {
            createBasketballActivity()
        }

        // go to gallery
        val resultLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {

            binding.selectGameType.setImageURI(it)
            imageUri = it

            if(imageUri != null) {
                Picasso.get().load(it).into(binding.selectGameType)

            }

        }

        binding.selectGameType.setOnClickListener {
            resultLauncher.launch("image/*")
        }


        return binding.root
    }

    private fun createBasketballActivity() {


        val gameDay = savedDay.toString()
        val gameHour = savedHour.toString()
        val gameMinute = savedMinute.toString()
        val organizer = auth.currentUser!!.email.toString()
        val totalPersonCount = binding.totalPersonCount.text.toString()

        selectGameType(gameDay,gameHour,gameMinute,organizer,totalPersonCount)



    }

    private fun selectGameType(day : String ,hour : String ,minute : String ,organizer : String , totalPerson : String) {

        val uuid = UUID.randomUUID()
        val imageName = "${uuid}.jpg"

        val reference = storage.reference
        val imageReference = reference.child("activityTypePhoto").child(imageName)
        if (imageUri != null) {
            imageReference.putFile(imageUri!!).addOnSuccessListener {
                val uploadedImage = FirebaseStorage.getInstance().reference.child("activityTypePhoto").child(imageName)
                uploadedImage.downloadUrl.addOnSuccessListener { uri ->

                    val downloadUrl = uri.toString()

                    val basketball = Basketball(organizer,
                        day,hour,minute,totalPerson, currentPersonCount,"yapılacak",downloadUrl)

                    database.collection("BasketballActivity").document().set(basketball)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(requireContext(),"Veriler kaydedildi",Toast.LENGTH_SHORT).show()

                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(),"Başarısız işlem : ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
                        }
                }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(),"Hata ! url indirilemedi : ${it.localizedMessage}",
                            Toast.LENGTH_SHORT).show()
                    }
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"Hata ! dosya yüklenemedi : ${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun pickData() {
        binding.selectDate.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(requireContext(),this,year,month,day).show()
        }
    }

    private fun getDateTimeCalendar() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)


        //  val formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar)

    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()

        TimePickerDialog(requireContext(),this,hour,minute,true).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        binding.selectedDate.text = "Tarih : $savedDay/$savedMonth/$savedYear Saat : $savedHour - $savedMinute"

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}