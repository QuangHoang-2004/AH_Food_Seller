package com.example.ah_food_seller.viewmodel

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ah_food_seller.R
import com.example.ah_food_seller.ui.theme.Poppins
import com.example.ah_food_seller.ui.theme.SecondaryColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Locale
//val calendar = Calendar.getInstance()
//val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("vi", "VN"))
//var selectedDate by remember { mutableStateOf(dateFormat.format(calendar.time)) }
//
//OrderHistoryTop(dateText = selectedDate) { newDate ->
//    selectedDate = newDate
//}

