package com.example.medicalreiminder.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicalreiminder.R
import com.example.medicalreiminder.cancelAlarm
import com.example.medicalreiminder.viewModels.AuthenticationViewModel
import com.example.medicalreiminder.viewModels.ReminderViewModel
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun sendEmergencyMessage(context: Context) {
    val message = "Emergency! I need help. Here's my location: https://maps.google.com/?q=37.4219983,-122.084" // sample location

    val smsIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("sms:")
        putExtra("sms_body", message)
    }

    try {
        context.startActivity(smsIntent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No messaging app found", Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun MainScreen(
    modifier: Modifier,
    ReminderViewModel: ReminderViewModel,
    authenticationViewModel: AuthenticationViewModel,
    onAddMed: (String, Long, Long, String) -> Unit,
    onEditMed: (Int, String, Long, Long, String) -> Unit,
) {

    val medications = ReminderViewModel.reminders.collectAsState(emptyList()).value.toMutableList()
    val context = LocalContext.current
    Box(modifier = modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.backg),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()

        )

        if (medications.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Add your first Medication to get a Reminder",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF28125B),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { onAddMed("", 0L, 0L, "") },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF77AADA)),
                    modifier = Modifier
                        .padding(16.dp)
                        .height(50.dp)
                        .width(200.dp)
                ) {
                    Text("Add your Med", fontSize = 16.sp, color = Color.White)
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                LazyColumn {
                    items(medications) { med ->
                        RemiderCard(
                            reminder = med,
                            onDelete = { ReminderViewModel.deleteReminder(med)
                                authenticationViewModel.deleteReminderFromFireBase(med)
                                cancelAlarm(context,med)
                            }) {
                            onEditMed(
                                med.id,
                                med.name,
                                med.time,
                                med.timeOffset,
                                med.dose
                            )
                        }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { sendEmergencyMessage(context) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 90.dp), // 90 to leave space above the "+"
            shape = CircleShape,
            containerColor = Color.Red
        ) {
            Text("SOS", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }


        FloatingActionButton(
            onClick = { onAddMed("", 0L, 0L, "") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = Color(0xFF77AADA)
        ) {
            Text("+", fontSize = 24.sp, color = Color.White)
        }
    }


}

