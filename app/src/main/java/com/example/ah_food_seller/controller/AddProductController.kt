package com.example.ah_food_seller.controller

import android.util.Log
import com.example.ah_food_seller.model.Product
import com.google.firebase.auth.FirebaseAuth

private val auth = FirebaseAuth.getInstance()
private val currentUser = auth.currentUser
fun addProduct(
    nameProduct: String,
    contentProduct: String,
    moneyProduct: String,
    imgProduct: String,
    statusProduct: Boolean,
    id_Category: String,
) {
    if (currentUser != null) {
        val restaurantId = currentUser.uid

        // Tạo một đối tượng Product
        val product = Product(
            nameProduct = nameProduct,
            contentProduct = contentProduct,
            moneyProduct = moneyProduct,
            imgProduct = imgProduct,
            statusProduct = statusProduct,
            id_Category = id_Category,
            id_Restaurant = restaurantId
        )

        // Lưu đối tượng Product vào Firestore
        firestore.collection("products")
            .add(product)
            .addOnSuccessListener { documentReference ->
                Log.d("addProduct", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("addProduct", "Error adding document", e)
            }
    } else {
        Log.w("addProduct", "No current user logged in")
    }
}