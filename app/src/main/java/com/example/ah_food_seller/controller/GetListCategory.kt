package com.example.ah_food_seller.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Category(
    val nameCategory: String = "",
    val id_Restaurant: String = "",
    val idCategory: String = ""
)

class CategoryViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            firestore.collection("categories")
                .get()
                .addOnSuccessListener { documents ->
                    val categoryList = documents.map { document ->
                        val category = document.toObject(Category::class.java)
                        category.copy(idCategory = document.id)
                    }
                    _categories.value = categoryList
                }
                .addOnFailureListener { exception ->
                    // Handle the error
                }
        }
    }
}
