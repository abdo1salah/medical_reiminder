package com.example.medicalreiminder.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val firstTime: Long = 0L,
    val secondTime: Long = 0L,
    val thirdTime: Long = 0L,
    val dose: String = ""
) : Parcelable