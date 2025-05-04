package com.example.medicalreiminder.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalreiminder.model.Reminder
import com.example.medicalreiminder.model.ReminderDao
import com.example.medicalreiminder.model.UserModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthenticationViewModel : ViewModel() {
    val auth = Firebase.auth

    val firestore = Firebase.firestore
    fun signUp(
        email: String,
        password: String,
        name: String,
        context: Context,
        onResult: (Boolean, String?) -> Unit
    ) {
        if (email.isBlank() or password.isBlank() or name.isBlank() ) {
            Toast.makeText(context, "please write email , password and name first", Toast.LENGTH_SHORT)
                .show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { Result ->
                if (Result.isSuccessful) {
                    val userId = Result.result.user?.uid
                    val userModel = UserModel(userId!!, name, email)
                    firestore.collection("users")
                        .document(userId)
                        .set(userModel)
                        .addOnCompleteListener { dbResult ->
                            if (dbResult.isSuccessful) {
                                verifyEmail(context)
                                onResult(true, null)
                            } else {
                                onResult(false, "something went wrong")
                            }

                        }

                } else {
                    onResult(false, Result.exception?.localizedMessage)
                }

            }
    }

    fun login(
        email: String,
        password: String,
        context: Context,
        onResult: (Boolean, String?) -> Unit
    ) {
        if (email.isBlank() or password.isBlank()) {
            Toast.makeText(context, "please write email and password first", Toast.LENGTH_SHORT)
                .show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                   if (auth.currentUser!!.isEmailVerified) {
                        onResult(true, null)
                    } else {
                        Toast.makeText(context, "verify your email first", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    onResult(false, it.exception?.localizedMessage)
                }
            }
    }

    fun sendPasswordResetEmail(email: String, context: Context) {
        if (email.isBlank()) {
            Toast.makeText(context, "Please enter your email first", Toast.LENGTH_SHORT).show()
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun verifyEmail(context: Context) {
        auth.currentUser!!.sendEmailVerification()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "check your email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun logOut() {
        auth.signOut()
    }

    fun addReminderToFireBase(reminder: Reminder, context: Context) {
        viewModelScope.launch {
            firestore.collection("users")
                .document(auth.currentUser?.uid!!)
                .collection("reminders")
                .document(reminder.id.toString())
                .set(reminder)
                .addOnCompleteListener {
                    Toast.makeText(context, "Reminder added", Toast.LENGTH_SHORT).show()
                }
        }

    }

    fun deleteReminderFromFireBase(reminder: Reminder) {
        viewModelScope.launch {
            firestore.collection("users")
                .document(auth.currentUser?.uid!!)
                .collection("reminders")
                .document(reminder.id.toString())
                .delete()
        }
    }

    suspend fun getAllRemindersFromFireBase(): List<Reminder> {
        return try {
            val documents = firestore.collection("users")
                .document(auth.currentUser?.uid!!)
                .collection("reminders")
                .get()
                .await()

            documents.map { it.toObject<Reminder>() }
        } catch (e: Exception) {
            listOf(Reminder())
        }
    }

}