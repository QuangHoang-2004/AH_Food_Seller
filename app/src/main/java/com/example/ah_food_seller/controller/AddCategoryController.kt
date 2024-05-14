package com.example.ah_food_seller.controller

import android.util.Log
import com.example.ah_food_seller.model.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private val auth = FirebaseAuth.getInstance()
private val currentUser = auth.currentUser
val firestore = FirebaseFirestore.getInstance()

fun addCategory(
    nameCategory: String,
) {
    if (currentUser != null) {
        val restaurantId = currentUser.uid

        // Tạo một đối tượng Category
        val category = Category(
            nameCategory = nameCategory,
            id_Restaurant = restaurantId
        )

        // Lưu đối tượng Category vào Firestore
        firestore.collection("categories")
            .add(category)
            .addOnSuccessListener { documentReference ->
                Log.d("addCategory", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("addCategory", "Error adding document", e)
            }
    } else {
        Log.w("addCategory", "No current user logged in")
    }
}

fun deleteCategory(categoryId: String) {
    if (currentUser != null) {
        // Lấy instance của Firestore
        val firestore = FirebaseFirestore.getInstance()

        // Xóa tài liệu Category từ Firestore
        firestore.collection("categories")
            .document(categoryId)
            .delete()
            .addOnSuccessListener {
                Log.d("deleteCategory", "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w("deleteCategory", "Error deleting document", e)
            }
    } else {
        Log.w("deleteCategory", "No current user logged in")
    }
}
