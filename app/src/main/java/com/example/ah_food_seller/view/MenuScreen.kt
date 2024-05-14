package com.example.ah_food_seller.view

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ah_food_seller.R
import com.example.ah_food_seller.controller.CategoryViewModel
import com.example.ah_food_seller.controller.ProductViewModel
import com.example.ah_food_seller.controller.getProductCountForCategory
import com.example.ah_food_seller.controller.updateProductStatus
import com.example.ah_food_seller.ui.theme.BackgroundColor
import com.example.ah_food_seller.ui.theme.LightPrimaryColor
import com.example.ah_food_seller.ui.theme.LightTextColor
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.Purple500
import com.example.ah_food_seller.ui.theme.Purple700
import com.example.ah_food_seller.ui.theme.SecondaryColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuScreenMain(){
    val mainNavController = rememberNavController()

    NavHost(navController = mainNavController, startDestination = "main"){
        composable("main"){
            MenuScreen(mainNavController = mainNavController)
        }
        composable("detailMenu"){
            MenuDetailScreen(mainNavController = mainNavController)
        }
        composable("addCategory"){
            AddCategoryScreen(mainNavController = mainNavController)
        }
        composable("addProduct"){
            AddProductScreen(mainNavController = mainNavController)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MenuScreen(
   // viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    mainNavController: NavHostController
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.background(PrimaryColor)
            ){
                androidx.compose.material.Text(
                    text = "Thực đơn",
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
    ) { padingValue ->
        Column(
            modifier = Modifier
                .padding(padingValue)
        ) {
            val pageState = rememberPagerState (
                pageCount = {1}
            )
            val coroutineScope = rememberCoroutineScope()
            TabRow(
                selectedTabIndex = pageState.currentPage,
                containerColor = PrimaryColor,
                contentColor = LightPrimaryColor,
                divider = {},
                indicator = {tabPositions->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pageState.currentPage]),
                        height = 3.dp,
                        color = Purple700
                    )
                }
            ) {
                Tab(
                    selected = pageState.currentPage == 0,
                    onClick = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(0)
                        }
                    }
                ) {
                    Text(
                        text = "Các món chính",
                        modifier = Modifier.padding(10 .dp)
                    )
                }

//                Tab(
//                    selected = pageState.currentPage == 1,
//                    onClick = {
//                        coroutineScope.launch {
//                            pageState.animateScrollToPage(1)
//                        }
//                    }
//                ) {
//                    Text(text = "Tỳ chọn nhóm")
//                }
            }

            HorizontalPager(state = pageState,
                userScrollEnabled = false
                ) {page->

                Column(
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxSize()
                ) {
                    when(page){
                        0 -> MainMenu(mainNavController = mainNavController)
//                        1 -> MenuHistory()
                    }

                }

            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainMenu(mainNavController: NavHostController) {
    Column(
        modifier = Modifier
    ) {

        MainMenuTop(
            mainText = stringResource(id = R.string.contact),
            onClick = {
                mainNavController.navigate("detailMenu")
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

            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(end = 15.dp)
            ) {
                androidx.compose.material.Text(
                    text = "Chọn",
                    fontFamily = Poppins,
                    color = Purple500,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
//        LazyColumn(){
//            items( 5){
//                index ->
//                MainMenuItem(
//                    mainText = stringResource(id = R.string.contact),
//                    statusText = 5,
//                    nameText = "Cà Phê $index",
//                    onClick = {}
//                )
//                MainMenuItem(
//                    mainText = stringResource(id = R.string.contact),
//                    statusText = 8,
//                    nameText = "Nước ép",
//                    onClick = {}
//                )
//            }
//        }
        CategoryListScreen()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CategoryListScreen() {
    val viewModel: CategoryViewModel = viewModel()
    val categories = viewModel.categories.collectAsState().value

    LazyColumn(modifier = Modifier) {
        items(categories) { category ->
//            val productCount = getProductCountForCategory(category.idCategory)
            MainMenuItem(
                countText = 5, // Adjust as per your logic
                nameText = category.nameCategory,
                id_Category = category.idCategory
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainMenuTop(mainText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth()
        ,
        elevation = 0.dp,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 18.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Box(
                modifier = Modifier.padding(end = 20.dp),
                contentAlignment = Alignment.TopStart
            ){
                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.servicebell_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.TopStart)
                )
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 3.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(start = 5.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        androidx.compose.material.Text(
                            text = "Thiết lập thực đơn",
                            fontFamily = Poppins,
                            color = SecondaryColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        androidx.compose.material.Text(
                            text = "Thêm hoặc chỉnh thực đơn",
                            fontFamily = Poppins,
                            color = LightTextColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        )

                    }
                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.ic_right_arrow),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 5.dp, end = 5.dp)
                        .background(color = LightTextColor)
                )
                androidx.compose.material.Text(
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp, end = 15.dp),
                    text = "Bằng cách nhấn chọn Thiết lập thực đơn, bạn đọc, hiểu và đồng ý với điều khoản sử dụng",
                    fontFamily = Poppins,
                    color = LightTextColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun MainMenuItem(countText: Int, nameText: String, id_Category: String) {
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
                    text = nameText,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
//                androidx.compose.material.Text(
//                    text = "$countText món",
//                    fontFamily = Poppins,
//                    color = SecondaryColor,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold,
//                )

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
                    ProductListScreen(id_Category = id_Category)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProductListScreen(id_Category: String) {
    val viewModel: ProductViewModel = viewModel()
    val products by viewModel.products.collectAsState()

    Column(modifier = Modifier.padding(horizontal = 15.dp)) {
        products.forEach() { product ->
            if (product.id_Category == id_Category) {
                val checkedState = remember { mutableStateOf(product.statusProduct) }
                updateProductStatus(productId = product.idProduct, checkedState.value)
                MainMenuItemProduct(
                    checkedState = checkedState,
                    nameProduct = product.nameProduct,
                    idCategory = product.id_Category
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun MainMenuItemProduct(checkedState: MutableState<Boolean>, nameProduct: String, idCategory: String) {
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
            androidx.compose.material.Text(
                text = nameProduct,
                fontFamily = Poppins,
                color = SecondaryColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Switch(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color.Green.copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.LightGray.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .padding(5.dp)
                    .padding(end = 20.dp)
                    .size(10.dp) // Adjust the size as needed
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MenuHistory() {
    Box(Modifier.verticalScroll(rememberScrollState())) {
        Column {
            MenuHistoryTop(
                icon = R.drawable.ic_launcher_background,
                mainText = stringResource(id = R.string.contact),
                dateText = "7 tháng 2, 2024",
                onClick = {}
            )
            MenuHistoryCenter()
            MenuHistoryBottom()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MenuHistoryTop(icon: Int, mainText: String, dateText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Column (
            modifier = Modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            androidx.compose.material.Text(
                text = "Chọn ngày",
                fontFamily = Poppins,
                color = SecondaryColor,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
            Row(
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            androidx.compose.material.Text(
                                text = "Hôm nay, ",
                                fontFamily = Poppins,
                                color = SecondaryColor,
                                fontSize = 14.sp,
                            )
                            androidx.compose.material.Text(
                                text = "$dateText",
                                fontFamily = Poppins,
                                color = SecondaryColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .size(16.dp)
                )

            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MenuHistoryCenter() {
    Column(
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        MenuHistoryCenterItem(
            mainText = stringResource(id = R.string.contact),
            value = "677.000",
        )
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Card(
                backgroundColor = Color.White,
                modifier = Modifier
                    .padding(top = 8.dp),
                elevation = 0.dp,
            ) {
                Column (
                    modifier = Modifier
                        .width(195.dp)
                        .padding(horizontal = 10.dp, vertical = 15.dp),
                    horizontalAlignment = Alignment.Start

                ){
                    androidx.compose.material.Text(
                        text = "3",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                    androidx.compose.material.Text(
                        text = "Đơn hàng hoàn tất",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                }
            }

            Card(
                backgroundColor = Color.White,
                modifier = Modifier
                    .width(195.dp)
                    .padding(top = 8.dp),
                elevation = 0.dp,
            ) {
                Column (
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 15.dp),
                    horizontalAlignment = Alignment.Start

                ){
                    androidx.compose.material.Text(
                        text = "0",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                    androidx.compose.material.Text(
                        text = "Đơn đã hủy",
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MenuHistoryCenterItem(mainText: String, value: String) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Column (
            modifier = Modifier
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ){
            androidx.compose.material.Text(
                text = "$value VNĐ",
                fontFamily = Poppins,
                color = Color(40, 166, 54),
                style = TextStyle(textAlign = TextAlign.Center),
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
            androidx.compose.material.Text(
                text = "Tổng doanh thu",
                fontFamily = Poppins,
                color = SecondaryColor,
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 2.dp)
            )
            androidx.compose.material.Text(
                text = "Đây là doanh thu trước khi được điều chỉnh và khấu trừ.",
                fontFamily = Poppins,
                color = LightTextColor,
                style = TextStyle(textAlign = TextAlign.Center),
                fontSize = 13.sp,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MenuHistoryBottom() {
    Column(
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        androidx.compose.material.Text(
            text = "Đơn hàng",
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
        )
        MenuHistoryBottomItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.contact),
            dateText = "11:30 PM",
            onClick = {}
        )
        MenuHistoryBottomItem(
            icon = R.drawable.ic_launcher_background,
            mainText = stringResource(id = R.string.contact),
            dateText = "10:30 PM",
            onClick = {}
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun MenuHistoryBottomItem(icon: Int, mainText: String, dateText: String, onClick: () -> Unit) {
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
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    androidx.compose.material.Text(
                        text = mainText,
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Row {
                        androidx.compose.material.Text(
                            text = "Đã hoàn tất ",
                            fontFamily = Poppins,
                            color = SecondaryColor,
                            fontSize = 13.sp,
                        )
                        androidx.compose.material.Text(
                            text = "$dateText",
                            fontFamily = Poppins,
                            color = SecondaryColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
            Row {
                androidx.compose.material.Text(
                    text = "160.000",
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.ic_right_arrow),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .padding(top = 3.dp)
                        .size(16.dp)
                )
            }

        }
    }
}

//@Composable
//@Preview("a", showBackground = true)
//fun a(){
//    Column (
//        modifier = Modifier.fillMaxSize()
//    ) {
//    }
//}