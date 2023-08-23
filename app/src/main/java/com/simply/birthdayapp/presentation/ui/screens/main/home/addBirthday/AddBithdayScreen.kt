package com.simply.birthdayapp.presentation.ui.screens.main.home.addBirthday

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.models.RelationshipEnum
import com.simply.birthdayapp.presentation.ui.components.AppBaseTopBar
import com.simply.birthdayapp.presentation.ui.components.RoundAsyncImage
import com.simply.birthdayapp.presentation.ui.theme.Primary
import org.koin.androidx.compose.getViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBirthdayScreen(
    addBirthdayViewModel: AddBirthdayViewModel = getViewModel(),
    onDoneClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val name by addBirthdayViewModel.name.collectAsState()
    val selectedImageUri by addBirthdayViewModel.imageUri.collectAsState()
    val calendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
    val selectedDate by addBirthdayViewModel.dateTitle.collectAsState()
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val selectedRelationship by addBirthdayViewModel.relationship.collectAsState()
    val doneButtonEnable by addBirthdayViewModel.combine.collectAsState()
    val focusManager = LocalFocusManager.current
    val relationshipList = RelationshipEnum.values()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) addBirthdayViewModel.setImage(uri)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Primary)
            .padding(horizontal = 16.dp)
            .verticalScroll(state = rememberScrollState())
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppBaseTopBar(onBackClick = { onBackClick() })
        if (selectedImageUri == null) {
            Image(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .height(100.dp)
                    .width(100.dp)
                    .clip(CircleShape)
                    .clickable { launcher.launch("image/*") },
                painter = painterResource(id = R.drawable.placeholder_user_avatar),
                contentDescription = null,
            )
        } else {
            RoundAsyncImage(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .height(100.dp)
                    .width(100.dp)
                    .clip(CircleShape)
                    .clickable { launcher.launch("image/*") },
                data = selectedImageUri,
                borderColor = Color.Transparent,
                borderWidth = 0.dp,
                placeholder = painterResource(id = R.drawable.placeholder_user_avatar),
                error = painterResource(id = R.drawable.placeholder_user_avatar),
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 30.dp, start = 40.dp)
                .align(Alignment.Start),
            text = stringResource(id = R.string.name),
            fontFamily = FontFamily(Font(R.font.karma_bold)),
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 18.sp,
        )
        TextField(
            value = name,
            onValueChange = { if (it.length <= 25) addBirthdayViewModel.setName(it) },
            modifier = Modifier
                .padding(top = 3.dp)
                .padding(horizontal = 50.dp),
            shape = CircleShape,
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                cursorColor = Color.Gray,
            ),
            singleLine = true,
        )
        Text(
            modifier = Modifier
                .padding(top = 30.dp, start = 40.dp)
                .align(Alignment.Start),
            text = stringResource(id = R.string.relationship),
            fontFamily = FontFamily(Font(R.font.karma_bold)),
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 18.sp,
        )
        LazyVerticalGrid(
            modifier = Modifier.height(150.dp),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(top = 10.dp),
        ) {
            items(relationshipList) { relationship ->
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(4.dp)
                        .height(37.dp)
                        .clip(CircleShape)
                        .clickable(onClick = {
                            addBirthdayViewModel.setRelationship(if (selectedRelationship == relationship) null else relationship)
                            focusManager.clearFocus()
                        })
                        .border(
                            width = 2.dp,
                            color = if (selectedRelationship == relationship) MaterialTheme.colorScheme.tertiary else Color.Transparent,
                            shape = CircleShape,
                        ),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = relationship.value,
                            fontFamily = FontFamily(Font(R.font.karma_bold)),
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
        Text(
            modifier = Modifier
                .padding(top = 20.dp, start = 40.dp)
                .align(Alignment.Start)
                .clickable { showDatePicker = true },
            text = stringResource(id = R.string.date),
            fontFamily = FontFamily(Font(R.font.karma_bold)),
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 18.sp,
        )
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .width(200.dp)
                .clickable { showDatePicker = true },
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Text(
                text = selectedDate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
            )
        }
        Button(
            enabled = doneButtonEnable,
            onClick = {
                onDoneClick()
                addBirthdayViewModel.createBirthday(context)
            },
            modifier = Modifier.padding(top = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
        ) {
            Text(
                text = stringResource(id = R.string.done),
                fontFamily = FontFamily(Font(R.font.karma_bold)),
            )
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        addBirthdayViewModel.setDate(datePickerState.selectedDateMillis)
                    }) {
                        Text(text = "Confirm", color = MaterialTheme.colorScheme.tertiary)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDatePicker = false },
                    ) {
                        Text(text = "Cancel", color = MaterialTheme.colorScheme.tertiary)
                    }
                },
            ) {
                DatePicker(
                    dateFormatter = DatePickerFormatter(),
                    modifier = Modifier.padding(top = 16.dp),
                    state = datePickerState,
                    showModeToggle = false,
                    title = null,
                    colors = DatePickerDefaults.colors(
                        selectedDayContainerColor = MaterialTheme.colorScheme.tertiary,
                        selectedYearContainerColor = MaterialTheme.colorScheme.tertiary,
                        todayContentColor = MaterialTheme.colorScheme.tertiary,
                        currentYearContentColor = MaterialTheme.colorScheme.tertiary,
                        weekdayContentColor = MaterialTheme.colorScheme.tertiary,
                        headlineContentColor = MaterialTheme.colorScheme.tertiary,
                    ),
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddBirthdayScreenPreview() {
    AddBirthdayScreen(addBirthdayViewModel = getViewModel())
}