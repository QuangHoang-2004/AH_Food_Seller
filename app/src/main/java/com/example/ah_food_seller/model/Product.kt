package com.example.ah_food_seller.model

data class Product(
    val nameProduct: String = "",
    val contentProduct: String = "",
    val moneyProduct: String = "",
    val imgProduct: String = "",
    val statusProduct: Boolean = false,
    val id_Category: String = "",
    val id_Restaurant: String = "",
)
