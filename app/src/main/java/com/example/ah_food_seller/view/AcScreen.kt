package com.example.ah_food_seller.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ah_food_seller.R
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor
import com.google.firebase.auth.FirebaseAuth


//private val auth = FirebaseAuth.getInstance()
//private val currentUser = auth.currentUser
@ExperimentalMaterialApi
@Composable
fun ACScreen(
//    mainNavController: NavHostController
) {
    var nameRestaurant by remember { mutableStateOf("") }
    var addressRestaurant by remember { mutableStateOf("") }

//    if (currentUser != null) {
//        val restaurantId = currentUser.uid
//        nameRestaurant = currentUser.uid
//    }
    Column(
        modifier = Modifier
    ) {
//        var keyboardOptionsNumber = KeyboardOptions(keyboardType = KeyboardType.Number)
//        var keyboardOptions = KeyboardOptions()

        AcTop(
            onClick = {
//                mainNavController.navigate("main")
            }
        )

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            androidx.compose.material.Text(
                text = "Cửa hàng",
                fontFamily = Poppins,
                color = SecondaryColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 10.dp)
            )
        }

        ACItem(
            nameText = "Tên quán",
            value = nameRestaurant,
            onValueChange = { nameRestaurant = it },
            lable = "Nhập tên quán.",
//            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )

        ACItem(
            nameText = "Địa chỉ",
            value = addressRestaurant,
            onValueChange = { addressRestaurant = it },
            lable = "Nhập địa chỉ.",
//            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {

            },
            modifier = Modifier
                .padding(top = 20.dp, start = 50.dp, end = 50.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Xác Nhận", color = Color.White)
        }

    }
}

@ExperimentalMaterialApi
@Composable
fun AcTop(onClick: () -> Unit) {
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
                    onClick = { onClick() },
                    backgroundColor = PrimaryColor,
                    elevation = 0.dp,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                ){
                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                androidx.compose.material.Text(
                    text = "Chỉnh sửa thông tin",
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


@ExperimentalMaterialApi
@Composable
private fun ACItem(
//    keyboardOptions: KeyboardOptions,
    nameText: String,
    lable: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Column (

        ) {
            androidx.compose.material.Text(
                text = nameText,
                fontFamily = Poppins,
                color = SecondaryColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            TextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(lable) },
//                keyboardOptions = keyboardOptions,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewMenuDetail() {
    ACScreen()
}