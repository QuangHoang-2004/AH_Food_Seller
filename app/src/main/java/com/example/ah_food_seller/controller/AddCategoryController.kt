package com.example.ah_food_seller.controller

import android.nfc.Tag
import android.util.Log
import androidx.compose.runtime.Composable
import com.example.ah_food_seller.AuthOrMainScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
val auth = FirebaseAuth.getInstance()
val currentUser = auth.currentUser
@Composable
fun addCategory(
//    nameCategory: String,
//    id_Restaurant: String,
//    onSignedIn: (FirebaseUser) -> Unit
) {
    if (currentUser != null) {
        val userId = currentUser.uid
        Log.d("123", "addCategory: $userId")
    }else{
        
    }
}