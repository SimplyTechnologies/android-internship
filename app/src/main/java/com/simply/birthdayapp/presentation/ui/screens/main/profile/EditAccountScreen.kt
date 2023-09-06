package com.simply.birthdayapp.presentation.ui.screens.main.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.components.AppBaseTopBar
import com.simply.birthdayapp.presentation.ui.components.AuthButton
import com.simply.birthdayapp.presentation.ui.components.BaseTextField
import com.simply.birthdayapp.presentation.ui.components.CircularProgress
import com.simply.birthdayapp.presentation.ui.components.RoundAsyncImage
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel


@Composable
fun EditAccountScreen(
    profileViewModel: ProfileViewModel,
    onBackClick: () -> Unit = {},
    daneClick: () -> Unit = {},
) {
    val name by profileViewModel.name.collectAsState()
    val surName by profileViewModel.surName.collectAsState()
    val selectedImageUri by profileViewModel.imageUri.collectAsState()
    val doneButtonEnabled by profileViewModel.enableEditAccountDoneButton.collectAsState()
    val updateProfileSuccess by profileViewModel.updateProfileSuccess.collectAsState()
    val updateProfileErrorMessage by profileViewModel.updateProfileErrorMessage.collectAsState()
    val updateProfileErrorState by profileViewModel.updateProfileErrorState.collectAsState()
    val showLoading by profileViewModel.isOnLoadingState.collectAsState()
    val userProfile by profileViewModel.profile.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val onBackHandler = {
        focusManager.clearFocus()
        onBackClick()
        profileViewModel.resetFormEdit()
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) profileViewModel.setImage(image = uri)
    }

    LaunchedEffect(updateProfileSuccess) {
        if (updateProfileSuccess) {
            Toast.makeText(context, R.string.edit_account_success, Toast.LENGTH_SHORT).show()
            daneClick()
            profileViewModel.updateProfileSuccessState()
        }
    }
    LaunchedEffect(updateProfileErrorMessage) {
        if (updateProfileErrorMessage.isNotEmpty()) {
            Toast.makeText(context, R.string.edit_account_failed, Toast.LENGTH_SHORT).show()
            profileViewModel.updateProfileErrorMessage()
        }
    }
    if (updateProfileErrorState) {
        AlertDialog(
            containerColor = AppTheme.colors.white,
            titleContentColor = AppTheme.colors.black,
            textContentColor = AppTheme.colors.black,
            onDismissRequest = { profileViewModel.editAccountErrorStare() },
            title = { Text(text = stringResource(id = R.string.edit_account_error)) },
            text = { Text(text = stringResource(R.string.edit_account_network_error)) },
            confirmButton = {
                TextButton(onClick = { profileViewModel.editAccountErrorStare() }) {
                    Text(text = stringResource(id = R.string.ok), color = AppTheme.colors.black)
                }
            },
        )
    }
    BackHandler { onBackHandler() }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppBaseTopBar(onBackClick = onBackHandler)
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = AppTheme.colors.backgroundPink)
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                RoundAsyncImage(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .size(150.dp)
                        .clip(AppTheme.shapes.circle)
                        .clickable { launcher.launch("image/*") },
                    data = selectedImageUri ?: userProfile?.image,
                    placeholder = painterResource(id = R.drawable.placeholder_user_avatar),
                    error = painterResource(id = R.drawable.placeholder_user_avatar),
                )
                Text(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 70.dp)
                        .align(Alignment.Start),
                    text = stringResource(R.string.name),
                    color = AppTheme.colors.darkPink,
                    style = AppTheme.typography.bold,
                    fontSize = 18.sp,
                )
                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BaseTextField(
                        modifier = Modifier.padding(top = 4.dp),
                        textState = name,
                        label = stringResource(id = R.string.name),
                        focusedContainerColor = AppTheme.colors.white,
                        unfocusedContainerColor = AppTheme.colors.white,
                        shape = AppTheme.shapes.circle,
                        onValueChange = { input -> profileViewModel.setName(name = input) },
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 70.dp)
                        .align(Alignment.Start),
                    text = stringResource(R.string.surname),
                    color = AppTheme.colors.darkPink,
                    style = AppTheme.typography.bold,
                    fontSize = 18.sp,
                )
                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BaseTextField(
                        modifier = Modifier.padding(top = 4.dp),
                        textState = surName,
                        label = stringResource(id = R.string.surname),
                        focusedContainerColor = AppTheme.colors.white,
                        unfocusedContainerColor = AppTheme.colors.white,
                        shape = AppTheme.shapes.circle,
                        imeAction = ImeAction.Done,
                        onValueChange = { input -> profileViewModel.setSurName(input) },
                    )
                }
                AuthButton(
                    modifier = Modifier
                        .padding(horizontal = 100.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    backgroundColor = AppTheme.colors.lightPink,
                    shape = RoundedCornerShape(13.dp),
                    buttonTitle = stringResource(id = R.string.done),
                    enabled = doneButtonEnabled,
                    fontSize = 20.sp,
                    onClick = {
                        profileViewModel.updateProfile()

                    },
                )
            }
            if (showLoading)
                CircularProgress()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditAccountScreenPreview() {
    EditAccountScreen(profileViewModel = getViewModel())
}