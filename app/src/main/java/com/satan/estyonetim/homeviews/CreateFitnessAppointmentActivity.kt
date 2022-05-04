package com.satan.estyonetim.homeviews

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.satan.estyonetim.databinding.ActivityCreateFitnessAppointmentBinding
import com.satan.estyonetim.utils.NotificationWorker
import java.util.*

class CreateFitnessAppointmentActivity : AppCompatActivity() ,DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    private lateinit var binding : ActivityCreateFitnessAppointmentBinding

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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateFitnessAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pickDate()


    }

    private fun getDateTimeCalendar() {
        val cal : Calendar  = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun pickDate() {

        binding.timePickerButton.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(this,this,year,month,day).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()
        TimePickerDialog(this,this,hour,minute,true).show()


    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        binding.selectedDate.text = "Tarih = $savedDay-$savedMonth-$savedYear\n"
        binding.selectedTime.text = "Saat = $savedHour : $savedMinute"

        notification()
    }

    private fun notification() {

        val notificationRequest : WorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .build()

        WorkManager.getInstance(this).enqueue(notificationRequest)

    }
}