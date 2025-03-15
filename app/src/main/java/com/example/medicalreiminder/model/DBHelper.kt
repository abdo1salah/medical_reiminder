package com.example.medicalreiminder.model
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class DbHelper : RoomDatabase() {
    abstract val dao: ReminderDao
    companion object{
        @Volatile
        private var INSTANCE : DbHelper? = null
        fun getInstance(context: Context) : DbHelper {
            return INSTANCE ?: synchronized(this){
                val instance = Room
                    .databaseBuilder(context, DbHelper::class.java,"MyDB")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}