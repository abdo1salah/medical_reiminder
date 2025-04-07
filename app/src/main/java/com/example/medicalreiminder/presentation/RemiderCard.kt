package com.example.medicalreiminder.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.medicalreiminder.model.Reminder
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun RemiderCard(
    reminder: Reminder,
    onDelete: (Reminder) -> Unit,
    onEditReminder: () -> Unit
) {
    val format = remember {
        SimpleDateFormat("hh:mm a", Locale.getDefault())
    }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)), modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
    {
        Column(modifier = Modifier.padding(5.dp)) {
            Text("Name: ${reminder.name}", fontWeight = FontWeight.Bold)
            Text("Time 1: ${format.format(reminder.time)}")
            Text("Every: ${reminder.timeOffset}")
            Text("Frequency: ${reminder.dose}")

            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { onEditReminder() }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Blue)
                }
                IconButton(onClick = { onDelete(reminder) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                }
            }
        }

    }

}