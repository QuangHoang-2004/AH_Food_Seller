package com.example.ah_food_seller.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.ah_food_seller.ui.theme.LightTextColor
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor

@ExperimentalMaterialApi
@Composable
fun OrderDetailScreen(mainNavController: NavHostController) {
    Column(
        modifier = Modifier
    ) {

        OrderDetailTopScreen(
            mainText = stringResource(id = R.string.contact),
            onClick = {
                mainNavController.navigate("main")
            }
        )

        OrderDetailTop(
            mainText = "Hùng",
            idOderText = "GF-123",
            statusText = 5
        )

        OrderDetailCenter(
            address = "Sơn Viên - Duy Nghĩa - Duy Xuyên"
        )

        OrderDetailBottom(
            mainText = "Hùng",
            iphone = "0865387176"
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun OrderDetailTopScreen(mainText: String, onClick: () -> Unit) {
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
fun OrderDetailTop(mainText: String, idOderText: String, statusText: Int) {
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
                text = "$statusText món cho Hùng",
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
//                    text = "Mã đặt hàng",
                    text = "Địa chỉ",
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                androidx.compose.material.Text(
                    text = address,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 5.dp)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun OrderDetailCenterItem(icon: Int, mainText: String, idOrderText: String, statusText: Int, nameText: String, onClick: () -> Unit) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 3.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ){
                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.motorbike_icon),
                    contentDescription = "",
                    modifier = Modifier.size(35.dp)
                )
                androidx.compose.material.Text(
                    text = nameText,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Row {
                Card(
                    onClick = { onClick() },
                    modifier = Modifier.padding(end = 10.dp),
                    elevation = 0.dp
                ) {
                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.location_icon),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp)
                    )
                }
                Card(
                    onClick = { onClick() },
                    modifier = Modifier,
                    elevation = 0.dp
                ){
                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.phone_icon),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}
@ExperimentalMaterialApi
@Composable
fun OrderDetailBottom(mainText: String, iphone: String) {
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
            mainText = stringResource(id = R.string.contact),
            nameText = mainText,
            iphone = iphone,
            onClick = {}
        )

        OrderDetailBottomItemCenter(
            mainText = stringResource(id = R.string.contact),
            statusText = 1,
            foodnameText = "Cơm hến",
            moneyText = "96.000",
            onClick = {}
        )

        OrderDetailBottomItemCenter(
            mainText = stringResource(id = R.string.contact),
            statusText = 2,
            foodnameText = "Bún bò huế",
            moneyText = "65.000",
            onClick = {}
        )

        OrderDetailBottomItemCenter(
            mainText = stringResource(id = R.string.contact),
            statusText = 1,
            foodnameText = "Bánh canh nam phổ",
            moneyText = "58.000",
            onClick = {}
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun OrderDetailBottomItemTop(mainText: String, iphone: String, nameText: String, onClick: () -> Unit) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 3.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Column {
            Row(
                modifier = Modifier.padding(vertical = 14.dp, horizontal = 14.dp).fillMaxWidth(),
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

            androidx.compose.material.Text(
                text = "Tin nhắn từ khách hàng",
                fontFamily = Poppins,
                color = LightTextColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 14.dp).fillMaxWidth()
            )

        }
    }
}

@ExperimentalMaterialApi
@Composable
fun OrderDetailBottomItemCenter(mainText: String, statusText: Int, moneyText: String, foodnameText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
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