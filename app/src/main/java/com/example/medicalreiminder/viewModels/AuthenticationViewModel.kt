package com.example.medicalreiminder.viewModels

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
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { Result ->
                if (Result.isSuccessful) {
                    val userId = Result.result.user?.uid
                    val userModel = UserModel(userId!!, name, email)
                    firestore.collection("users").document(userId)
                        .set(userModel)
                        .addOnCompleteListener { dbResult ->
                            if (dbResult.isSuccessful) {
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

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(true,null)
                } else {
                    onResult(false,it.exception?.localizedMessage)
                }
            }
    }

}