package com.example.ah_food_seller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ah_food_seller.ui.theme.AH_Food_SellerTheme
import com.example.ah_food_seller.view.AuthScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class MainActivity : ComponentActivity() {
//    private val auth: FirebaseAuth by lazy { Firebase.auth }
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AH_Food_SellerTheme {
                AuthOrScreenMain()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun  AuthOrScreenMain(){
    val mainAuthOrNavController = rememberNavController()

    NavHost(navController = mainAuthOrNavController, startDestination = "Logout"){
        composable("Logout"){
            AuthOrMainScreen(mainAuthOrNavController = mainAuthOrNavController)
        }
    }
}

@Composable
fun AuthOrMainScreen(mainAuthOrNavController: NavHostController) {
    val auth: FirebaseAuth by lazy { Firebase.auth }
    var user by remember { mutableStateOf(auth.currentUser) }

    if (user == null) {
        AuthScreen(
            onSignedIn = { signedInUser ->
                user = signedInUser
            }
        )
    } else {
        OrderFoodApp(user!!, mainAuthOrNavController = mainAuthOrNavController)
    }
}
