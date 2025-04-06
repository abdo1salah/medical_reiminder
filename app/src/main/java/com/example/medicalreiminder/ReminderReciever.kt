package com.example.medicalreiminder

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
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
            if (ContextCompat.checkSelfPermission(
                    context,
                    POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {

            }
        }
        else{
            val notification = NotificationCompat.Builder(context)
                .setContentTitle(reminder.name)
                .build()
            NotificationManagerCompat.from(context).
            notify(1,notification)
        }
    }
}