package com.example.medicalreiminder.model.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

fun hasPermission(context: Context): Boolean = ActivityCompat.checkSelfPermission(
    context,
    Manifest.permission.ACCESS_FINE_LOCATION
) == PackageManager.PERMISSION_GRANTED ||
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

fun checkGpsState(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, callback: (Double, Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                callback(lat, long)
            }
        }
        .addOnFailureListener { exception ->
            // Handle location retrieval failure
            exception.printStackTrace()
        }
    return
}

fun sendEmergencyMessage(context: Context, lat: Double, long: Double) {
    val message =
        "Emergency! I need help. Here's my location: https://maps.google.com/?q=$lat,$long" // sample location
    Toast.makeText(context, "sending", Toast.LENGTH_SHORT).show()
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