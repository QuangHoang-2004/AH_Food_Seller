package com.example.ah_food_seller.viewmodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.SecondaryColor
import com.google.firebase.Firebase
import com.google.firebase.database.database

@Composable
fun HomeScreen(){
    var name by remember {
        mutableStateOf("")
    }
    var pass by remember {
        mutableStateOf("")
    }
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier.fillMaxWidth().height(80.dp),
            contentAlignment = Alignment.Center
        ){
            androidx.compose.material.Text(
                text = "Trang Đăng Nhập/Đăng ký",
                fontFamily = Poppins,
                color = SecondaryColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth().padding(end = 10.dp)
        ){
            Column (
                modifier = Modifier
                    .height(120.dp)
                    .padding(start = 10.dp, end = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ){
                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(bottom = 10.dp),
                    contentAlignment = Alignment.Center
                ){
                    androidx.compose.material.Text(
                        text = "Tên",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(bottom = 10.dp),
                    contentAlignment = Alignment.Center
                ){
                    androidx.compose.material.Text(
                        text = "Mật Khẩu",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Column {
                EditTextFile(
                    value = name,
                    onValueChange = { name = it },
                    lable = "Tên đăng nhập.",
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                )
                EditTextFile(
                    value = pass,
                    onValueChange = { pass = it },
                    lable = "Mật khẩu đăng nhập.",
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                )
            }
        }
        Row(
            modifier = Modifier.padding(top = 20.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(bottom = 20.dp).width(150.dp)
            ) {
                Text(text = "Đăng Nhập")
            }
            Button(
                onClick = { /*TODO*/
                    val database = Firebase.database
                    val myRef = database.getReference("TaiKhoan")
                    myRef.setValue("hoang")

                          },
                modifier = Modifier.padding(start = 20.dp).width(150.dp)
            ) {
                Text(text = "Đăng Ký")
            }
        }
    }
}

@Composable
fun EditTextFile(
    lable: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){

    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(lable) },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.height(50.dp)
        )
    }
}

//@Composable
//@Preview("hoang", showBackground = true)
//fun Home(){
//    HomeScreen()
//}









