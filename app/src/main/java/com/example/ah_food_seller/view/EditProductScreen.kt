package com.example.ah_food_seller.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
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
import androidx.compose.ui.graphics.Color
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
import com.example.ah_food_seller.R
import com.example.ah_food_seller.controller.CategoryViewModel
import com.example.ah_food_seller.controller.addProduct
import com.example.ah_food_seller.model.Product
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor

@ExperimentalMaterialApi
@Composable
fun EditProductScreen(
    mainNavController: NavHostController
) {
    var nameProduct by remember { mutableStateOf("") }
    var contentProduct by remember { mutableStateOf("") }
    var moneyProduct by remember { mutableStateOf("") }
    var imgProduct by remember { mutableStateOf("") }
    var statusProduct:Boolean = true
    var id_Category = remember { mutableStateOf("") }

    var name_Category = remember { mutableStateOf("Chọn danh mục") }

    Column(
        modifier = Modifier
    ) {
        var keyboardOptionsNumber = KeyboardOptions(keyboardType = KeyboardType.Number)
        var keyboardOptions = KeyboardOptions()
        EditProductTop(
            mainText = stringResource(id = R.string.contact),
            onClick = {
                mainNavController.navigate("detailMenu")
            }
        )

        EditProductItem(
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

        EditProductItem(
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

        EditProductItem(
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
                addProduct(
                    nameProduct = nameProduct,
                    contentProduct = contentProduct,
                    moneyProduct = moneyProduct,
                    imgProduct = imgProduct,
                    statusProduct = statusProduct,
                    id_Category = id_Category.value
                )
                mainNavController.navigate("detailMenu")
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
fun EditProductTop(mainText: String, onClick: () -> Unit) {
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
fun EditProductItem(
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
fun SelectComponentEdit(
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

//@OptIn(ExperimentalMaterialApi::class)
//@Preview(showBackground = true)
//@Composable
//fun PreviewAddProduct() {
//    AddProductScreen()
//}