//package com.example.ah_food_seller.model
//
//import androidx.compose.runtime.mutableStateListOf
//import androidx.lifecycle.ViewModel
//import com.example.ah_food_seller.controller.getProductName
//import com.example.ah_food_seller.controller.getUserName
//import com.example.ah_food_seller.controller.listenForOrderUpdates
//import com.example.ah_food_seller.controller.removeOrderListener
//
//class OrderViewModel : ViewModel() {
//    var orders = mutableStateListOf<Order>()
//    var orderUserNameMap = mutableMapOf<String, String>()
//    var orderProductNameMap = mutableMapOf<String, String>()
//
//    init {
//        listenForOrderUpdates()
//    }
//
//    private fun listenForOrderUpdates() {
//        listenForOrderUpdates(
//            onOrderUpdated = { updatedOrders ->
//                orders.clear()
//                orders.addAll(updatedOrders)
//                fetchAdditionalData(updatedOrders)
//            }
//        )
//    }
//
//    private fun fetchAdditionalData(orders: List<Order>) {
//        orders.forEach { order ->
//            fetchUserName(order.userId)
//            order.items.forEach { item ->
//                fetchProductName(item.productId)
//            }
//        }
//    }
//
//    private fun fetchUserName(userId: String) {
//        getUserName(userId,
//            onSuccess = { userName ->
//                orderUserNameMap[userId] = userName
//            },
//            onFailure = { /* Handle error */ }
//        )
//    }
//
//    private fun fetchProductName(productId: String) {
//        getProductName(productId,
//            onSuccess = { productName ->
//                orderProductNameMap[productId] = productName
//            },
//            onFailure = { /* Handle error */ }
//        )
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        removeOrderListener()
//    }
//}
