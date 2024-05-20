package com.example.ah_food_seller.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import com.example.ah_food_seller.model.User

private val auth = FirebaseAuth.getInstance()
private val currentUser = auth.currentUser

data class Order(
    val idUser: String = "",
    val sdt: String = "",
    val statusOder: String = "",
    val idOrder: String = "",
)
class OrderViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser

    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        currentUser?.let { user ->
            firestore.collection("orders")
                .whereEqualTo("id_Restaurant", user.uid)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        // Handle error
                        return@addSnapshotListener
                    }

                    val orderList = snapshot?.documents?.map { document ->
                        document.toObject(Order::class.java)!!
                    } ?: emptyList()

                    _orders.value = orderList
                }
        }
    }

    suspend fun getCustomerName(idUser: String): String? {
        return try {
            val documentSnapshot = firestore.collection("users")
                .document(idUser)
                .get()
                .await()

            if (documentSnapshot.exists()) {
                val user = documentSnapshot.toObject(User::class.java)
                listOfNotNull(user?.firstName, user?.lastName).joinToString(" ")
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}