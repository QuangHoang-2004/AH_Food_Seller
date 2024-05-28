package com.example.ah_food_seller.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.ah_food_seller.ui.theme.LightPrimaryColor
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor
import com.example.ah_food_seller.ui.theme.Shapes
import com.example.ah_food_seller.R
import com.example.ah_food_seller.controller.getRestaurantById
import com.example.ah_food_seller.controller.updateRestaurantStatus
import com.example.ah_food_seller.model.Restaurant
import com.example.ah_food_seller.ui.theme.PlaceholderColor
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreenMain(
    mainAuthOrNavController: NavHostController,
    resUser: Restaurant
){
    val mainNavController = rememberNavController()

    NavHost(navController = mainNavController, startDestination = "main"){
        composable("main"){
            SettingsScreen(
                mainNavController = mainNavController,
                resUser = resUser
            )
        }
        composable("settingdetail"){
            ViewRestaurantScreen(
                mainAuthOrNavController = mainAuthOrNavController,
                mainNavController = mainNavController,
                resUser = resUser
            )
        }

        composable("shopsettings"){
            RestaurantScreenMain(
                mainNavController = mainNavController,
                        resUser = resUser
            )
        }
    }
}



@ExperimentalMaterialApi
@Composable
fun SettingsScreen(
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
    LaunchedEffect(restaurantId) {
        val restaurant = getRestaurantById(restaurantId).firstOrNull()
        restaurantMain = restaurant
        checkedState.value = restaurant?.statusRestaurant ?: true
        nameRestaurant.value = restaurant?.nameRestaurant ?: ""
        addressRestaurant.value = restaurant?.addressRestaurant ?: ""
        imgRestaurant.value = restaurant?.imageUrl ?: ""
    }

    Box(Modifier.verticalScroll(rememberScrollState())) {
        Column {
            updateRestaurantStatus(restaurantId, checkedState.value)
            ProfileCardUI(
                name = nameRestaurant.value,
                address = addressRestaurant.value,
                img = imgRestaurant,
                checkedState = checkedState,
                mainNavController = mainNavController,
            )
            GeneralOptionsUI(
                mainNavController = mainNavController
            )
            SupportOptionsUI()
        }
    }
}


@Composable
fun ProfileCardUI(name : String, address: String, img: MutableState<String>, checkedState: MutableState<Boolean>, mainNavController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
//            .height(230.dp)
            .padding(10.dp),
        backgroundColor = Color.White,
        elevation = 0.dp,
        shape = Shapes.large
    ) {
        Column (
            modifier = Modifier
        ){
            Row(
                modifier = Modifier
                    .padding(20.dp, 20.dp, 20.dp, 0.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    androidx.compose.material.Text(
                        text = name,
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(200.dp)
                    )

                    androidx.compose.material.Text(
                        text = address,
                        fontFamily = Poppins,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.width(200.dp)
                    )

                    Button(
                        modifier = Modifier.padding(top = 10.dp),
                        onClick = {
                            mainNavController.navigate("settingdetail")
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = PrimaryColor
                        ),
                        contentPadding = PaddingValues(horizontal = 30.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 2.dp
                        ),
                        shape = Shapes.medium
                    ) {
                        androidx.compose.material.Text(
                            text = "Xem chi tiết",
                            fontFamily = Poppins,
                            color = SecondaryColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(120.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (img.value != "") {
                        Image(
                            painter = rememberImagePainter(img.value),
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
            }
            Row(
                modifier = Modifier
                    .background(color = PlaceholderColor)
                    .padding(20.dp, 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                ) {
                    androidx.compose.material.Text(
                        text = "Tình trạng quán",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    androidx.compose.material.Text(
                        text = "Tắt để tạm dừng các đơn hàng đến",
                        fontFamily = Poppins,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Row (
                    modifier = Modifier
                        .padding(10.dp, 10.dp, 0.dp, 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    androidx.compose.material.Text(
                        text = "Mở cửa",
                        fontFamily = Poppins,
                        color = Color(40, 166, 54),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 3.5.dp)
                    )

                    Switch(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it},
                        colors = SwitchDefaults.colors(Color.Green),
                        modifier = Modifier
                            .padding(10.dp, 5.dp, 0.dp, 5.dp)
                    )
                }

            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun GeneralOptionsUI(
    mainNavController: NavHostController
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        androidx.compose.material.Text(
            text = "Cài đặt",
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        GeneralSettingItem(
            icon = R.drawable.shop_icon,
            mainText = stringResource(id = R.string.shopsetting),
            onClick = {
                mainNavController.navigate("shopsettings")
            }
        )
        GeneralSettingItem(
            icon = R.drawable.setting_icon,
            mainText = stringResource(id = R.string.applicationsetting),
            onClick = {}
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun GeneralSettingItem(icon: Int, mainText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = Shapes.medium)
                        .background(LightPrimaryColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(5.dp)
                            .height(30.dp)
                            .width(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.offset(y = (2).dp)
                ) {
                    androidx.compose.material.Text(
                        text = mainText,
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )

        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SupportOptionsUI() {
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        androidx.compose.material.Text(
            text = "Hỗ trợ",
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        SupportItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.contact),
            onClick = {}
        )
        SupportItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.privacy_policy),
            onClick = {}
        )
        SupportItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.language),
            onClick = {}
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun SupportItem(icon: Int, mainText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 8.dp)
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
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = Shapes.medium)
                        .background(LightPrimaryColor),
                        contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(5.dp)
                            .height(30.dp)
                            .width(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                androidx.compose.material.Text(
                    text = mainText,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
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