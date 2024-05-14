package com.example.ah_food_seller.controller

import android.util.Log
import com.example.ah_food_seller.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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

fun updateProductStatus(productId: String, newStatus: Boolean) {
    if (currentUser != null) {
        // Lưu trữ trường ID của người dùng hiện tại
        val restaurantId = currentUser.uid

        // Tạo một map để chỉ định các trường cần cập nhật và giá trị mới của chúng
        val updates = hashMapOf<String, Any>(
            "statusProduct" to newStatus
        )

        // Thực hiện cập nhật chỉ mục "statusProduct" của tài liệu
        firestore.collection("products")
            .document(productId) // Sử dụng document với ID của sản phẩm cần cập nhật
            .update(updates) // Sử dụng phương thức update() để chỉ cập nhật trường "statusProduct"
            .addOnSuccessListener {
                Log.d("updateProductStatus", "DocumentSnapshot successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w("updateProductStatus", "Error updating document", e)
            }
    } else {
        Log.w("updateProductStatus", "No current user logged in")
    }
}

fun deleteProduct(productId: String) {
    if (currentUser != null) {
        // Lấy instance của Firestore
        val firestore = FirebaseFirestore.getInstance()

        // Xóa tài liệu Product từ Firestore
        firestore.collection("products")
            .document(productId)
            .delete()
            .addOnSuccessListener {
                Log.d("deleteProduct", "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w("deleteProduct", "Error deleting document", e)
            }
    } else {
        Log.w("deleteProduct", "No current user logged in")
    }
}
