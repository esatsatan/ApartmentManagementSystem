package com.satan.estyonetim.adminhomeviews

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.graphics.blue
import androidx.core.graphics.red
import androidx.core.graphics.toColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.R
import com.satan.estyonetim.databinding.FragmentCreateBasketballGameBinding
import com.satan.estyonetim.model.Basketball
import java.util.*


class CreateBasketballGameFragment : Fragment(),DatePickerDialog.OnDateSetListener ,TimePickerDialog.OnTimeSetListener {

    private var _binding : FragmentCreateBasketballGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

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

        pickData()

        binding.createBasketballButton.setOnClickListener {
            createBasketballActivity()
        }


        return binding.root
    }

    private fun createBasketballActivity() {


        val gameDay = savedDay.toString()
        val gameHour = savedHour.toString()
        val gameMinute = savedMinute.toString()
        val organizer = auth.currentUser!!.email.toString()
        val totalPersonCount = binding.totalPersonCount.text.toString()

        val basketball = Basketball(organizer,gameDay,gameHour,gameMinute,totalPersonCount,
            currentPersonCount)

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