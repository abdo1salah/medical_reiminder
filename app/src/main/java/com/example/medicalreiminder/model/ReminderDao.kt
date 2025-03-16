package com.example.medicalreiminder.model


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Upsert
    suspend fun upsertReminder(reminder : Reminder)
    @Delete
    suspend fun deleteReminder(reminder: Reminder)
    @Query(" select * from reminders")
    fun getReminders() : Flow<List<Reminder>>
}