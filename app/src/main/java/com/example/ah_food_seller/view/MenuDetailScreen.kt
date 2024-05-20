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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ah_food_seller.R
import com.example.ah_food_seller.controller.CategoryViewModel
import com.example.ah_food_seller.controller.Product
import com.example.ah_food_seller.controller.ProductViewModel
import com.example.ah_food_seller.controller.deleteCategory
import com.example.ah_food_seller.controller.deleteProduct
import com.example.ah_food_seller.model.Category
import com.example.ah_food_seller.ui.theme.BackgroundColor
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.Purple500
import com.example.ah_food_seller.ui.theme.SecondaryColor
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@ExperimentalMaterialApi
@Composable
fun MenuDetailScreen(mainNavController: NavHostController) {
    Column(
        modifier = Modifier
    ) {

        MenuDetailTop(
            mainText = stringResource(id = R.string.contact),
            onClick = {
                mainNavController.navigate("main")
            }
        )

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            androidx.compose.material.Text(
                text = "Thực đơn",
                fontFamily = Poppins,
                color = SecondaryColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 10.dp)
            )

            Row {
                TextButton(
                    onClick = {
                        mainNavController.navigate("addCategory")
                              },
                    modifier = Modifier.padding(end = 15.dp)
                ) {
                    Text(
                        text = "Thêm danh mục",
                        fontFamily = Poppins,
                        color = Purple500,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                TextButton(
                    onClick = {
                        mainNavController.navigate("addProduct")
                              },
                    modifier = Modifier.padding(end = 15.dp)
                ) {
                    Text(
                        text = "Thêm món ăn",
                        fontFamily = Poppins,
                        color = Purple500,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
        CategoryListScreen(mainNavController = mainNavController)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CategoryListScreen(mainNavController: NavHostController) {
    val viewModel: CategoryViewModel = viewModel()
    val categories = viewModel.categories.collectAsState().value

    LazyColumn(modifier = Modifier) {
        items(categories) { category ->
            MenuDetailItem(
                mainNavControllerM = mainNavController,
                statusText = 5, // Adjust as per your logic
                category = category
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MenuDetailTop(mainText: String, onClick: () -> Unit) {
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
                    text = "Thiết lập thực đơn",
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
private fun MenuDetailItem(
    mainNavControllerM: NavHostController,
    statusText: Int,
    category: com.example.ah_food_seller.controller.Category
) {
    val mainNavController = rememberNavController()
    var boolean: Boolean = true


    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Column (
            modifier = Modifier.fillMaxWidth()
        ){
            Row(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material.Text(
                    text = category.nameCategory,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Row {
                    TextButton(
                        onClick = {
                            deleteCategory(categoryId = category.idCategory)
                            mainNavControllerM.navigate("detailMenu")
                        },
                        modifier = Modifier.padding(end = 15.dp)
                    ) {
                        androidx.compose.material.Text(
                            text = "Xóa",
                            fontFamily = Poppins,
                            color = Purple500,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    TextButton(
                        onClick = {
                            val categoryJson = Gson().toJson(category)
                            val encodedCategoryJson = URLEncoder.encode(categoryJson, StandardCharsets.UTF_8.toString())
                            mainNavControllerM.navigate("editCategory/${encodedCategoryJson}")
                        },
                        modifier = Modifier.padding(end = 15.dp)
                    ) {
                        androidx.compose.material.Text(
                            text = "Sửa",
                            fontFamily = Poppins,
                            color = Purple500,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    IconButton(onClick = {
                        if (boolean){
                            mainNavController.navigate("listProduct")
                            boolean = false
                        }else{
                            mainNavController.navigate("main")
                            boolean = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_down),
                            contentDescription = "Arrow Down",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified // Use Color.Unspecified to keep the original color of the icon
                        )
                    }
                }
            }
            NavHost(navController = mainNavController, startDestination = "main"){
                composable("main"){
                }
                composable("listProduct"){
                    ProductListScreen(id_Category = category.idCategory, mainNavController = mainNavController, mainNavControllerM = mainNavControllerM)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProductListScreen(id_Category: String, mainNavController: NavHostController, mainNavControllerM: NavHostController) {
    val viewModel: ProductViewModel = viewModel()
    val products by viewModel.products.collectAsState()

    Column(modifier = Modifier.padding(horizontal = 15.dp)) {
        products.forEach() { product ->
            if (product.id_Category == id_Category) {
                MainMenuItemProduct(
                    product = product,
                    mainNavController = mainNavController,
                    mainNavControllerM = mainNavControllerM
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun MainMenuItemProduct(
    product: Product,
    mainNavController: NavHostController,
    mainNavControllerM: NavHostController
    ) {
    Card(
        backgroundColor = BackgroundColor,
        modifier = Modifier
            .padding(bottom = 5.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                androidx.compose.material.Text(
                    text = product.nameProduct,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                androidx.compose.material.Text(
                    text = product.moneyProduct,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row {
                TextButton(
                    onClick = {
                        deleteProduct(productId = product.idProduct, imgProductUrl = product.imgProduct)
                        mainNavController.navigate("listProduct")
                    },
                    modifier = Modifier.padding(end = 15.dp)
                ) {
                    androidx.compose.material.Text(
                        text = "Xóa",
                        fontFamily = Poppins,
                        color = Purple500,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                TextButton(
                    onClick = {
                        val productJson = Gson().toJson(product)
                        val encodedProductJson =
                            URLEncoder.encode(productJson, StandardCharsets.UTF_8.toString())
                        mainNavControllerM.navigate("editeProduct/${encodedProductJson}")
                    },
                    modifier = Modifier.padding(end = 15.dp)
                ) {
                    androidx.compose.material.Text(
                        text = "Sửa",
                        fontFamily = Poppins,
                        color = Purple500,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterialApi::class)
//@Preview(showBackground = true)
//@Composable
//fun PreviewMenuDetail() {
//
//}