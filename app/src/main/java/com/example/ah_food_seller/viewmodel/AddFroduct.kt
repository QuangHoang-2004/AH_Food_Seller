package com.example.ah_food_seller.viewmodel

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ah_food_seller.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

@Composable
fun AddProductScreen() {
    Box {
        Image(
            painter = painterResource(id = R.drawable.bg_main_login),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
            )


        val isUploading = remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        val img: Bitmap = BitmapFactory.decodeResource(Resources.getSystem(), android.R.drawable.ic_menu_report_image)
        val bitmap = remember {
            mutableStateOf(img)
        }

        val laucher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview(),
        ) {
          if ( it != null){
              bitmap.value = it
          }
        }

        val launchImage = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ){
            if(Build.VERSION.SDK_INT < 28){
                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            }else{
                val source = it?.let {it1 ->
                    ImageDecoder.createSource(context.contentResolver, it1)
                }
                bitmap.value = source?.let { it1 ->
                    ImageDecoder.decodeBitmap(it1)
                }!!
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
        ) {
            Image(
//                painter = painterResource(id = R.drawable.ic_home),
                bitmap = bitmap.value.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(250.dp)
                    .background(color = Color.Blue)
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
            )
        }
        var showDialog by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .padding(top = 280.dp, start = 260.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = Color.White)
                    .size(50.dp)
                    .padding(10.dp)
                    .clickable { showDialog = true }
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
        ) {
            Button(onClick = {
                isUploading.value = true
                bitmap.value.let { bitmap ->
                    uploadImageToFirebase(bitmap, context as ComponentActivity){success ->
                        isUploading.value = false
                        if(success){
                            Toast.makeText(context, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "Failded to Upload", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text(
                    text = "Upload Image",
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
        ) {
            if (showDialog){
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(300.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Blue)
                ){
                    Column(
                        modifier = Modifier
                            .padding(start = 60.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_home), 
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    laucher.launch()
                                    showDialog = false
                                }
                        )
                        Text(text = "Camera", color = Color.White)
                    }
                    Spacer(modifier = Modifier.padding(30.dp))
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.ic_home),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    launchImage.launch("image/*")
                                    showDialog = false
                                }
                        )
                        Text(text = "Gallery", color = Color.White)
                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 50.dp, bottom = 80.dp)
                    ) {
                        Text(
                            text = "x",
                            color = Color.White,
                            modifier = Modifier
                                .clickable { showDialog = false }
                            )
                    }
                }
            }

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(450.dp)
                .fillMaxWidth()
        ) {
            if(isUploading.value){
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp),
                    color = Color.White
                )
            }

        }
    }
}

fun uploadImageToFirebase(bitmap: Bitmap, context: ComponentActivity, callback: (Boolean) -> Unit) {
    val storageRef = Firebase.storage.reference
    val imageRef = storageRef.child("images/${bitmap}")

    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val imageData = baos.toByteArray()

    imageRef.putBytes(imageData!!).addOnSuccessListener {
        callback(true)
    }.addOnFailureListener{
        callback(false)
    }

}















