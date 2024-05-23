package com.example.ah_food_seller.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.ah_food_seller.R
import com.example.ah_food_seller.controller.getRestaurantById
import com.example.ah_food_seller.model.Restaurant
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ViewRestaurantScreen(
    mainAuthOrNavController: NavHostController,
    mainNavController: NavHostController,
    resUser: Restaurant
) {
    val restaurant by remember { mutableStateOf(resUser) }
    val restaurantId = restaurant.id.toString()

    var restaurantMain by remember { mutableStateOf<Restaurant?>(null) }
    val checkedState = remember { mutableStateOf(true) }
    val nameRestaurant = remember { mutableStateOf("") }
    val addressRestaurant = remember { mutableStateOf("") }
    val imgRestaurant = remember { mutableStateOf("") }
    val sdtRestaurant = remember { mutableStateOf("") }
    LaunchedEffect(restaurantId) {
        val restaurant = getRestaurantById(restaurantId).firstOrNull()
        restaurantMain = restaurant
        checkedState.value = restaurant?.statusRestaurant ?: true
        nameRestaurant.value = restaurant?.nameRestaurant ?: ""
        addressRestaurant.value = restaurant?.addressRestaurant ?: ""
        imgRestaurant.value = restaurant?.imageUrl ?: ""
        sdtRestaurant.value = restaurant?.sdtRestaurant ?: "Chưa có thông tin liên lạc!"
    }
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopScreen(
            onClick = {
                mainNavController.navigate("main")
            }
        )
        Box(
            modifier = Modifier
                .padding(10.dp)
                .padding(top = 18.dp)
                .size(200.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                .border(2.dp, Color.Gray, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (imgRestaurant.value != "") {
                Image(
                    painter = rememberImagePainter(imgRestaurant),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp)),  // Ensure image is clipped to rounded corners
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.bg_main_login),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp)),  // Ensure image is clipped to rounded corners
                    contentScale = ContentScale.Crop
                )
            }
        }
        CenterScreen(
            nameRestaurant.value, addressRestaurant.value, sdtRestaurant.value
        )
        LogoutItem(
            mainAuthOrNavController = mainAuthOrNavController,
            mainText = stringResource(id = R.string.logout)
        )
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TopScreen(
    onClick: () -> Unit
) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
        ,
        elevation = 0.dp,
    ) {
        Box(
            modifier = Modifier.background(PrimaryColor),
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    onClick = {
                        onClick()
                              },
                    backgroundColor = PrimaryColor,
                    elevation = 0.dp,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                Text(
                    text = "Thông tin cửa hàng",
                    style = TextStyle(textAlign = TextAlign.Center),
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .padding(end = 45.dp)
                        .fillMaxWidth(),
                )

            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CenterScreen(
    nameRestaurant: String,
    addressRestaurant: String,
    sdtRestaurant: String
) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
        ,
        elevation = 0.dp,
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
        ) {
            CenterItem("Tên Quán", nameRestaurant)
            CenterItem("Địa chỉ", addressRestaurant)
            CenterItem("Số điện thoại", sdtRestaurant)
        }
    }
}

@Composable
private fun CenterItem(
    nameText: String,
    value: String
) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
        ,
        elevation = 0.dp,
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            androidx.compose.material.Text(
                text = nameText,
                style = TextStyle(textAlign = TextAlign.Start),
                fontFamily = Poppins,
                color = SecondaryColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 10.dp)
                    .padding(end = 30.dp)
            )
            androidx.compose.material.Text(
                text = value,
                style = TextStyle(textAlign = TextAlign.End),
                fontFamily = Poppins,
                color = SecondaryColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 10.dp)
                    .padding(end = 20.dp)
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun LogoutItem(mainAuthOrNavController: NavHostController, mainText: String) {
    val auth: FirebaseAuth by lazy { Firebase.auth }
    var user by remember { mutableStateOf(auth.currentUser) }
    val coroutineScope = rememberCoroutineScope()
    Card(
        onClick = {
                    coroutineScope.launch {
                        auth.signOut()
                        user = null
                        mainAuthOrNavController.navigate("Logout")
                    }
        },
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 20.dp,bottom = 8.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                androidx.compose.material.Text(
                    text = mainText,
                    fontFamily = Poppins,
                    color = Color.Red,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )

        }
    }
}

//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//@Preview("hia", showBackground = true)
//fun view(){
//    ViewRestaurantScreen()
//}
