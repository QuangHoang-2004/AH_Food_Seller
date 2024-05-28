package com.example.ah_food_seller.controller

import com.google.firebase.firestore.FirebaseFirestore
import com.example.ah_food_seller.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration

private val auth = FirebaseAuth.getInstance()
private val currentUser = auth.currentUser

val db = FirebaseFirestore.getInstance()
var listenerRegistration: ListenerRegistration? = null

fun listenForOrderUpdates(
    onOrderUpdated: (List<Order>) -> Unit,
) {
    val restaurantId = currentUser?.uid
    listenerRegistration = db.collection("orders")
        .whereEqualTo("restaurantId", restaurantId)
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshots != null) {
                val orders = mutableListOf<Order>()
                for (doc in snapshots.documents) {
                    val order = doc.toObject(Order::class.java)?.copy(orderId = doc.id)
                    if (order != null) {
                        order.totalQuantity = order.items.sumOf { it.quantity }
                        orders.add(order)
                    }
                }
                onOrderUpdated(orders)
            }
        }
}

// Dừng lắng nghe khi không cần thiết
fun removeOrderListener() {
    listenerRegistration?.remove()
}

fun getUserName(userId: String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("users").document(userId)

    userRef.get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val firstName = document.getString("firstName") ?: ""
                val lastName = document.getString("lastName") ?: ""
                val userName = "$lastName $firstName".trim()
                onSuccess(userName)
            } else {
                onFailure(Exception("User not found"))
            }
        }
}

fun getProductName(productId: String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("products").document(productId)

    userRef.get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val productName = document.getString("nameProduct") ?: ""
                onSuccess(productName)
            } else {
                onFailure(Exception("Product not found"))
            }
        }
}

fun updateOrderStatus(orderId: String, newStatus: String, onSuccess: () -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val orderRef = db.collection("orders").document(orderId)

    orderRef.update("statusOrder", newStatus)
        .addOnSuccessListener {
            onSuccess()
        }
}
