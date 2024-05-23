package com.example.ah_food_seller.controller

import com.example.ah_food_seller.model.Restaurant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    nameRestaurant: String,
    addressRes: String,
    onSignedIn: (FirebaseUser) -> Unit
) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser

                val userProfile = hashMapOf(
                    "nameRestaurant" to nameRestaurant,
                    "addressRestaurant" to addressRes,
                    "email" to email,
                    "sdtRestaurant" to "",
                    "statusRestaurant" to false,
                    "imageUrl" to ""
                )

                val firestore = FirebaseFirestore.getInstance()
                firestore.collection("restaurants")
                    .document(user!!.uid)
                    .set(userProfile)
                    .addOnSuccessListener {
                        onSignedIn(user)
                    }
                    .addOnFailureListener {
                        //handle exception

                    }
            } else {
                // Handle sign-up failure

            }
        }
}


// Khai báo một MutableStateFlow để lưu trữ dữ liệu của nhà hàng
val restaurantData: MutableStateFlow<Restaurant?> = MutableStateFlow(null)

// Hàm lấy dữ liệu của nhà hàng từ Firestore và theo dõi sự thay đổi
suspend fun getRestaurantById(id: String): Flow<Restaurant?> {
    val firestore = FirebaseFirestore.getInstance()
    val restaurantRef = firestore.collection("restaurants").document(id)

    // Lấy dữ liệu ban đầu
    val initialData = restaurantRef.get().await().toObject(Restaurant::class.java)

    // Theo dõi sự thay đổi trong cơ sở dữ liệu và cập nhật dữ liệu của nhà hàng
    restaurantRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            // Xử lý lỗi
            println("Listen failed: $e")
            restaurantData.value = null
            return@addSnapshotListener
        }

        if (snapshot != null && snapshot.exists()) {
            // Dữ liệu đã thay đổi, cập nhật dữ liệu của nhà hàng
            val updatedRestaurant = snapshot.toObject(Restaurant::class.java)
            restaurantData.value = updatedRestaurant
        } else {
            // Dữ liệu không tồn tại hoặc bị xóa, cập nhật dữ liệu của nhà hàng thành null
            restaurantData.value = null
        }
    }

    // Trả về MutableStateFlow để theo dõi dữ liệu của nhà hàng
    return restaurantData
}

fun updateRestaurantStatus(restaurantId: String, newStatus: Boolean) {
        val updates = hashMapOf<String, Any>(
            "statusRestaurant" to newStatus
        )
        firestore.collection("restaurants")
            .document(restaurantId)
            .update(updates)
}