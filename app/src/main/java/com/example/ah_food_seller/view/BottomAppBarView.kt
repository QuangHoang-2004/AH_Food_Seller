package com.example.ah_food_seller.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ah_food_seller.R
import com.example.ah_food_seller.model.BottomMenuItem

@Composable
private fun prepareBottomMenu(): List<BottomMenuItem>{
    val bottomMenuItemList = arrayListOf<BottomMenuItem>()

    bottomMenuItemList.add(
        BottomMenuItem(
            lable = "Order",
            icon = painterResource(id = R.drawable.order_icon)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            lable = "Menu",
            icon = painterResource(id = R.drawable.feedback_icon)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            lable = "MailBox",
            icon = painterResource(id = R.drawable.box_icon)
        )
    )
    bottomMenuItemList.add(
        BottomMenuItem(
            lable = "Profile",
            icon = painterResource(id = R.drawable.profile_icon)
        )
    )

    return bottomMenuItemList
}

@Composable
fun MyBottomAppBar(navController: NavController){
    val bottomMenuItemsList = prepareBottomMenu()
    val contextForToast = LocalContext.current.applicationContext
    var selectedItem by remember {
        mutableStateOf("Home")
    }

    BottomAppBar(
        modifier = Modifier.background(Color.White)
    ) {
        bottomMenuItemsList.forEachIndexed { index, bottomMenuItem ->
            if(index == 2){
                BottomNavigationItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = { /*TODO*/ },
                    enabled = false
                )
            }

            BottomNavigationItem(
                selected = (selectedItem == bottomMenuItem.lable),
                onClick = {
                          selectedItem = bottomMenuItem.lable
//                        Toast.makeText(contextForToast,bottomMenuItem.lable, Toast.LENGTH_LONG).show()
                    when (bottomMenuItem.lable) {
                        "Order" -> navController.navigate("Order")
                        "Menu" -> navController.navigate("Menu")
                        "MailBox" -> navController.navigate("MailBox")
                        "Profile" -> navController.navigate("Profile")
                    }
                },
                icon = {
                    Icon(
                        painter = bottomMenuItem.icon,
                        contentDescription = bottomMenuItem.lable,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                },
                alwaysShowLabel = true,
                enabled = true
            )
        }
    }
}