package com.example.ah_food_seller.view

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ah_food_seller.R
import com.example.ah_food_seller.controller.getUserName
import com.example.ah_food_seller.ui.theme.LightPrimaryColor
import com.example.ah_food_seller.ui.theme.LightTextColor
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.PrimaryColor
import com.example.ah_food_seller.ui.theme.Purple700
import com.example.ah_food_seller.ui.theme.SecondaryColor
import com.example.ah_food_seller.model.Order
import com.example.ah_food_seller.controller.listenForOrderUpdates
import com.example.ah_food_seller.viewmodel.OrderItem
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderScreenMain(
    user: FirebaseUser
){
//    val userProfile = remember { mutableStateOf<Restaurant?>(null) }
//    LaunchedEffect(user.uid) {
//        val firestore = FirebaseFirestore.getInstance()
//        val userDocRef = firestore.collection("users").document(user.uid)
//
//        userDocRef.get()
//            .addOnSuccessListener { document ->
//                if (document.exists()) {
//                    val nameRes = document.getString("nameRestaurant")
//                    val addressRes = document.getString("addressRestaurant")
//                    val email = document.getString("email")
//                    userProfile.value = Restaurant(nameRes, addressRes, user.email ?: "")
//                } else {
//                }
//            }
//            .addOnFailureListener { e ->
//            }
//    }
    val orderdetail = remember { mutableStateOf<Order?>(null) }
    val mainNavController = rememberNavController()

    NavHost(navController = mainNavController, startDestination = "main"){
        composable("main"){
            OrderScreen(mainNavController = mainNavController, orderdetail = orderdetail)
        }
        composable("detailOrder"){
            OrderDetailScreen(mainNavController = mainNavController, orderdetail = orderdetail.value)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun OrderScreen(
    orderdetail: MutableState<Order?>,
//    navController: NavHostController = rememberNavController(),
    mainNavController: NavHostController
) {
    var orders by remember { mutableStateOf<List<Order>>(emptyList()) }

    LaunchedEffect(Unit) {
        listenForOrderUpdates(
            onOrderUpdated = { updatedOrders ->
                orders = updatedOrders
            }
        )
    }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.background(PrimaryColor)
            ){
                androidx.compose.material.Text(
                    text = "Đơn hàng",
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
                pageCount = {2}
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
                        text = "Hiện tại",
                        modifier = Modifier.padding(10 .dp)
                    )
                }

                Tab(
                    selected = pageState.currentPage == 1,
                    onClick = {
                        coroutineScope.launch {
                            pageState.animateScrollToPage(1)
                        }
                    }
                ) {
                    Text(text = "Lịch sử")
                }
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
                        0 -> CurrentOrder(mainNavController = mainNavController, orders = orders, orderdetail = orderdetail)
                        1 -> OrderHistory()
                    }

                }

            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CurrentOrder(mainNavController: NavHostController, orders: List<Order>, orderdetail: MutableState<Order?>) {
    Column(
        modifier = Modifier
    ) {
        OrderListScreen(mainNavController = mainNavController, orders = orders, orderdetail = orderdetail)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OrderListScreen(mainNavController: NavHostController, orders: List<Order>, orderdetail: MutableState<Order?>) {
    LazyColumn {
        items(orders) { order ->
            if(order.statusOrder == "Chờ xác nhận" || order.statusOrder == "Đã xác nhận") {
                CurrentOrderItem(
                    order = order,
                    onClick = {
                        orderdetail.value = order
                        mainNavController.navigate("detailOrder")
                    }
                )
                Divider(
                    Modifier.padding(horizontal = 12.dp),
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CurrentOrderItem(order: Order, onClick: () -> Unit) {
    var userName by remember { mutableStateOf("Loading...") }

    LaunchedEffect(order.userId) {
        getUserName(
            userId = order.userId,
            onSuccess = { name ->
                userName = name
            },
            onFailure = { exception ->
                userName = "Error: ${exception.message}"
            }
        )
    }
    Card(
        onClick = { onClick() },
        backgroundColor = Color.White,
        modifier = Modifier
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
                        text = userName,
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Row (
                        modifier = Modifier.padding(top = 5.dp)
                    ){
                        androidx.compose.material.Text(
                            text = "${order.totalQuantity} món ",
                            fontFamily = Poppins,
                            color = SecondaryColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        androidx.compose.material.Text(
                            text = "cho $userName",
                            fontFamily = Poppins,
                            color = SecondaryColor,
                            fontSize = 13.sp,
                        )
                    }
//                    Row {
//                        androidx.compose.material.Text(
//                            text = "Tài xế sẽ đến sau ",
//                            fontFamily = Poppins,
//                            color = SecondaryColor,
//                            fontSize = 13.sp,
//                        )
//                        androidx.compose.material.Text(
//                            text = "$dateText",
//                            fontFamily = Poppins,
//                            color = SecondaryColor,
//                            fontSize = 13.sp,
//                            fontWeight = FontWeight.Bold,
//                        )
//                    }
                }
            }
            androidx.compose.material.Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )

        }
    }
}

@ExperimentalMaterialApi
@Composable
fun OrderHistory() {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("vi", "VN"))
    var selectedDate by remember { mutableStateOf(dateFormat.format(calendar.time)) }

    Box(Modifier.verticalScroll(rememberScrollState())) {
        Column {
            OrderHistoryTop(dateText = selectedDate) { newDate ->
                selectedDate = newDate
            }
            OrderHistoryCenter()
            OrderHistoryBottom()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun OrderHistoryTop(
    dateText: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("vi", "VN"))
            onDateSelected(dateFormat.format(calendar.time))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Card(
        onClick = { datePickerDialog.show() },
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        elevation = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                                text = dateText,
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
fun OrderHistoryCenter() {
    Column(
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        OrderHistoryCenterItem(
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
fun OrderHistoryCenterItem(mainText: String, value: String) {
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
fun OrderHistoryBottom() {
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
        OrderHistoryBottomItem(
            mainText = stringResource(id = R.string.contact),
            dateText = "11:30 PM",
            onClick = {}
        )
        Divider(
            Modifier.padding(horizontal = 12.dp),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        )
        OrderHistoryBottomItem(
            mainText = stringResource(id = R.string.contact),
            dateText = "10:30 PM",
            onClick = {}
        )
        Divider(
            Modifier.padding(horizontal = 12.dp),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun OrderHistoryBottomItem(mainText: String, dateText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        backgroundColor = Color.White,
        modifier = Modifier
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