package com.example.ah_food_seller.model

data class Restaurant(
    val id: String?,
    val nameRestaurant: String?,
    val addressRestaurant: String?,
    val email: String,
    val sdtRestaurant: String? = null,
    val statusRestaurant: Boolean? = true,
    val imageUrl: String?= null,
){
    constructor():this("", "","","", "",true,"")
}
