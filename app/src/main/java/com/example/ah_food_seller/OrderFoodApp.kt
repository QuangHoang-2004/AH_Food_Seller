package com.example.ah_food_seller

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ah_food_seller.model.Restaurant
import com.example.ah_food_seller.ui.theme.AH_Food_SellerTheme
import com.example.ah_food_seller.ui.theme.LightPrimaryColor
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.SecondaryColor
import com.example.ah_food_seller.ui.theme.Shapes
import com.example.ah_food_seller.view.MailBoxScreen
import com.example.ah_food_seller.view.MenuDetailScreen
import com.example.ah_food_seller.view.MenuScreen
import com.example.ah_food_seller.view.MenuScreenMain
import com.example.ah_food_seller.view.MyBottomAppBar
import com.example.ah_food_seller.view.OrderDetailScreen
import com.example.ah_food_seller.view.OrderScreen
import com.example.ah_food_seller.view.OrderScreenMain
import com.example.ah_food_seller.view.SettingsScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private val auth: FirebaseAuth by lazy { Firebase.auth }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderFoodApp(
//    viewModel: OrderViewModel = viewModel(),
    resUser: FirebaseUser,
    navController: NavHostController = rememberNavController()
){
    var users by remember { mutableStateOf(auth.currentUser) }
    val userProfile = remember { mutableStateOf<Restaurant?>(null) }
    val backStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(resUser.uid) {
        val firestore = FirebaseFirestore.getInstance()
        val userDocRef = firestore.collection("restaurants").document(resUser.uid)

        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val nameRes = document.getString("nameRestaurant")
                    val addressRes = document.getString("addressRestaurant")
                    val email = document.getString("email")
                    userProfile.value = Restaurant(nameRes, addressRes, resUser.email ?: "")
                } else {
                }
            }
            .addOnFailureListener { e ->
            }
    }
    Scaffold (
        bottomBar = { MyBottomAppBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                        navController.navigate("")
                          },
                contentColor = Color.White,
                modifier = Modifier
                    .absoluteOffset(y = (65).dp)
                ) {
                Icon(painter = painterResource(id = R.drawable.wallet_icon), contentDescription = "product",
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp))
            }
        },
       floatingActionButtonPosition = FabPosition.Center,
        modifier = Modifier.padding(bottom = 0 .dp),
       // contentWindowInsets = WindowInsets(right = 50 .dp),
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Order",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            composable(route = "Order") { // Chỉ định route cho ScreenA
//                MainContent(
//                    user = user!!
//                )
                OrderScreenMain(
                    user = resUser!!
                )
            }
            composable(route = "Menu") { // Chỉ định route cho ScreenB
                MenuScreenMain()
            }
            composable(route = "MailBox") { // Chỉ định route cho ScreenA
                MailBoxScreen()
            }
            composable(route = "Profile") { // Chỉ định route cho ScreenB
                userProfile.value?.let { users ->
                    SettingsScreen(users)
                }

            }
        }
    }
}