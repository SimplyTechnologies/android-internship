package com.simply.birthdayapp.presentation.ui.screens.main.profile

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.components.AppBaseTopBar
import com.simply.birthdayapp.presentation.ui.components.AuthButton
import com.simply.birthdayapp.presentation.ui.components.PasswordTextFiled
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun ChangePasswordScreen(
    profileViewModel: ProfileViewModel,
    onChangePasswordSuccess: () -> Unit = {},
    navToProfileScreen: () -> Unit = {},
) {
    val oldPassword by profileViewModel.oldPassword.collectAsState()
    val password by profileViewModel.password.collectAsState()
    val repeatPassword by profileViewModel.repeatPassword.collectAsState()
    val hasPasswordError by profileViewModel.hasPasswordError.collectAsState()
    val hasOldPasswordError by profileViewModel.hasOldPasswordError.collectAsState()
    val hasRepeatPasswordError by profileViewModel.hasRepeatPasswordError.collectAsState()
    val doneButtonEnabled by profileViewModel.enableDoneButton.collectAsState()
    val changePasswordSuccess by profileViewModel.changePasswordSuccess.collectAsState()
    val changePasswordErrorMessage by profileViewModel.changePasswordErrorMessage.collectAsState()
    val changePasswordErrorState by profileViewModel.changePasswordErrorState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val onBackHandler = {
        focusManager.clearFocus()
        navToProfileScreen()
        profileViewModel.clearForm()
    }

    LaunchedEffect(changePasswordSuccess) {
        if (changePasswordSuccess) {
            Toast.makeText(context, R.string.change_password_success, Toast.LENGTH_SHORT).show()
            onChangePasswordSuccess()
            profileViewModel.changePasswordSuccessState()
        }
    }
    LaunchedEffect(changePasswordErrorMessage) {
        if (changePasswordErrorMessage.isNotEmpty()) {
            Toast.makeText(context, changePasswordErrorMessage, Toast.LENGTH_SHORT).show()
            profileViewModel.changePasswordErrorMessage()
        }
    }
    if (changePasswordErrorState) {
        AlertDialog(
            containerColor = AppTheme.colors.white,
            titleContentColor = AppTheme.colors.black,
            textContentColor = AppTheme.colors.black,
            onDismissRequest = { profileViewModel.changePasswordErrorState() },
            title = { Text(text = stringResource(id = R.string.change_password_error)) },
            text = { Text(text = stringResource(R.string.change_password_network_error)) },
            confirmButton = {
                TextButton(onClick = { profileViewModel.changePasswordErrorState() }) {
                    Text(text = stringResource(id = R.string.ok), color = AppTheme.colors.black)
                }
            },
        )
    }
    BackHandler { onBackHandler() }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppBaseTopBar(onBackClick = onBackHandler)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.backgroundPink)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 100.dp, start = 70.dp)
                    .align(Alignment.Start),
                text = stringResource(R.string.old_password),
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
                PasswordTextFiled(
                    modifier = Modifier.padding(top = 2.dp),
                    textState = oldPassword,
                    label = stringResource(id = R.string.password),
                    focusedContainerColor = AppTheme.colors.white,
                    unfocusedContainerColor = AppTheme.colors.white,
                    hasPasswordError = hasOldPasswordError,
                    errorText = stringResource(id = R.string.password_error),
                    onValueChange = { oldPassword -> profileViewModel.setOldPassword(oldPassword = oldPassword) },
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 24.dp, start = 70.dp)
                    .align(Alignment.Start),
                text = stringResource(R.string.new_password),
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
                PasswordTextFiled(
                    modifier = Modifier.padding(top = 2.dp),
                    textState = password,
                    label = stringResource(id = R.string.password),
                    focusedContainerColor = AppTheme.colors.white,
                    unfocusedContainerColor = AppTheme.colors.white,
                    hasPasswordError = hasPasswordError,
                    errorText = stringResource(id = R.string.password_error),
                    onValueChange = { password -> profileViewModel.setPassword(password = password) },
                )
            }
            Text(
                modifier = Modifier
                    .padding(top = 24.dp, start = 70.dp)
                    .align(Alignment.Start),
                text = stringResource(R.string.repeat_new_password),
                color = AppTheme.colors.darkPink,
                style = AppTheme.typography.bold,
                fontSize = 18.sp,
            )
            Column(
                modifier = Modifier
                    .padding(bottom = 100.dp)
                    .width(300.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PasswordTextFiled(
                    modifier = Modifier.padding(top = 2.dp),
                    label = stringResource(id = R.string.password),
                    textState = repeatPassword,
                    focusedContainerColor = AppTheme.colors.white,
                    unfocusedContainerColor = AppTheme.colors.white,
                    hasPasswordError = hasRepeatPasswordError,
                    errorText = stringResource(id = R.string.repeat_password_error),
                    imeAction = ImeAction.Done,
                    onValueChange = { repeatPassword ->
                        profileViewModel.setRepeatPassword(repeatPassword = repeatPassword)
                    },
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
                onClick = { profileViewModel.changePassword() },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangePasswordScreenPreview() {
    ChangePasswordScreen(profileViewModel = getViewModel())
}