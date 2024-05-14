package com.example.ah_food_seller.model

data class Restaurant(
    val nameRestaurant: String?,
    val addressRestaurant: String?,
    val email: String,
    val statusRestaurant: Boolean?=true,
    val imageUrl: String?=null,
){
    constructor():this("","","",true,"")
}
