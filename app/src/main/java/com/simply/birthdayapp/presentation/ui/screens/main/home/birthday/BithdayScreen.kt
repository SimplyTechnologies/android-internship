package com.simply.birthdayapp.presentation.ui.screens.main.home.birthday

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.CalendarContract
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.extensions.fromMillisToUtcDate
import com.simply.birthdayapp.presentation.extensions.fromUtcToMillisDate
import com.simply.birthdayapp.presentation.extensions.uriToByteArray
import com.simply.birthdayapp.presentation.models.RelationshipEnum
import com.simply.birthdayapp.presentation.ui.components.AppBaseTopBar
import com.simply.birthdayapp.presentation.ui.components.CalendarPermissionDialog
import com.simply.birthdayapp.presentation.ui.components.DatePickerComponent
import com.simply.birthdayapp.presentation.ui.components.RoundAsyncImage
import com.simply.birthdayapp.presentation.ui.screens.main.LocalSnackbarHostState
import com.simply.birthdayapp.presentation.ui.screens.main.home.HomeViewModel
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import java.util.Calendar
import java.util.TimeZone
import org.koin.androidx.compose.getViewModel

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayScreen(
    birthdayViewModel: BirthdayViewModel,
    homeViewModel: HomeViewModel,
    navigateToHomeScreen: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val selectedImageUri by birthdayViewModel.imageByteArray.collectAsState()
    val name by birthdayViewModel.name.collectAsState()
    val selectedRelationship by birthdayViewModel.relationship.collectAsState()
    val dateDayMonthYear by birthdayViewModel.dateDayMonthYear.collectAsState()
    val dateUtc by birthdayViewModel.dateUtc.collectAsState()
    val editModeBirthday by birthdayViewModel.editModeBirthday.collectAsState()
    val addToCalendarCheck by birthdayViewModel.addToCalendarCheck.collectAsState()

    val createBirthdayError by birthdayViewModel.createBirthdayError.collectAsState()
    val updateBirthdayError by birthdayViewModel.updateBirthdayError.collectAsState()
    val deleteBirthdayError by birthdayViewModel.deleteBirthdayError.collectAsState()

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current
    val calendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
    var showDatePickerDialog by rememberSaveable { mutableStateOf(false) }
    var showCalendarPermissionExplanationDialog by rememberSaveable { mutableStateOf(false) }
    val doneButtonEnable by birthdayViewModel.combine.collectAsState()
    val relationshipList = RelationshipEnum.values()
    var showNeedCalendarPermissionMessage by rememberSaveable { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) birthdayViewModel.setImage(uri.uriToByteArray(context))
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted.not()) {
            if (context.shouldShowCalendarPermissionRationale().not()) showNeedCalendarPermissionMessage = true
            birthdayViewModel.setAddToCalendarCheck(false)
        }
    }

    LaunchedEffect(createBirthdayError) {
        if (createBirthdayError) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.fail_to_create_birthday),
                duration = SnackbarDuration.Short,
            )
            birthdayViewModel.setCreateBirthdayErrorFalse()
        }
    }
    LaunchedEffect(updateBirthdayError) {
        if (updateBirthdayError) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.fail_to_update_birthday),
                duration = SnackbarDuration.Short,
            )
            birthdayViewModel.setUpdateBirthdayErrorFalse()
        }
    }
    LaunchedEffect(deleteBirthdayError) {
        if (deleteBirthdayError) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.fail_to_delete_birthday),
                duration = SnackbarDuration.Short,
            )
            birthdayViewModel.setDeleteBirthdayErrorFalse()
        }
    }
    LaunchedEffect(addToCalendarCheck) {
        if (addToCalendarCheck) {
            checkCalendarPermission(
                context = context,
                requestPermissionLauncher = requestPermissionLauncher,
                onShowRationale = { showCalendarPermissionExplanationDialog = true },
            )
        }
    }
    LaunchedEffect(showNeedCalendarPermissionMessage) {
        if (showNeedCalendarPermissionMessage) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.app_needs_calendar_permission_to_add_birthdays),
                duration = SnackbarDuration.Short,
            )
            showNeedCalendarPermissionMessage = false
        }
    }
    Column {
        AppBaseTopBar(onBackClick = onBackClick)
        BackHandler { onBackClick() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.backgroundPink)
                .padding(horizontal = 16.dp)
                .verticalScroll(state = rememberScrollState())
                .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (editModeBirthday != null) {
                IconButton(
                    modifier = Modifier
                        .align(alignment = Alignment.End)
                        .padding(top = 16.dp, end = 8.dp),
                    onClick = {
                        editModeBirthday?.id?.let {
                            birthdayViewModel.deleteBirthday(
                                id = it,
                                navigateToHomeScreen = { navigateToHomeScreen() },
                                onCompletion = { homeViewModel.fetchBirthdays() },
                            )
                        }
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_trash),
                        contentDescription = "Trash icon",
                    )
                }
            }
            RoundAsyncImage(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(100.dp)
                    .width(100.dp)
                    .clip(AppTheme.shapes.circle)
                    .clickable { launcher.launch("image/*") },
                data = selectedImageUri,
                placeholder = painterResource(id = R.drawable.placeholder_user_avatar),
                error = painterResource(id = R.drawable.placeholder_user_avatar),
            )
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
                    .clickable { showDatePickerDialog = true },
                text = stringResource(id = R.string.date) + "*",
                style = AppTheme.typography.boldKarmaDarkPink,
                fontSize = 18.sp,
            )
            Card(
                shape = AppTheme.shapes.circle,
                modifier = Modifier
                    .width(200.dp)
                    .clip(AppTheme.shapes.circle)
                    .clickable { showDatePickerDialog = true },
                colors = CardDefaults.cardColors(containerColor = AppTheme.colors.white),
            ) {
                Text(
                    text = dateDayMonthYear,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = addToCalendarCheck,
                    onCheckedChange = {
                        birthdayViewModel.setAddToCalendarCheck(it)
                    },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = AppTheme.colors.lightPink,
                        checkedColor = AppTheme.colors.lightPink,
                    ),
                )
                Text(
                    modifier = Modifier
                        .fillMaxHeight(),
                    text = stringResource(id = R.string.addToCalendar),
                    style = AppTheme.typography.boldKarmaDarkPink,
                    fontSize = 16.sp
                )
            }
            Button(
                enabled = doneButtonEnable,
                onClick = {
                    if (addToCalendarCheck && context.calendarPermissionGranted()) {
                        addEventToCalendar(context, datePickerState.selectedDateMillis ?: calendar.timeInMillis)
                    }
                    editModeBirthday?.let { birthday ->
                        birthday.id.let {
                            birthdayViewModel.updateBirthday(it, navigateToHomeScreen) {
                                homeViewModel.fetchBirthdays()
                            }
                        }
                    } ?: run {
                        birthdayViewModel.createBirthday(navigateToHomeScreen) {
                            homeViewModel.fetchBirthdays()
                        }
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
                shape = AppTheme.shapes.smallRoundedCorners,
                colors = ButtonDefaults.buttonColors(AppTheme.colors.darkPink),
            ) {
                Text(
                    text = stringResource(id = R.string.done),
                    style = AppTheme.typography.bold,
                )
            }

            if (showDatePickerDialog) {
                datePickerState.setSelection(dateUtc.fromUtcToMillisDate())
                DatePickerComponent(
                    datePickerState = datePickerState,
                    onDismissRequest = { showDatePickerDialog = false },
                    onConfirmButtonClick = {
                        showDatePickerDialog = false
                        birthdayViewModel.setDate(
                            (datePickerState.selectedDateMillis ?: calendar.timeInMillis).fromMillisToUtcDate()
                        )
                    },
                    onDismissButtonClick = { showDatePickerDialog = false },
                )
            }

            if (showCalendarPermissionExplanationDialog) {
                CalendarPermissionDialog(
                    onDismissRequest = {
                        birthdayViewModel.setAddToCalendarCheck(false)
                        showCalendarPermissionExplanationDialog = false
                    },
                    onConfirmButtonClick = {
                        showCalendarPermissionExplanationDialog = false
                        requestPermissionLauncher.requestCalendarPermission()
                    },
                    onDismissButtonClick = {
                        birthdayViewModel.setAddToCalendarCheck(false)
                        showCalendarPermissionExplanationDialog = false
                    },
                )
            }
        }
    }
}

private fun checkCalendarPermission(
    context: Context,
    requestPermissionLauncher: ActivityResultLauncher<String>,
    onShowRationale: () -> Unit,
) {
    if (context.calendarPermissionGranted().not()) {
        if (context.shouldShowCalendarPermissionRationale()) onShowRationale()
        else requestPermissionLauncher.requestCalendarPermission()
    }
}

private fun addEventToCalendar(context: Context, date: Long) {
    val values = ContentValues().apply {
        put(CalendarContract.Events.DTSTART, date)
        put(CalendarContract.Events.DTEND, date)
        put(CalendarContract.Events.TITLE, "Your Event Title")
        put(CalendarContract.Events.CALENDAR_ID, 1)
        put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
    }
    context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
}

private fun Context.calendarPermissionGranted(): Boolean =
    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) ==
            PackageManager.PERMISSION_GRANTED

private fun Context.shouldShowCalendarPermissionRationale(): Boolean =
    ActivityCompat.shouldShowRequestPermissionRationale(this as Activity, Manifest.permission.WRITE_CALENDAR)

private fun ActivityResultLauncher<String>.requestCalendarPermission() = launch(Manifest.permission.WRITE_CALENDAR)

@Composable
@Preview(showBackground = true)
private fun BirthdayScreenPreview() {
    BirthdayScreen(
        birthdayViewModel = getViewModel(),
        homeViewModel = getViewModel(),
    )
}