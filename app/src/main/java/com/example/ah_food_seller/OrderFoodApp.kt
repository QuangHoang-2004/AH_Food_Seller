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
import androidx.compose.runtime.getValue
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
import com.google.firebase.auth.FirebaseUser

enum class AppScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Order(title = R.string.order_screen),
    Menu(title = R.string.menu_screen),
    Wallet(title = R.string.wallet_screen),
    MailBox(title = R.string.mailbox_screen),
    Feedback(title = R.string.feedback_screen),
    Profile(title = R.string.profile_screen),
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderFoodApp(
//    viewModel: OrderViewModel = viewModel(),
//    user: FirebaseUser,
    navController: NavHostController = rememberNavController()
){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Start.name
    )

    Scaffold (
        bottomBar = { MyBottomAppBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

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
                OrderScreenMain()
            }
            composable(route = "Menu") { // Chỉ định route cho ScreenB
                MenuScreenMain()
            }
            composable(route = "MailBox") { // Chỉ định route cho ScreenA
                MailBoxScreen()
            }
            composable(route = "Profile") { // Chỉ định route cho ScreenB
                SettingsScreen()
            }
        }
    }
}