package com.example.medicalreiminder.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.medicalreiminder.model.UserModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
        if (email.isBlank() or password.isBlank()){
            Toast.makeText(context, "please write email and password first", Toast.LENGTH_SHORT).show()
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

}