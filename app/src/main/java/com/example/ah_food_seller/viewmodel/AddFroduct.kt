package com.example.ah_food_seller.viewmodel

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

@Composable
fun OrderScreen() {
    var orders by remember { mutableStateOf<List<Order>>(emptyList()) }

    LaunchedEffect(Unit) {
        listenForOrderUpdates(
            onOrderUpdated = { updatedOrders ->
                orders = updatedOrders
            }
        )
    }

    if (orders != null) {
        OrderList(orders = orders)
    }
}

@Composable
fun OrderList(orders: List<Order>) {
    LazyColumn {
        items(orders) { order ->
            OrderItem(order = order)
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    // Customize the UI for each order item
    Text(text = "Order ID: ${order.userId}, Total: ${order.totalPaymentAmount}")
}














