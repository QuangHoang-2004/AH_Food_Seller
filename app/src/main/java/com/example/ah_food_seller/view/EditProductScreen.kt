package com.example.ah_food_seller.view

import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.ah_food_seller.R
import com.example.ah_food_seller.controller.Product
import com.example.ah_food_seller.controller.CategoryViewModel
import com.example.ah_food_seller.controller.getProductImgById
import com.example.ah_food_seller.controller.updateProduct
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson

@ExperimentalMaterialApi
@Composable
fun EditProductScreen(
    mainNavController: NavHostController,
    navBackStackEntry: NavBackStackEntry
) {
    val categoryJson = navBackStackEntry.arguments?.getString("product") ?: ""
    val decodedCategoryJson = Uri.decode(categoryJson)
    val product = Gson().fromJson(decodedCategoryJson, Product::class.java)

    var nameProduct by remember { mutableStateOf(product.nameProduct) }
    var contentProduct by remember { mutableStateOf(product.contentProduct) }
    var moneyProduct by remember { mutableStateOf(product.moneyProduct) }
    // Gán giá trị khởi tạo cho imgProduct
    var imgProduct by remember { mutableStateOf("") }

    // Gọi hàm getProductImgById khi productId thay đổi
    LaunchedEffect(product.idProduct) {
        val idProduct = product.idProduct
        val img = getProductImgById(idProduct)
        // Cập nhật giá trị imgProduct khi kết quả trả về
        imgProduct = img ?: ""
    }

    val statusProduct:Boolean = product.statusProduct
    val id_Category = remember { mutableStateOf(product.id_Category) }

    val name_Category = remember { mutableStateOf("Chọn danh mục") }

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
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val keyboardOptionsNumber = KeyboardOptions(keyboardType = KeyboardType.Number)
        val keyboardOptions = KeyboardOptions()
        EditProductTop(
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
        }else {

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
                if (imgProduct.isNotEmpty()) {
                    Image(
                        painter = rememberImagePainter(imgProduct),
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

            EditProductItem(
                nameText = "Tên món",
                value = nameProduct,
                onValueChange = { nameProduct = it },
                lable = "Nhập tên món.",
                keyboardOptions = keyboardOptions,
            )

            EditProductItem(
                nameText = "Miêu tả",
                value = contentProduct,
                onValueChange = { contentProduct = it },
                lable = "Nhập miêu tả.",
                keyboardOptions = keyboardOptions,
            )

            EditProductItem(
                nameText = "Giá",
                value = moneyProduct,
                onValueChange = { moneyProduct = it },
                lable = "Nhập giá tiền.",
                keyboardOptions = keyboardOptionsNumber,
            )
            SelectComponentEdit(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                id_Category = id_Category,
                selectedItem = name_Category,
                onItemSelected = { newItem -> name_Category.value = newItem }
            )

            Button(
                onClick = {
                    if (nameProduct.isNotEmpty() && contentProduct.isNotEmpty() && moneyProduct.isNotEmpty()) {
                        isLoading = true
                        if (imageUri != null) {
                            uploadImageAndProduct(
                                imageUri,
                                product.idProduct,
                                nameProduct,
                                contentProduct,
                                moneyProduct,
                                statusProduct,
                                id_Category.value,
                                mainNavController,
                                onUploadComplete = { isLoading = false }
                            )
                        } else {
                            uploadImageAndProduct(
                                null, // Truyền null vào khi không chọn ảnh
                                product.idProduct,
                                nameProduct,
                                contentProduct,
                                moneyProduct,
                                statusProduct,
                                id_Category.value,
                                mainNavController,
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
fun EditProductTop(onClick: () -> Unit) {
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
                    text = "Sửa món ăn",
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
fun EditProductItem(
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectComponentEdit(
    modifier: Modifier = Modifier,
    id_Category: MutableState<String>,
    selectedItem: MutableState<String>,
    onItemSelected: (String) -> Unit
) {
    val viewModel: CategoryViewModel = viewModel()
    val categories = viewModel.categories.collectAsState().value

    var expanded by remember { mutableStateOf(false) }

    // Tìm danh mục tương ứng với id_Category được cung cấp
    val selectedCategory = categories.find { it.idCategory == id_Category.value }

    // Đặt giá trị ban đầu của selectedItem là tên của danh mục được chọn, nếu có
    if (selectedCategory != null) {
        selectedItem.value = selectedCategory.nameCategory
    }

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
                        contentDescription = "Biểu tượng mũi tên",
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


private fun uploadImageAndProduct(
    imageUri: Uri?,
    idProduct: String,
    nameProduct: String,
    contentProduct: String,
    moneyProduct: String,
    statusProduct: Boolean,
    id_Category: String,
    mainNavController: NavHostController,
    onUploadComplete: () -> Unit
) {
    if (imageUri != null) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("products/${System.currentTimeMillis()}.jpg")
        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                val imgProductNew = downloadUri.toString()
                updateProduct(
                    productId = idProduct,
                    nameProduct = nameProduct,
                    contentProduct = contentProduct,
                    moneyProduct = moneyProduct,
                    imgProduct = imgProductNew,
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
    } else {
        updateProduct(
            productId = idProduct,
            nameProduct = nameProduct,
            contentProduct = contentProduct,
            moneyProduct = moneyProduct,
            imgProduct = null,
            statusProduct = statusProduct,
            id_Category = id_Category
        )
        onUploadComplete()
        mainNavController.navigate("detailMenu")
    }
}