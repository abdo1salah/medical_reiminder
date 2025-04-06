package com.example.medicalreiminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.medicalreiminder.model.Reminder
import com.google.gson.Gson

fun setUpAlarm(context: Context, reminder: Reminder) {
    val repeatEvery = 24L*60L*60L*1000L
    val intent = Intent(context, ReminderReciever::class.java).apply {
        putExtra("Reminder", Gson().toJson(reminder))
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminder.id,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,reminder.firstTime,repeatEvery,pendingIntent)
    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,reminder.secondTime,repeatEvery,pendingIntent)
    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,reminder.thirdTime,repeatEvery,pendingIntent)
}

fun cancelAlarm(context: Context, reminder: Reminder) {
    val repeatEvery = 24L*60L*60L*1000L
    val intent = Intent(context, ReminderReciever::class.java).apply {
        putExtra("Reminder", Gson().toJson(reminder))
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminder.id,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.cancel(pendingIntent)
}