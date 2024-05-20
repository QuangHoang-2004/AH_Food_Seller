package com.example.ah_food_seller.controller

import android.util.Log
import com.example.ah_food_seller.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

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
    }
}

fun deleteProduct(productId: String, imgProductUrl: String) {
    if (currentUser != null) {
        // Lấy instance của Firestore
        val firestore = FirebaseFirestore.getInstance()

        // Xóa tài liệu Product từ Firestore
        firestore.collection("products")
            .document(productId)
            .delete()
            .addOnSuccessListener {
                Log.d("deleteProduct", "DocumentSnapshot successfully deleted!")
                // Sau khi xóa sản phẩm thành công, bạn cần xóa hình ảnh từ Firebase Storage
                val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imgProductUrl)
                storageRef.delete()
            }

    }
}

fun updateProduct(
    productId: String,
    nameProduct: String,
    contentProduct: String,
    moneyProduct: String,
    imgProduct: String,
    statusProduct: Boolean,
    id_Category: String,
) {
    if (currentUser != null) {
        val restaurantId = currentUser.uid

        // Tạo một đối tượng Product cập nhật
        val product = Product(
            id = productId,
            nameProduct = nameProduct,
            contentProduct = contentProduct,
            moneyProduct = moneyProduct,
            imgProduct = imgProduct,
            statusProduct = statusProduct,
            id_Category = id_Category,
            id_Restaurant = restaurantId
        )

        // Truy xuất đối tượng sản phẩm cần cập nhật trong Firestore
        val productRef = firestore.collection("products").document(productId)

        // Thực hiện cập nhật dữ liệu cho sản phẩm
        productRef.set(product)
            .addOnSuccessListener {
                // Xử lý khi cập nhật thành công
                Log.d("UpdateProduct", "Product updated successfully")
            }
            .addOnFailureListener { e ->
                // Xử lý khi cập nhật thất bại
                Log.e("UpdateProduct", "Error updating product", e)
            }
    }
}


