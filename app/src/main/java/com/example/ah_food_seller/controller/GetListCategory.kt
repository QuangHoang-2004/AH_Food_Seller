package com.example.ah_food_seller.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private val auth = FirebaseAuth.getInstance()
private val currentUser = auth.currentUser

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
        if (currentUser != null) {
            val restaurantId = currentUser.uid
            viewModelScope.launch {
                firestore.collection("categories")
                    .whereEqualTo("id_Restaurant", restaurantId)
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
}
