package com.example.ah_food_seller.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ah_food_seller.R
import com.example.ah_food_seller.model.Product
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.SecondaryColor

@ExperimentalMaterialApi
@Composable
fun AddProductScreen(
    mainNavController: NavHostController
) {
    var nameProduct by remember { mutableStateOf("") }
    var contentCategory by remember { mutableStateOf("") }
    var moneyCategory by remember { mutableStateOf("") }
    var items = listOf("Item 1", "Item 2", "Item 3")
    var id_Category = remember { mutableStateOf("Chọn danh mục") }
    var product by remember { mutableStateOf(
        Product(
            idProduct = 0,
            nameProduct = "oke",
            contentProduct = "",
            moneyProduct = "",
            imgProduct = "",
            statusProduct = false,
            id_Category = "",
            id_Restaurant = ""
        )
    ) }
//    var product: Product
    Column(
        modifier = Modifier
    ) {

        AddProductTop(
            mainText = stringResource(id = R.string.contact),
            onClick = {
                mainNavController.navigate("detailMenu")
            }
        )

        AddProductItem(
            mainText = stringResource(id = R.string.contact),
            nameText = "Tên món",
            value = nameProduct,
            onValueChange = { nameProduct = it },
            lable = "Nhập tên món.",
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )

        AddProductItem(
            mainText = stringResource(id = R.string.contact),
            nameText = "Miêu tả",
            value = contentCategory,
            onValueChange = { contentCategory = it },
            lable = "Nhập miêu tả.",
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )

        AddProductItem(
            mainText = stringResource(id = R.string.contact),
            nameText = "Giá",
            value = moneyCategory,
            onValueChange = { moneyCategory = it },
            lable = "Nhập giá tiền.",
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )
        SelectComponent(
            modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
            items = items,
            selectedItem = id_Category,
            onItemSelected = { newItem -> id_Category.value = newItem }
        )

        Button(
            onClick = {
                product = Product(
                    idProduct = 1,
                    nameProduct = nameProduct,
                    contentProduct = contentCategory,
                    moneyProduct = moneyCategory,
                    imgProduct = "image_url",
                    statusProduct = true,
                    id_Category = id_Category.value,
                    id_Restaurant = "restaurant_456"
                )
                      },
            modifier = Modifier
                .padding(top = 20.dp, start = 50.dp, end = 50.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Xác Nhận ${id_Category.value} ${product.id_Category}", color = Color.White)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun AddProductTop(mainText: String, onClick: () -> Unit) {
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
fun AddProductItem(
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
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectComponent(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedItem: MutableState<String>,
    onItemSelected: (String) -> Unit
) {
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
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text(text = item) },
                        onClick = {
                            onItemSelected(item)
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