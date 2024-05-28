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
        // Tạo một map để chỉ định các trường cần cập nhật và giá trị mới của chúng
        val updates = hashMapOf<String, Any>(
            "statusProduct" to newStatus
        )

        // Thực hiện cập nhật chỉ mục "statusProduct" của tài liệu
        firestore.collection("products")
            .document(productId) // Sử dụng document với ID của sản phẩm cần cập nhật
            .update(updates) // Sử dụng phương thức update() để chỉ cập nhật trường "statusProduct"
}

fun deleteProduct(productId: String, imgProductUrl: String) {
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

fun updateProduct(
    productId: String,
    nameProduct: String,
    contentProduct: String,
    moneyProduct: String,
    imgProduct: String?,
    statusProduct: Boolean,
    id_Category: String,
) {
    if (currentUser != null) {
        val restaurantId = currentUser.uid

        // Truy xuất giá trị của trường imgProduct từ Firestore trước khi cập nhật
        firestore.collection("products").document(productId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    // Lấy giá trị imgProduct hiện tại từ Firestore
                    val currentImgProduct = document.getString("imgProduct") ?: ""

                    // Sử dụng giá trị imgProduct hiện tại nếu imgProduct mới truyền vào là null hoặc rỗng
                    val finalImgProduct = imgProduct ?: currentImgProduct

                    // Tạo một đối tượng Product cập nhật
                    val product = Product(
                        nameProduct = nameProduct,
                        contentProduct = contentProduct,
                        moneyProduct = moneyProduct,
                        imgProduct = finalImgProduct,
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
                        }
                        .addOnFailureListener { e ->
                            // Xử lý khi cập nhật thất bại
                        }

                    if (imgProduct != currentImgProduct && imgProduct != null) {
                        val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(currentImgProduct)
                        storageRef.delete()
                    }
                } else {
                }
            }
            .addOnFailureListener { e ->
                // Xử lý khi không thể truy xuất dữ liệu từ Firestore
            }
    }
}




