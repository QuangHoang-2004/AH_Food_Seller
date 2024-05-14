package com.example.ah_food_seller.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.ah_food_seller.R
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor

@ExperimentalMaterialApi
@Composable
fun MailBoxScreen() {
    Column(
        modifier = Modifier
    ) {

        MailBoxTop(
            mainText = stringResource(id = R.string.contact),
        )

        androidx.compose.material.Text(
            text = "Cập nhật(2)",
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
        )

        MailBoxItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.contact),
            statusText = 5,
            nameText = "Cà Phê",
            onClick = {}
        )
        MailBoxItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.contact),
            statusText = 8,
            nameText = "Nước ép",
            onClick = {}
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun MailBoxTop(mainText: String) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
        ,
        elevation = 0.dp,
    ) {
        Box(
            modifier = Modifier.background(PrimaryColor)
        ){
            androidx.compose.material.Text(
                text = "Hộp thư",
                style = TextStyle(textAlign = TextAlign.Center),
                fontFamily = Poppins,
                color = SecondaryColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MailBoxItem(icon: Int, mainText: String, statusText: Int, nameText: String, onClick: () -> Unit) {
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
            Box(modifier = Modifier.size(30.dp)) {
                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.email_icon),
                    contentDescription = "",
                    modifier = Modifier.size(25.dp)
                )
            }
            Column (
            ){
                androidx.compose.material.Text(
                    text = "Đối tác đã kiểm tra đơn hàng kĩ chưa?",
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                )
                androidx.compose.material.Text(
                    text = "Dưới đây là lưu ý vận hành quan trọng..",
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Box(modifier = Modifier.size(30.dp)) {
                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.ic_right_arrow),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewMailBox() {
    MailBoxScreen()
}