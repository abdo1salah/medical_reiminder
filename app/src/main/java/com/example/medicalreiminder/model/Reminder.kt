package com.example.medicalreiminder.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String = "",
    val time: Long = 0L,
    val timeOffset: Long = 0L,
    val dose: String = ""
) : Parcelable