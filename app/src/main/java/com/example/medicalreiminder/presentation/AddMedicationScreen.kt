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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.NavController
import com.example.medicalreiminder.model.Reminder
import com.example.medicalreiminder.viewModels.ReminderViewModel

@Composable
fun AddMedicationScreen(
    modifier: Modifier,
    medName: String,
    medFirstTime: String,
    medSecondTime: String,
    medThirdTime: String,
    medDose: String,
    viewModel: ReminderViewModel,
    back: () -> Unit
) {
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
        OutlinedButton(
            onClick = { showTimePicker(time1) { time1 = it } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Time 1: $time1")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Time 2 Picker
        OutlinedButton(
            onClick = { showTimePicker(time2) { time2 = it } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Time 2: $time2")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Time 3 Picker
        OutlinedButton(
            onClick = { showTimePicker(time3) { time3 = it } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Time 3: $time3")
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
                    viewModel.addReminder(
                        Reminder(
                            name = medName,
                            firstTime = medFirstTime,
                            secondTime = medSecondTime,
                            thirdTime = medThirdTime,
                            dose = medDose
                        )
                    )
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
