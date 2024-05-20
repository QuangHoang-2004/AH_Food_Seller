import android.util.Log
import com.example.ah_food_seller.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private val auth = FirebaseAuth.getInstance()
private val currentUser = auth.currentUser
private val firestore = FirebaseFirestore.getInstance()

fun addOrder(
    idUser: String,
    sdt: String,
    statusOrder: String,
) {
    if (currentUser != null) {
        val restaurantId = currentUser.uid

        // Tạo một đối tượng Order
        val order = Order(
            idUser = idUser,
            sdt = sdt,
            statusOrder = statusOrder,
            id_Restaurant = restaurantId
        )

        firestore.collection("orders")
            .add(order)
            .addOnSuccessListener { documentReference ->
                Log.d("addOrder", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("addOrder", "Error adding document", e)
            }
    } else {
        Log.w("addOrder", "No current user logged in")
    }
}
