package com.example.ah_food_seller.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ah_food_seller.R
import com.example.ah_food_seller.controller.getProductName
import com.example.ah_food_seller.controller.getUserName
import com.example.ah_food_seller.controller.updateOrderStatus
import com.example.ah_food_seller.model.Order
import com.example.ah_food_seller.ui.theme.LightTextColor
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor

@ExperimentalMaterialApi
@Composable
fun OrderDetailScreen(mainNavController: NavHostController, orderdetail: Order?) {
    var userName by remember { mutableStateOf("Loading...") }
    var newStatus by remember { mutableStateOf(orderdetail?.statusOrder) }

    LaunchedEffect(orderdetail?.userId) {
        if (orderdetail != null) {
            getUserName(
                userId = orderdetail.userId,
                onSuccess = { name ->
                    userName = name
                },
                onFailure = { exception ->
                    userName = "Error: ${exception.message}"
                }
            )
        }
    }
    Column(
        modifier = Modifier
    ) {

        OrderDetailTopScreen(
            onClick = {
                mainNavController.navigate("main")
            }
        )

        OrderDetailTop(
            idOderText = "${orderdetail?.orderId}",
            statusText = "${orderdetail?.totalQuantity}",
            name = userName
        )

        OrderDetailCenter(
            address = "${orderdetail?.address}"
        )

        OrderDetailBottom(
            mainText = "$userName",
            orderdetail = orderdetail,
            newStatus = newStatus,
            onClick = {
                if(newStatus == "Chờ xác nhận") {
                    newStatus = "Đã xác nhận"
                }else if(newStatus == "Đã xác nhận"){
                    newStatus = "Đang được giao"
                }
                if (orderdetail != null ) {
                    updateOrderStatus(orderdetail.orderId, newStatus?: "",
                        onSuccess = {
                            if(newStatus == "Đang được giao"){
                                mainNavController.navigate("main")
                            }
                        }
                    )
                }
            }
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun OrderDetailTopScreen(onClick: () -> Unit) {
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
                    text = "Chi tiết đơn hàng",
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
fun OrderDetailTop(idOderText: String, statusText: String, name: String) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 14.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            androidx.compose.material.Text(
                text = idOderText,
                fontFamily = Poppins,
                color = LightTextColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            androidx.compose.material.Text(
                text = "$statusText món cho $name",
                fontFamily = Poppins,
                color = SecondaryColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )
//            androidx.compose.material.Text(
//                text = "Đang tìm kiếm tài xế...",
//                fontFamily = Poppins,
//                color = SecondaryColor,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold,
//            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun OrderDetailCenter(address: String) {
    Column(
        modifier = Modifier
    ) {
//        androidx.compose.material.Text(
//            text = "Tài xế",
//            fontFamily = Poppins,
//            color = SecondaryColor,
//            fontSize = 17.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .padding(vertical = 8.dp, horizontal = 10.dp)
//        )
//
//        OrderDetailCenterItem(
//            icon = R.drawable.ic_launcher_background,
//            mainText = stringResource(id = R.string.contact),
//            statusText = 5,
//            nameText = "Nguyễn Quốc Anh",
//            idOrderText = "ADR-0907351-3-1927",
//            onClick = {}
//        )

        Card(
            backgroundColor = Color.White,
            modifier = Modifier
                .padding(bottom = 5.dp)
                .fillMaxWidth(),
            elevation = 0.dp,
        ) {
            Row(
                modifier = Modifier.padding(vertical = 14.dp, horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material.Text(
                    text = "Địa chỉ",
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 40.dp)
                )
                androidx.compose.material.Text(
                    text = address,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

//@ExperimentalMaterialApi
//@Composable
//fun OrderDetailCenterItem(icon: Int, mainText: String, idOrderText: String, statusText: Int, nameText: String, onClick: () -> Unit) {
//    Card(
//        backgroundColor = Color.White,
//        modifier = Modifier
//            .padding(bottom = 3.dp)
//            .fillMaxWidth(),
//        elevation = 0.dp,
//    ) {
//        Row(
//            modifier = Modifier.padding(vertical = 14.dp, horizontal = 14.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Row (
//                verticalAlignment = Alignment.CenterVertically,
//            ){
//                androidx.compose.material.Icon(
//                    painter = painterResource(id = R.drawable.motorbike_icon),
//                    contentDescription = "",
//                    modifier = Modifier.size(35.dp)
//                )
//                androidx.compose.material.Text(
//                    text = nameText,
//                    fontFamily = Poppins,
//                    color = SecondaryColor,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(start = 10.dp)
//                )
//            }
//            Row {
//                Card(
//                    onClick = { onClick() },
//                    modifier = Modifier.padding(end = 10.dp),
//                    elevation = 0.dp
//                ) {
//                    androidx.compose.material.Icon(
//                        painter = painterResource(id = R.drawable.location_icon),
//                        contentDescription = "",
//                        modifier = Modifier.size(25.dp)
//                    )
//                }
//                Card(
//                    onClick = { onClick() },
//                    modifier = Modifier,
//                    elevation = 0.dp
//                ){
//                    androidx.compose.material.Icon(
//                        painter = painterResource(id = R.drawable.phone_icon),
//                        contentDescription = "",
//                        modifier = Modifier.size(25.dp)
//                    )
//                }
//            }
//        }
//    }
//}
@ExperimentalMaterialApi
@Composable
fun OrderDetailBottom(mainText: String, orderdetail: Order?, onClick: () -> Unit, newStatus: String?) {
    var Status by remember { mutableStateOf("") }
    var boolean: Boolean = false
    if(newStatus == "Chờ xác nhận") {
        Status = "Xác nhận"
        boolean = true
    }else if(newStatus == "Đã xác nhận"){
        Status = "Hoàn thành"
    }
    Column(
        modifier = Modifier
    ) {
        androidx.compose.material.Text(
            text = "Tóm tắt đơn hàng",
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 13.dp, horizontal = 10.dp)
        )

        OrderDetailBottomItemTop(
            nameText = mainText,
            iphone = "${orderdetail?.phone}",
            onClick = {}
        )

        LazyColumn {
            if (orderdetail != null) {
                items(orderdetail.items) { item ->
                    var productName by remember { mutableStateOf("Loading...") }

                    LaunchedEffect(item.productId) {
                        getProductName(
                            productId = item.productId,
                            onSuccess = { name ->
                                productName = name
                            },
                            onFailure = { exception ->
                                productName = "Error: ${exception.message}"
                            }
                        )
                    }
                    OrderDetailBottomItemCenter(
                        statusText = item.quantity,
                        foodnameText = "$productName",
                        moneyText = "${item.price}",
                    )
                }
                item {
                    Column {
                        Button(
                            onClick = { onClick() },
                            modifier = Modifier
                                .padding(top = 20.dp, start = 50.dp, end = 50.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = "$Status", color = Color.White)
                        }
                        if(boolean) {
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .padding(start = 50.dp, end = 50.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(text = "Hủy đơn", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun OrderDetailBottomItemTop(iphone: String, nameText: String, onClick: () -> Unit) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 3.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(vertical = 14.dp, horizontal = 14.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material.Text(
                    text = nameText,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
                Row {
                    Card(
                        onClick = { onClick() },
                        modifier = Modifier,
                        elevation = 0.dp
                    ){
                        androidx.compose.material.Text(
                            text = "sdt: $iphone",
                            fontFamily = Poppins,
                            color = SecondaryColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )
//                        androidx.compose.material.Icon(
//                            painter = painterResource(id = R.drawable.phone_icon),
//                            contentDescription = "",
//                            modifier = Modifier.size(25.dp)
//                        )
                    }
                }
            }

//            androidx.compose.material.Text(
//                text = "Tin nhắn từ khách hàng",
//                fontFamily = Poppins,
//                color = LightTextColor,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(horizontal = 14.dp).fillMaxWidth()
//            )

        }
    }
}

@ExperimentalMaterialApi
@Composable
fun OrderDetailBottomItemCenter(statusText: Int, moneyText: String, foodnameText: String) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                androidx.compose.material.Text(
                    text = "$statusText",
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                )
                androidx.compose.material.Text(
                    text = "x",
                    fontFamily = Poppins,
                    color = LightTextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                androidx.compose.material.Text(
                    text = foodnameText,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            androidx.compose.material.Text(
                text = "$moneyText",
                fontFamily = Poppins,
                color = LightTextColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 5.dp)
            )
    }
    }
}