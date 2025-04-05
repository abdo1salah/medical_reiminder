package com.example.medicinereminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.medicinereminder.ui.theme.MedicineReminderTheme
import androidx.compose.material.icons.rounded.QrCodeScanner
import android.app.TimePickerDialog
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.TextFieldDefaults







data class Medication(
    var name: String,
    var time1: String,
    var time2: String,
    var time3: String,
    var frequency: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicineReminderTheme {
                val medications = remember { mutableStateListOf<Medication>() }
                AppNavigation(medications)
            }
        }
    }
}

@Composable
fun AppNavigation(medications: MutableList<Medication>) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                navController = navController,
                medications = medications,
                onDelete = { med -> medications.remove(med) }
            )
        }
        composable("add_medication") {
            AddMedicationScreen(
                navController = navController,
                medications = medications
            )
        }
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    medications: MutableList<Medication>,
    onDelete: (Medication) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
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
                    onClick = { navController.navigate("add_medication") },
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
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
            ) {
                LazyColumn {
                    itemsIndexed(medications) { index, med ->
                        var isEditing by remember { mutableStateOf(false) }

                        var editedName by remember { mutableStateOf(med.name) }
                        var editedTime1 by remember { mutableStateOf(med.time1) }
                        var editedTime2 by remember { mutableStateOf(med.time2) }
                        var editedTime3 by remember { mutableStateOf(med.time3) }
                        var editedFreq by remember { mutableStateOf(med.frequency) }

                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFBBDEFB)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                if (isEditing) {
                                    OutlinedTextField(
                                        value = editedName,
                                        onValueChange = { editedName = it },
                                        label = { Text("Medication Name") },
                                        colors = TextFieldDefaults.colors(
                                            focusedTextColor = Color.Black,
                                            unfocusedTextColor = Color.DarkGray,
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent
                                        )

                                    )

                                    OutlinedTextField(
                                        value = editedTime1,
                                        onValueChange = { editedTime1 = it },
                                        label = { Text("Time 1") },
                                        colors = TextFieldDefaults.colors(
                                            focusedTextColor = Color.Black,
                                            unfocusedTextColor = Color.DarkGray,
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent
                                        )

                                    )

                                    OutlinedTextField(
                                        value = editedTime2,
                                        onValueChange = { editedTime2 = it },
                                        label = { Text("Time 2") },
                                        colors = TextFieldDefaults.colors(
                                            focusedTextColor = Color.Black,
                                            unfocusedTextColor = Color.DarkGray,
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent
                                        )

                                    )

                                    OutlinedTextField(
                                        value = editedTime3,
                                        onValueChange = { editedTime3 = it },
                                        label = { Text("Time 3") },
                                        colors = TextFieldDefaults.colors(
                                            focusedTextColor = Color.Black,
                                            unfocusedTextColor = Color.DarkGray,
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent
                                        )

                                    )

                                    OutlinedTextField(
                                        value = editedFreq,
                                        onValueChange = { editedFreq = it },
                                        label = { Text("Frequency") },
                                        colors = TextFieldDefaults.colors(
                                            focusedTextColor = Color.Black,
                                            unfocusedTextColor = Color.DarkGray,
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent
                                        )

                                    )

                                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                                        IconButton(onClick = {
                                            if (editedName.isNotBlank()) {
                                                medications[index] = Medication(editedName, editedTime1, editedTime2, editedTime3, editedFreq)
                                                isEditing = false
                                            }
                                        }) {
                                            Icon(Icons.Default.Check, contentDescription = "Save", tint = Color.Green)
                                        }
                                    }
                                } else {
                                    Text("Name: ${med.name}", fontWeight = FontWeight.Bold)
                                    Text("Time 1: ${med.time1}")
                                    Text("Time 2: ${med.time2}")
                                    Text("Time 3: ${med.time3}")
                                    Text("Frequency: ${med.frequency}")

                                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                                        IconButton(onClick = { isEditing = true }) {
                                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Blue)
                                        }
                                        IconButton(onClick = { onDelete(med) }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            FloatingActionButton(
                onClick = { navController.navigate("add_medication") },
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
}

@Composable
fun AddMedicationScreen(
    navController: NavController,
    medications: MutableList<Medication>
) {
    var medName by remember { mutableStateOf("") }
    var time1 by remember { mutableStateOf("08:00") }
    var time2 by remember { mutableStateOf("17:00") }
    var time3 by remember { mutableStateOf("21:00") }
    var frequency by remember { mutableStateOf("Daily") }

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
        modifier = Modifier
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
            TextButton(onClick = { navController.popBackStack() }) {
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
                    medications.add(
                        0,
                        Medication(medName, time1, time2, time3, frequency)
                    )
                    navController.popBackStack()
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
