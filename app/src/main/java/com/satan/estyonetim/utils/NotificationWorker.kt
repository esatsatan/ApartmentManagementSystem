package com.satan.estyonetim.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.satan.estyonetim.R
import com.satan.estyonetim.homeviews.CreateFitnessAppointmentActivity

class NotificationWorker(context : Context ,workerParameters : WorkerParameters) :
        Worker(context,workerParameters)
{

    override fun doWork(): Result {
        Log.d("işlem başarılı","doWork : Fonksyon başarıyla çağırıldı")

        showNotification()

        return Result.success()

    }

    private fun showNotification() {
        val intent = Intent(applicationContext,
            CreateFitnessAppointmentActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,0,intent,0
        )

        val builder = NotificationCompat.Builder(applicationContext,"my_unique_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Fitness Appointment  Reminder")
            .setContentText("Randevu satiniz yaklaşıyor !!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(1,builder.build())
        }


    }


}