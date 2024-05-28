package com.example.ah_food_seller.model

data class OrderItem(
    val productId: String = "",
    val quantity: Int = 0,
    val price: Double = 0.0
)

data class Order(
    val orderId: String = "",
    val userId: String = "",
    val items: List<OrderItem> = emptyList(),
    val totalOrderAmount: Double = 0.0,
    val shippingFee: Double = 0.0,
    val discount: Double = 0.0,
    val totalPaymentAmount: Double = 0.0,
    val statusOrder: String = "",
    val address: String = "",
    val phone: String = "",
    val paymentMethod: String = "",
    var totalQuantity: Int = 0
)
