package com.satan.estyonetim.homeviews

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.databinding.ActivityCreateFitnessAppointmentBinding
import com.satan.estyonetim.model.Fitness
import com.satan.estyonetim.utils.*
import com.satan.estyonetim.utils.Notification
import java.util.*

class CreateFitnessAppointmentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateFitnessAppointmentBinding
    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    var selectedHour : String? = null
    var selectedMinute : String? = null
    var selectedDay : String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateFitnessAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.firestore
        auth = Firebase.auth

      //  pickDate()
        createNotification()
        binding.createFitnessAppointmentButton.setOnClickListener {
           scheduleNotification()
            saveToDatabase()
        }
    }

    private fun scheduleNotification() {
        val intent = Intent(applicationContext,Notification::class.java)
        val title = "Randevu hatirlatma bildirimi"
        val message = "Randevu saatiniz geldi !!"
        intent.putExtra(titleExtra ,title)
        intent.putExtra(messageExtra ,message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent,
        )

        showAlert(time,title, message)


    }

    private fun showAlert(time: Long, title : String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Randevu oluşturuldu ..")
            .setMessage(
                "\nBaşlık : " + title +
                "\nMesaj : " + message +
                "\nat : " + dateFormat.format(date) + " " + timeFormat.format(date)
            )
            .setPositiveButton("Tamam"){_,_ ->}
            .show()
    }

    private fun getTime(): Long {

        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour - 1
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year,month,day,hour,minute)

        selectedHour = hour.toString()
        selectedMinute = minute.toString()
        selectedDay = day.toString()

        return calendar.timeInMillis

    }

    private fun saveToDatabase() {

        val name = binding.userName.text.toString()
        val fitness = Fitness(name,selectedHour!!,selectedMinute!!,selectedDay!!,"yapildi")

        database.collection("fitnessAppointment").add(fitness)
            .addOnCompleteListener {
                Toast.makeText(applicationContext,"Saved to database",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Failed to save  database !!",Toast.LENGTH_SHORT).show()
            }
    }

    private fun getUserPhoto() {

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification() {
        val name = "Notif Channel"
        val desc = "A description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID,name,importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

}