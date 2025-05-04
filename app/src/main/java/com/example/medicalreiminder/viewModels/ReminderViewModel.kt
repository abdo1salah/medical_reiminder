package com.example.medicalreiminder.viewModels

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalreiminder.model.Api.BarcodeApi
import com.example.medicalreiminder.model.DbHelper
import com.example.medicalreiminder.model.Reminder
import com.example.medicalreiminder.model.ReminderDao
import com.example.medicalreiminder.model.utils.getEndpoint
import com.example.medicalreiminder.presentation.BarcodeScanner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderViewModel(app: Application) : AndroidViewModel(app) {

    val db = DbHelper.getInstance(app).dao
    var reminders = db.getReminders()
    var prodName by mutableStateOf("")
    val scanner = BarcodeScanner(app)
    var lastInsertedId = mutableStateOf(0L)

    fun addReminder(reminder: Reminder, onInserted: (Reminder) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = db.upsertReminder(reminder)
            onInserted(reminder.copy(id = id.toInt()))
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            db.deleteReminder(reminder)
        }
    }

    fun scanBarcode(context: Context) {
        scanner.startScan(
            onSuccess = { barcode ->
                viewModelScope.launch {
                    val endPoint = getEndpoint(barcode)
                    try {
                        prodName = BarcodeApi.retrofitService.getData(endPoint).products[0].manufacturer
                    } catch (e: Exception) {
                        Log.d("trace", "API call failed: ${e.localizedMessage}")
                    }
                }
                Toast.makeText(context, prodName, Toast.LENGTH_SHORT).show()
            }
        )
    }


    fun loadIntoDb(reminders: List<Reminder>) {
        viewModelScope.launch {
            for (reminder in reminders) {
                db.upsertReminder(reminder)
            }
        }

    }

}