package com.example.medicalreiminder.viewModels

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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

    fun addReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            db.upsertReminder(reminder)
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch(Dispatchers.IO) {
            db.deleteReminder(reminder)
        }
    }

    fun scanBarcode() {
        viewModelScope.launch {
            scanner.startScan()
            val endPoint = getEndpoint(scanner.barCodeResults.value!!)
            try {
                prodName = BarcodeApi.retrofitService.getData(endPoint).products[0].brand
            } catch (e: Exception) {

            }
        }

    }
    fun loadIntoDb(reminders:List<Reminder>){
        viewModelScope.launch {
            for (reminder in reminders){
                db.upsertReminder(reminder)
            }
        }

    }

}