package com.example.ah_food_seller.viewmodel

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

val db = FirebaseFirestore.getInstance()
var listenerRegistration: ListenerRegistration? = null

// Thiết lập hàm lắng nghe thay đổi dữ liệu
fun listenForOrderUpdates(
    onOrderUpdated: (List<Order>) -> Unit
) {
    listenerRegistration = db.collection("orders")
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshots != null) {
                val orders = mutableListOf<Order>()
                for (doc in snapshots.documents) {
                    val order = doc.toObject(Order::class.java)
                    if (order != null) {
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
