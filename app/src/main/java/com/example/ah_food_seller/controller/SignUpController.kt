package com.example.ah_food_seller.controller

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    nameShop: String,
    addressShop: String,
    onSignedIn: (FirebaseUser) -> Unit
) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser

                // Create a user profile in Firestore
                val userProfile = hashMapOf(
                    "nameShop" to nameShop,
                    "addressShop" to addressShop,
                    "email" to email
                )

                val firestore = FirebaseFirestore.getInstance()
                firestore.collection("shops")
                    .document(user!!.uid)
                    .set(userProfile)
                    .addOnSuccessListener {
                        onSignedIn(user)
                    }
                    .addOnFailureListener {
                        //handle exception

                    }
            } else {
                // Handle sign-up failure

            }
        }
}