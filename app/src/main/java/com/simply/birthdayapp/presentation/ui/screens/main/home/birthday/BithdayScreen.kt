package com.simply.birthdayapp.presentation.ui.screens.main.home.birthday

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.models.RelationshipEnum
import com.simply.birthdayapp.presentation.ui.components.AppBaseTopBar
import com.simply.birthdayapp.presentation.ui.components.DatePickerComponent
import com.simply.birthdayapp.presentation.ui.components.RoundAsyncImage
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayScreen(
    birthdayViewModel: BirthdayViewModel = getViewModel(),
    navigateToHomeScreen: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val name by birthdayViewModel.name.collectAsState()
    val selectedImageUri by birthdayViewModel.imageUri.collectAsState()
    val calendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
    val selectedDate by birthdayViewModel.dateTitle.collectAsState()
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val selectedRelationship by birthdayViewModel.relationship.collectAsState()
    val doneButtonEnable by birthdayViewModel.combine.collectAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val relationshipList = RelationshipEnum.values()
    val createBirthdayError by birthdayViewModel.createBirthdayError.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) birthdayViewModel.setImage(uri)
    }

    LaunchedEffect(createBirthdayError) {
        if (createBirthdayError) {
            Toast.makeText(context, R.string.fail_to_create_birthday, Toast.LENGTH_SHORT).show()
            birthdayViewModel.setCreateBirthdayErrorFalse()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.backgroundPink)
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
                    .clip(AppTheme.shapes.circle)
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
                    .clip(AppTheme.shapes.circle)
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
            text = stringResource(id = R.string.name) + "*",
            style = AppTheme.typography.boldKarmaDarkPink,
            fontSize = 18.sp,
        )
        TextField(
            value = name,
            onValueChange = { if (it.length <= 25) birthdayViewModel.setName(it) },
            modifier = Modifier
                .padding(top = 3.dp)
                .padding(horizontal = 50.dp),
            shape = AppTheme.shapes.circle,
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = AppTheme.colors.white,
                focusedContainerColor = AppTheme.colors.white,
                cursorColor = AppTheme.colors.gray,
            ),
        )
        Text(
            modifier = Modifier
                .padding(top = 30.dp, start = 40.dp)
                .align(Alignment.Start),
            text = stringResource(id = R.string.relationship) + "*",
            style = AppTheme.typography.boldKarmaDarkPink,
            fontSize = 18.sp,
        )
        LazyVerticalGrid(
            modifier = Modifier.height(150.dp),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(top = 10.dp),
        ) {
            items(relationshipList) { relationship ->
                Card(
                    shape = AppTheme.shapes.circle,
                    modifier = Modifier
                        .padding(4.dp)
                        .height(37.dp)
                        .clip(AppTheme.shapes.circle)
                        .clickable(onClick = {
                            birthdayViewModel.setRelationship(if (selectedRelationship == relationship) null else relationship)
                            focusManager.clearFocus()
                        })
                        .border(
                            width = 2.dp,
                            color = if (selectedRelationship == relationship) AppTheme.colors.darkPink else Color.Transparent,
                            shape = AppTheme.shapes.circle,
                        ),
                    colors = CardDefaults.cardColors(containerColor = AppTheme.colors.white),
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(id = relationship.resId),
                            style = AppTheme.typography.boldKarmaBlack,
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
            text = stringResource(id = R.string.date) + "*",
            style = AppTheme.typography.boldKarmaDarkPink,
            fontSize = 18.sp,
        )
        Card(
            shape = AppTheme.shapes.circle,
            modifier = Modifier
                .width(200.dp)
                .clip(AppTheme.shapes.circle)
                .clickable { showDatePicker = true },
            colors = CardDefaults.cardColors(containerColor = AppTheme.colors.white),
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
            onClick = { birthdayViewModel.createBirthday(navigateToHomeScreen) },
            modifier = Modifier.padding(top = 16.dp),
            shape = AppTheme.shapes.smallRoundedCorners,
            colors = ButtonDefaults.buttonColors(AppTheme.colors.darkPink),
        ) {
            Text(
                text = stringResource(id = R.string.done),
                style = AppTheme.typography.bold,
            )
        }

        if (showDatePicker) {
            DatePickerComponent(datePickerState = datePickerState, onDismissRequest = { showDatePicker = false }, onConfirmButtonClick = {
                showDatePicker = false
                birthdayViewModel.setDate(datePickerState.selectedDateMillis)
            }, onDismissButtonClick = {
                showDatePicker = false
            })
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun BirthdayScreenPreview() {
    BirthdayScreen(birthdayViewModel = getViewModel())
}