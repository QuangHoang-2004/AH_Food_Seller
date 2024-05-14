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
        val firestore = FirebaseFirestore.getInstance()
        val restaurantId = currentUser.uid

        // Trước tiên, truy vấn tất cả các sản phẩm thuộc về categoryId
        firestore.collection("products")
            .whereEqualTo("id_Category", categoryId)
            .whereEqualTo("id_Restaurant", restaurantId)
            .get()
            .addOnSuccessListener { documents ->
                val batch = firestore.batch()

                // Thêm các thao tác xóa sản phẩm vào batch
                for (document in documents) {
                    batch.delete(document.reference)
                }

                // Truy xuất tài liệu danh mục
                val categoryRef = firestore.collection("categories").document(categoryId)
                categoryRef.get()
                    .addOnSuccessListener { categoryDocument ->
                        if (categoryDocument.exists()) {
                            // Thêm thao tác xóa danh mục vào batch
                            batch.delete(categoryDocument.reference)
                        }

                        // Thực hiện các thay đổi trong batch
                        batch.commit()
                            .addOnSuccessListener {
                            }
                            .addOnFailureListener { e ->
                            }
                    }
                    .addOnFailureListener { e ->
                    }
            }
            .addOnFailureListener { e ->
            }
    }
}
