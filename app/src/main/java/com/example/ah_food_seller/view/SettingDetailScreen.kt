package com.example.ah_food_seller.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.ah_food_seller.R
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor


//private val auth = FirebaseAuth.getInstance()
//private val currentUser = auth.currentUser
@ExperimentalMaterialApi
@Composable
fun SettingDetailScreen(
    mainNavController: NavHostController
) {
    var nameRestaurant by remember { mutableStateOf("") }
    var addressRestaurant by remember { mutableStateOf("") }
    var imgProduct by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    // Image picker launcher
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        uri?.let {
            // Handle selected image URI if needed
            imgProduct = it.toString()
        }
    }

//    if (currentUser != null) {
//        val restaurantId = currentUser.uid
//        nameRestaurant = currentUser.uid
//    }
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var keyboardOptionsNumber = KeyboardOptions(keyboardType = KeyboardType.Number)
        var keyboardOptions = KeyboardOptions()

        SettingDetailTop(
            onClick = {
                mainNavController.navigate("main")
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

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000)),  // Semi-transparent background
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        Box(
            modifier = Modifier
                .padding(10.dp)
                .padding(top = 18.dp)
                .size(200.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                .border(2.dp, Color.Gray, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .clickable { imageLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberImagePainter(imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp)),  // Ensure image is clipped to rounded corners
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("Chọn logo quán", color = Color.Gray)
            }
        }

        SettingDetailItem(
            nameText = "Tên quán",
            value = nameRestaurant,
            onValueChange = { nameRestaurant = it },
            lable = "Nhập tên quán.",
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )

        SettingDetailItem(
            nameText = "Địa chỉ",
            value = addressRestaurant,
            onValueChange = { addressRestaurant = it },
            lable = "Nhập địa chỉ.",
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )

        SettingDetailItem(
            nameText = "Số Điện Thoại",
            value = nameRestaurant,
            onValueChange = { nameRestaurant = it },
            lable = "Nhập số liên hệ.",
            keyboardOptions = keyboardOptionsNumber,
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
fun SettingDetailTop(onClick: () -> Unit) {
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
private fun SettingDetailItem(
    keyboardOptions: KeyboardOptions,
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
                keyboardOptions = keyboardOptions,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}