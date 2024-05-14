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
import androidx.compose.material3.TextButton
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
import androidx.navigation.NavHostController
import com.example.ah_food_seller.R
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.Purple500
import com.example.ah_food_seller.ui.theme.SecondaryColor

@ExperimentalMaterialApi
@Composable
fun AddCategoryScreen(
    mainNavController: NavHostController
) {
    var pass by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
    ) {

        AddCategoryTop(
            mainText = stringResource(id = R.string.contact),
            onClick = {
                mainNavController.navigate("detailMenu")
            }
        )

        AddCategoryItem(
            mainText = stringResource(id = R.string.contact),
            nameText = "Tên",
            value = pass,
            onValueChange = { pass = it },
            lable = "Nhập tên danh mục.",
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = { /*TODO*/ },
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
fun AddCategoryTop(mainText: String, onClick: () -> Unit) {
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
                    text = "Thêm danh mục mới",
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
fun AddCategoryItem(
    mainText: String,
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
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

//@OptIn(ExperimentalMaterialApi::class)
//@Preview(showBackground = true)
//@Composable
//fun PreviewAddProduct() {
//    AddProductScreen()
//}