package com.example.ah_food_seller.view

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.ah_food_seller.R
import com.example.ah_food_seller.controller.CategoryViewModel
import com.example.ah_food_seller.controller.addProduct
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor
import com.google.firebase.storage.FirebaseStorage


@ExperimentalMaterialApi
@Composable
fun AddProductScreen(
    mainNavController: NavHostController
) {
    var nameProduct by remember { mutableStateOf("") }
    var contentProduct by remember { mutableStateOf("") }
    var moneyProduct by remember { mutableStateOf("") }
    var imgProduct by remember { mutableStateOf("") }
    var statusProduct:Boolean = true
    var id_Category = remember { mutableStateOf("") }

    var name_Category = remember { mutableStateOf("Chọn danh mục") }

    // State for image URI
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


    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var keyboardOptionsNumber = KeyboardOptions(keyboardType = KeyboardType.Number)
        var keyboardOptions = KeyboardOptions()
        AddProductTop(
            mainText = stringResource(id = R.string.contact),
            onClick = {
                mainNavController.navigate("detailMenu")
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
                Text("Chọn ảnh", color = Color.Gray)
            }
        }

        AddProductItem(
            mainText = stringResource(id = R.string.contact),
            nameText = "Tên món",
            value = nameProduct,
            onValueChange = { nameProduct = it },
            lable = "Nhập tên món.",
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )

        AddProductItem(
            mainText = stringResource(id = R.string.contact),
            nameText = "Miêu tả",
            value = contentProduct,
            onValueChange = { contentProduct = it },
            lable = "Nhập miêu tả.",
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )

        AddProductItem(
            mainText = stringResource(id = R.string.contact),
            nameText = "Giá",
            value = moneyProduct,
            onValueChange = { moneyProduct = it },
            lable = "Nhập giá tiền.",
            keyboardOptions = keyboardOptionsNumber,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )
        SelectComponent(
            modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
            id_Category = id_Category,
            selectedItem = name_Category,
            onItemSelected = { newItem -> name_Category.value = newItem }
        )
        Button(
            onClick = {
                if (nameProduct.isNotEmpty() && contentProduct.isNotEmpty() && moneyProduct.isNotEmpty() && imageUri != null) {
                    isLoading = true
                    imageUri?.let { uri ->
                        uploadImageAndAddProduct(
                            uri,
                            nameProduct,
                            contentProduct,
                            moneyProduct,
                            statusProduct,
                            id_Category.value,
                            mainNavController,
                            onUploadComplete = { isLoading = false }
                        )
                    }
                } else {
                    // Hiển thị thông báo lỗi khi không nhập đủ dữ liệu
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

@ExperimentalMaterialApi
@Composable
fun AddProductTop(mainText: String, onClick: () -> Unit) {
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
                    text = "Thêm món mới",
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
fun AddProductItem(
    keyboardOptions: KeyboardOptions,
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
                keyboardOptions = keyboardOptions,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectComponent(
    modifier: Modifier = Modifier,
    id_Category: MutableState<String>,
    selectedItem: MutableState<String>,
    onItemSelected: (String) -> Unit
) {
    val viewModel: CategoryViewModel = viewModel()
    val categories = viewModel.categories.collectAsState().value

    var expanded by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
    ){
        androidx.compose.material.Text(
            text = "Danh mục",
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedItem.value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_down),
                        contentDescription = "Dropdown icon",
                        modifier = Modifier
//                            .clickable { expanded = !expanded }
                            .size(24.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { category  ->
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text(text = category.nameCategory ) },
                        onClick = {
                            onItemSelected(category.nameCategory)
                            id_Category.value = category.idCategory
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

fun uploadImageAndAddProduct(
    imageUri: Uri,
    nameProduct: String,
    contentProduct: String,
    moneyProduct: String,
    statusProduct: Boolean,
    id_Category: String,
    mainNavController: NavHostController,
    onUploadComplete: () -> Unit
) {
    // Upload image to Firebase Storage
    val storageRef = FirebaseStorage.getInstance().reference
    val imageRef = storageRef.child("products/${System.currentTimeMillis()}.jpg")
    val uploadTask = imageRef.putFile(imageUri)

    uploadTask.addOnSuccessListener {
        imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
            val imgProduct = downloadUri.toString()
            addProduct(
                nameProduct = nameProduct,
                contentProduct = contentProduct,
                moneyProduct = moneyProduct,
                imgProduct = imgProduct,
                statusProduct = statusProduct,
                id_Category = id_Category
            )
            onUploadComplete()
            mainNavController.navigate("detailMenu")
        }.addOnFailureListener {
            onUploadComplete()
        }
    }.addOnFailureListener {
        onUploadComplete()
    }
}
