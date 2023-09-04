package com.simply.birthdayapp.presentation.ui.screens.auth.signIn.forgotpassword

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
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
import com.simply.birthdayapp.presentation.ui.components.CircularProgress
import com.simply.birthdayapp.presentation.ui.components.PasswordTextFiled
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun NewPasswordScreen(
    forgotPasswordViewModel: ForgotPasswordViewModel,
    onResetPasswordSuccess: () -> Unit = {},
    onCodeInvalid: () -> Unit = {},
    navToForgotPasswordScreen: () -> Unit = {},
) {
    val password by forgotPasswordViewModel.password.collectAsState()
    val hasPasswordError by forgotPasswordViewModel.hasPasswordError.collectAsState()
    val hasRepeatPasswordError by forgotPasswordViewModel.hasRepeatPasswordError.collectAsState()
    val repeatPassword by forgotPasswordViewModel.repeatPassword.collectAsState()
    val doneButtonEnabled by forgotPasswordViewModel.enableDoneButton.collectAsState()
    val resetPasswordSuccess by forgotPasswordViewModel.resetPasswordSuccess.collectAsState()
    val resetPasswordErrorState by forgotPasswordViewModel.resetPasswordErrorState.collectAsState()
    val resetPasswordErrorMessage by forgotPasswordViewModel.resetPasswordErrorMessage.collectAsState()
    val showLoading by forgotPasswordViewModel.isOnLoadingState.collectAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val onBackHandler = {
        focusManager.clearFocus()
        navToForgotPasswordScreen()
        forgotPasswordViewModel.clearPasswords()
    }

    LaunchedEffect(resetPasswordSuccess) {
        if (resetPasswordSuccess) {
            Toast.makeText(context, R.string.reset_password_success, Toast.LENGTH_SHORT).show()
            onResetPasswordSuccess()
            forgotPasswordViewModel.resetPasswordSuccessState()
        }
    }
    LaunchedEffect(resetPasswordErrorMessage) {
        if (resetPasswordErrorMessage.isNotEmpty()) {
            Toast.makeText(context, resetPasswordErrorMessage, Toast.LENGTH_SHORT).show()
            onCodeInvalid()
            forgotPasswordViewModel.resetPasswordErrorMessage()
        }
    }
    BackHandler { onBackHandler() }

    if (resetPasswordErrorState) {
        AlertDialog(
            containerColor = AppTheme.colors.white,
            titleContentColor = AppTheme.colors.black,
            textContentColor = AppTheme.colors.black,
            onDismissRequest = { forgotPasswordViewModel.resetPasswordErrorState() },
            title = { Text(text = stringResource(id = R.string.reset_password_error)) },
            text = { Text(text = stringResource(R.string.reset_password_network_error)) },
            confirmButton = {
                TextButton(onClick = { forgotPasswordViewModel.resetPasswordErrorState() }) {
                    Text(text = stringResource(id = R.string.ok), color = AppTheme.colors.black)
                }
            },
        )
    }
    Scaffold(topBar = {
        AppBaseTopBar(onBackClick = onBackHandler)
    }) {
        Box {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = AppTheme.colors.backgroundPink)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 100.dp, start = 70.dp)
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
                        onValueChange = { password ->
                            forgotPasswordViewModel.setPassword(password = password)
                        },
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
                        imeAction = ImeAction.Done,
                        errorText = stringResource(id = R.string.repeat_password_error),
                        onValueChange = { repeatPassword ->
                            forgotPasswordViewModel.setRepeatPassword(
                                repeatPassword = repeatPassword
                            )
                        },
                    )
                }
                AuthButton(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(top = 200.dp)
                        .width(150.dp),
                    backgroundColor = AppTheme.colors.lightPink,
                    shape = RoundedCornerShape(13.dp),
                    buttonTitle = stringResource(id = R.string.done),
                    enabled = doneButtonEnabled,
                    fontSize = 20.sp,
                    onClick = { forgotPasswordViewModel.resetPassword() },
                )
            }
            if (showLoading)
                CircularProgress()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewPasswordScreenPreview() {
    NewPasswordScreen(forgotPasswordViewModel = getViewModel())
}