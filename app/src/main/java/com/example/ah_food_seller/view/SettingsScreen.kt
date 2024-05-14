package com.example.ah_food_seller.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ah_food_seller.ui.theme.LightPrimaryColor
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor
import com.example.ah_food_seller.ui.theme.Shapes
import com.example.ah_food_seller.R
import com.example.ah_food_seller.ui.theme.AH_Food_SellerTheme
import com.example.ah_food_seller.ui.theme.PlaceholderColor

@ExperimentalMaterialApi
@Composable
fun SettingsScreen() {
    Box(Modifier.verticalScroll(rememberScrollState())) {
        Column() {
//            HeaderText()
            ProfileCardUI()
            GeneralOptionsUI()
            SupportOptionsUI()
        }
    }
}

@Composable
fun HeaderText() {
    androidx.compose.material.Text(
        text = "Cài đặt",
        fontFamily = Poppins,
        color = SecondaryColor,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp
    )
}

@Composable
fun ProfileCardUI() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
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
                Column() {
                    androidx.compose.material.Text(
                        text = "Tên Quán",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    androidx.compose.material.Text(
                        text = "Địa chỉ quán",
                        fontFamily = Poppins,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Button(
                        modifier = Modifier.padding(top = 10.dp),
                        onClick = {},
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
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "",
                    modifier = Modifier.height(120.dp)
                )
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

                    val checktedState = remember {
                        mutableStateOf( false)
                    }

                    Switch(
                        checked = checktedState.value,
                        onCheckedChange = { checktedState.value = it},
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
fun GeneralOptionsUI() {
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
            onClick = {}
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
        SupportItem(
            icon = R.drawable.logout_icon,
            mainText = stringResource(id = R.string.logout),
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview("Preview", showBackground = true)
fun A(){
    AH_Food_SellerTheme {
        SettingsScreen()
    }
}