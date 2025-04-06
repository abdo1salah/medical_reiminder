package com.example.medicalreiminder.presentation

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.medicalreiminder.model.Reminder
import com.example.medicalreiminder.viewModels.AuthenticationViewModel
import com.example.medicalreiminder.viewModels.ReminderViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMedicationScreen(
    modifier: Modifier,
    id: Int,
    medName: String,
    medFirstTime: Long,
    medSecondTime: Long,
    medThirdTime: Long,
    medDose: String,
    reminderViewModel: ReminderViewModel,
    authenticationViewModel: AuthenticationViewModel,
    back: () -> Unit
) {
    val timePickerState = rememberTimePickerState()
    val isTimePicker1Visible = remember {
        mutableStateOf(false)
    }
    val isTimePicker2Visible = remember {
        mutableStateOf(false)
    }
    val isTimePicker3Visible = remember {
        mutableStateOf(false)
    }
    val format = remember {
        SimpleDateFormat("hh:mm a", Locale.getDefault())
    }
    var medName by remember { mutableStateOf(medName) }
    var time1 by remember { mutableStateOf(medFirstTime) }
    var time2 by remember { mutableStateOf(medSecondTime) }
    var time3 by remember { mutableStateOf(medThirdTime) }
    var frequency by remember { mutableStateOf(medDose) }

    val context = LocalContext.current

    // Time Picker dialogs
    fun showTimePicker(currentTime: String, onTimeSelected: (String) -> Unit) {
        val hour = currentTime.substringBefore(":").toInt()
        val minute = currentTime.substringAfter(":").toInt()
        TimePickerDialog(context, { _, h, m ->
            onTimeSelected(String.format("%02d:%02d", h, m))
        }, hour, minute, true).show()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { back() }) {
                Text("Cancel", color = Color.Blue)
            }
            IconButton(onClick = {
                // TODO: Add barcode scan logic
            }) {
                Icon(
                    imageVector = Icons.Rounded.QrCodeScanner,
                    contentDescription = "Scan Barcode",
                    tint = Color.Blue
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = medName,
            onValueChange = { medName = it },
            label = { Text("Medication name") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.DarkGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )

        )

        Spacer(modifier = Modifier.height(10.dp))

        // Time 1 Picker
        if (isTimePicker1Visible.value){
            Dialog(onDismissRequest = {}) {
                TimePicker(state = timePickerState)
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {isTimePicker1Visible.value = isTimePicker1Visible.value.not()}) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        val calendar = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY,timePickerState.hour)
                            set(Calendar.MINUTE,timePickerState.minute)
                        }
                        time1 = calendar.timeInMillis
                    }) {
                        Text("Confirm")
                    }
                }
            }
        }
        if (isTimePicker2Visible.value){
            Dialog(onDismissRequest = {}) {
                TimePicker(state = timePickerState)
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {isTimePicker2Visible.value = isTimePicker2Visible.value.not()}) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        val calendar = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY,timePickerState.hour)
                            set(Calendar.MINUTE,timePickerState.minute)
                        }
                        time2 = calendar.timeInMillis
                    }) {
                        Text("Confirm")
                    }
                }
            }
        }
        if (isTimePicker3Visible.value){
            Dialog(onDismissRequest = {}) {
                TimePicker(state = timePickerState)
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {isTimePicker3Visible.value = isTimePicker3Visible.value.not()}) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        val calendar = Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY,timePickerState.hour)
                            set(Calendar.MINUTE,timePickerState.minute)
                        }
                        time3 = calendar.timeInMillis
                    }) {
                        Text("Confirm")
                    }
                }
            }
        }
        // Time 1 Picker
        OutlinedButton(
            onClick = {
                isTimePicker1Visible.value = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Time 1: ${format.format(time1)}")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Time 2 Picker
        OutlinedButton(
            onClick = { isTimePicker2Visible.value = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Time 2: ${format.format(time2)}")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Time 3 Picker
        OutlinedButton(
            onClick = {isTimePicker3Visible.value = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Time 3: ${format.format(time3)}")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = frequency,
            onValueChange = { frequency = it },
            label = { Text("Frequency") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.DarkGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )

        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (medName.isNotBlank()) {
                    val reminder =  Reminder(
                        id = id,
                        name = medName,
                        firstTime = time1,
                        secondTime = time2,
                        thirdTime = time3,
                        dose = frequency
                    )
                    reminderViewModel.addReminder(
                        reminder
                    )
                    authenticationViewModel.addReminderToFireBase(reminder,context)
                    back()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF77AADA))
        ) {
            Text("Save", fontSize = 16.sp, color = Color.White)
        }
    }
}
