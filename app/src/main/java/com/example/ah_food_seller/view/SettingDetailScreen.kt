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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.ah_food_seller.controller.getRestaurantById
import com.example.ah_food_seller.controller.updateProduct
import com.example.ah_food_seller.controller.updateRestaurant
import com.example.ah_food_seller.model.Restaurant
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.firstOrNull


//private val auth = FirebaseAuth.getInstance()
//private val currentUser = auth.currentUser
@ExperimentalMaterialApi
@Composable
fun SettingDetailScreen(
    mainNavControllerM: NavHostController,
    resUser: Restaurant
) {
    val restaurant by remember { mutableStateOf(resUser) }
    val restaurantId = restaurant.id.toString()

    var restaurantMain by remember { mutableStateOf<Restaurant?>(null) }
    val nameRestaurant = remember { mutableStateOf("") }
    val addressRestaurant = remember { mutableStateOf("") }
    val sdtRestaurant = remember { mutableStateOf("") }
    val imgRestaurant = remember { mutableStateOf("") }
    LaunchedEffect(restaurantId) {
        val restaurant = getRestaurantById(restaurantId).firstOrNull()
        restaurantMain = restaurant
        nameRestaurant.value = restaurant?.nameRestaurant ?: ""
        addressRestaurant.value = restaurant?.addressRestaurant ?: ""
        imgRestaurant.value = restaurant?.imageUrl ?: ""
        sdtRestaurant.value = restaurant?.sdtRestaurant ?: "Chưa có thông tin liên lạc!"
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    // Image picker launcher
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        uri?.let {
            // Handle selected image URI if needed
            imgRestaurant.value = it.toString()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), // Đảm bảo cột có thể cuộn dọc
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var keyboardOptionsNumber = KeyboardOptions(keyboardType = KeyboardType.Number)
        var keyboardOptions = KeyboardOptions()

        SettingDetailTop(
            onClick = {
                mainNavControllerM.navigate("main")
            }
        )
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000)),  // Semi-transparent background
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else{
            Row (
                modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
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
                if (imgRestaurant.value != "") {
                    Image(
                        painter = rememberImagePainter(imgRestaurant.value),
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
                value = nameRestaurant.value,
                onValueChange = { nameRestaurant.value = it },
                lable = "Nhập tên quán.",
                keyboardOptions = keyboardOptions,
            )

            SettingDetailItem(
                nameText = "Địa chỉ",
                value = addressRestaurant.value,
                onValueChange = { addressRestaurant.value = it },
                lable = "Nhập địa chỉ.",
                keyboardOptions = keyboardOptions,
            )

            SettingDetailItem(
                nameText = "Số Điện Thoại",
                value = sdtRestaurant.value,
                onValueChange = { sdtRestaurant.value = it },
                lable = "Nhập số liên hệ.",
                keyboardOptions = keyboardOptionsNumber,
            )

            Button(
                onClick = {
                    if (nameRestaurant.value.isNotEmpty() && addressRestaurant.value.isNotEmpty() && sdtRestaurant.value.isNotEmpty()) {
                        isLoading = true
                        if (imageUri != null) {
                            uploadImageAndRestaurant(
                                imageUri,
                                nameRestaurant.value,
                                addressRestaurant.value,
                                sdtRestaurant.value,
                                mainNavControllerM,
                                onUploadComplete = { isLoading = false }
                            )
                        } else {
                            uploadImageAndRestaurant(
                                null,
                                nameRestaurant.value,
                                addressRestaurant.value,
                                sdtRestaurant.value,
                                mainNavControllerM,
                                onUploadComplete = { isLoading = false }
                            )
                        }
                    }
                },
                modifier = Modifier
                    .padding(top = 20.dp, start = 50.dp, end = 50.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Xác Nhận", color = Color.White)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SettingDetailTop(onClick: () -> Unit) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
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

private fun uploadImageAndRestaurant(
    imageUri: Uri?,
    nameRestaurant: String,
    addressRestaurant: String,
    sdtRestaurant: String,
    mainNavControllerM: NavHostController,
    onUploadComplete: () -> Unit
) {
    if (imageUri != null) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("restaurants/${System.currentTimeMillis()}.jpg")
        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                val imgProductNew = downloadUri.toString()
                updateRestaurant(
                    nameRestaurant = nameRestaurant,
                    addressRestaurant = addressRestaurant,
                    sdtRestaurant = sdtRestaurant,
                    imgRestaurant = imgProductNew,
                )
                onUploadComplete()
                mainNavControllerM.navigate("main")
            }.addOnFailureListener {
                onUploadComplete()
            }
        }.addOnFailureListener {
            onUploadComplete()
        }
    } else {
        updateRestaurant(
            nameRestaurant = nameRestaurant,
            addressRestaurant = addressRestaurant,
            sdtRestaurant = sdtRestaurant,
            imgRestaurant = null,
        )
        onUploadComplete()
        mainNavControllerM.navigate("main")
    }
}