package com.example.ah_food_seller.controller

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    nameRestaurant: String,
    addressRes: String,
    onSignedIn: (FirebaseUser) -> Unit
) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser

                val userProfile = hashMapOf(
                    "nameRestaurant" to nameRestaurant,
                    "addressRestaurant" to addressRes,
                    "email" to email
                )

                val firestore = FirebaseFirestore.getInstance()
                firestore.collection("restaurants")
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