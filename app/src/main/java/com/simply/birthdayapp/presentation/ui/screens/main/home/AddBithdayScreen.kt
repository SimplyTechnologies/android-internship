package com.simply.birthdayapp.presentation.ui.screens.main.home

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.BackgroundColor
import com.simply.birthdayapp.presentation.ui.theme.Primary2
import java.util.Calendar

@Composable
fun AddBirthdayScreen() {
    var name by rememberSaveable { mutableStateOf("") }
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var bitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var selectedDateText by rememberSaveable { mutableStateOf("") }
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMonth
    )

    val relationshipList = listOf(
        "Best friend", "Mother", "Father",
        "Grandmother", "Grandfather",
        "Brother", "Sister", "Uncle"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.End)
        )

        if (selectedImageUri == null) {
            Image(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .size(90.dp)
                    .clickable { launcher.launch("image/*") },
                painter = painterResource(id = R.drawable.placeholder_user_avatar),
                contentDescription = null
            )
        } else {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, selectedImageUri)
            } else {
                selectedImageUri?.let {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, it)
                    bitmap = ImageDecoder.decodeBitmap(source)
                }

                bitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .clickable { launcher.launch("image/*") },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(top = 30.dp, start = 40.dp)
                .align(Alignment.Start),
            text = stringResource(id = R.string.name),
            color = Primary2,
            fontSize = 20.sp
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .padding(top = 3.dp),
            shape = CircleShape,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                cursorColor = Color.Gray
            ),
            singleLine = true,
        )

        Text(
            modifier = Modifier
                .padding(top = 30.dp, start = 40.dp)
                .align(Alignment.Start),
            text = stringResource(id = R.string.relationship),
            color = Primary2,
            fontSize = 20.sp
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(top = 10.dp),
            content = {
                items(relationshipList.size) { index ->
                    Card(
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .padding(4.dp)
                            .height(30.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = relationshipList[index]
                            )
                        }
                    }
                }
            })
    }
}

@Composable
@Preview(showBackground = true)
fun AddBirthdayScreenPreview() {
    AddBirthdayScreen()
}