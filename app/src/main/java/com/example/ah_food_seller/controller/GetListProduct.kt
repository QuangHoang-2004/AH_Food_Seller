package com.example.ah_food_seller.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
    private val _categories = MutableStateFlow<List<Product>>(emptyList())
    val categories: StateFlow<List<Product>> = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            firestore.collection("categories")
                .get()
                .addOnSuccessListener { documents ->
                    val categoryList = documents.map { document ->
                        val category = document.toObject(Product::class.java)
                        category.copy(idProduct = document.id)
                    }
                    _categories.value = categoryList
                }
                .addOnFailureListener { exception ->
                    // Handle the error
                }
        }
    }
}
