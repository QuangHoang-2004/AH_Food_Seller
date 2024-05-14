package com.example.ah_food_seller.controller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private val auth = FirebaseAuth.getInstance()
private val currentUser = auth.currentUser

data class Product(
    val nameProduct: String = "",
    val contentProduct: String = "",
    val moneyProduct: String = "",
    val imgProduct: String = "",
    val statusProduct: Boolean = false,
    val id_Category: String = "",
    val id_Restaurant: String = "",
    val idProduct: String = ""
)
class ProductViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        if (currentUser != null) {
            val restaurantId = currentUser.uid
            viewModelScope.launch {
                firestore.collection("products")
                    .whereEqualTo("id_Restaurant", restaurantId)
                    .get()
                    .addOnSuccessListener { documents ->
                        val productList = documents.map { document ->
                            val product = document.toObject(Product::class.java)
                            product.copy(idProduct = document.id)
                        }
                        _products.value = productList
                    }
                    .addOnFailureListener { exception ->
                        // Handle the error
                    }
            }
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

}