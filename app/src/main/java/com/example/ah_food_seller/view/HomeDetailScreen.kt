package com.example.ah_food_seller.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ah_food_seller.R
import com.example.ah_food_seller.ui.theme.AH_Food_SellerTheme

@Composable
fun HomeActionItem(modifier: Modifier = Modifier, @DrawableRes icon: Int, name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {

        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))

        Text(name, style = TextStyle(fontSize = 12.sp))
    }
}

@Composable
fun HomeAction(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        HomeActionItem(icon = R.drawable.ic_home, name = "Trang chủ")
        HomeActionItem(icon = R.drawable.ic_order, name = "Đơn hàng")
        HomeActionItem(icon = R.drawable.ic_menu, name = "Thực đơn")
        HomeActionItem(icon = R.drawable.ic_mailbox, name = "Hộp thư")
        HomeActionItem(icon = R.drawable.ic_other, name = "Khác")

    }
}
@Composable
@Preview(name = "Video Info Preview", showBackground = true)
fun VideoActionPreview() {
    AH_Food_SellerTheme {
        HomeAction()
    }
}