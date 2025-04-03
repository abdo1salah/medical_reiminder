package com.example.medicalreiminder.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name: String = "",
    val firstTime: String = "",
    val secondTime: String = "",
    val thirdTime: String = "",
    val dose: String = ""
): Parcelable