package com.example.medicalreiminder

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.medicalreiminder.model.Reminder
import com.google.gson.Gson

class ReminderReciever : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val reminderJson = intent.getStringExtra("Reminder")
        val reminder = Gson().fromJson(reminderJson, Reminder::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Toast.makeText(context, "recieved", Toast.LENGTH_SHORT).show()
            if (ContextCompat.checkSelfPermission(
                    context,
                    POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val notification = NotificationCompat.Builder(context,"medicine_reminder")
                    .setContentTitle(reminder.name)
                    .setSmallIcon(R.drawable.android)
                    .setContentText("Time to take your medicine")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()
                NotificationManagerCompat.from(context).
                notify(1,notification)
                Toast.makeText(context, "high version", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            val notification = NotificationCompat.Builder(context,"rem")
                .setContentTitle(reminder.name)
                .setSmallIcon(R.drawable.android)
                .build()
            NotificationManagerCompat.from(context).
            notify(1,notification)
            Toast.makeText(context, "low versions", Toast.LENGTH_SHORT).show()
        }
    }
}