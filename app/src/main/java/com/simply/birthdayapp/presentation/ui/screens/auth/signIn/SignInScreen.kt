package com.simply.birthdayapp.presentation.ui.screens.auth.signIn

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.components.AppBaseTopBar
import com.simply.birthdayapp.presentation.ui.components.AuthButton
import com.simply.birthdayapp.presentation.ui.components.BaseTextField
import com.simply.birthdayapp.presentation.ui.components.CircularProgress
import com.simply.birthdayapp.presentation.ui.components.PasswordTextFiled
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = getViewModel(),
    onSignInBackClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
) {
    val loginSuccess by signInViewModel.loginSuccessState.collectAsState()
    val loginErrorState by signInViewModel.loginErrorState.collectAsState()
    val loginErrorMessage by signInViewModel.loginErrorMessage.collectAsState()
    val hasPasswordError by signInViewModel.hasPasswordError.collectAsState()
    val hasEmailError by signInViewModel.hasEmailError.collectAsState()
    val email by signInViewModel.email.collectAsState()
    val password by signInViewModel.password.collectAsState()
    val checkedState by signInViewModel.checkedState.collectAsState()
    val loginButtonEnabled by signInViewModel.enableLoginButton.collectAsState()
    val showLoading by signInViewModel.isOnLoadingState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show()
            onLoginSuccess()
            signInViewModel.loginSuccessState()
        }
    }
    LaunchedEffect(loginErrorMessage) {
        if (loginErrorMessage.isNotEmpty()) {
            Toast.makeText(context, loginErrorMessage, Toast.LENGTH_SHORT).show()
            signInViewModel.resetLoginErrorMessage()
        }
    }
    if (loginErrorState) {
        AlertDialog(
            containerColor = AppTheme.colors.white,
            titleContentColor = AppTheme.colors.black,
            textContentColor = AppTheme.colors.black,
            onDismissRequest = { signInViewModel.loginErrorState() },
            title = { Text(text = stringResource(id = R.string.sign_in_error)) },
            text = { Text(text = stringResource(R.string.sign_in_network_error)) },
            confirmButton = {
                TextButton(
                    onClick = { signInViewModel.loginErrorState() }
                ) {
                    Text(text = stringResource(id = R.string.ok), color = AppTheme.colors.black)
                }
            },
        )
    }
    Scaffold(
        topBar = { AppBaseTopBar(onBackClick = onSignInBackClick) }
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = AppTheme.colors.backgroundPink)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Card(
                    modifier = Modifier
                        .width(317.dp)
                        .wrapContentHeight()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .wrapContentHeight()
                            .background(AppTheme.colors.white)
                            .padding(horizontal = 24.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 16.dp),
                            text = stringResource(id = R.string.sign_in),
                            fontSize = 20.sp,
                            color = AppTheme.colors.darkPink,
                            fontWeight = FontWeight(700),
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.karm_light)),
                        )
                        BaseTextField(
                            modifier = Modifier.padding(top = 24.dp),
                            textState = email,
                            label = stringResource(id = R.string.email),
                            shape = AppTheme.shapes.smallRoundedCorners,
                            keyboardType = KeyboardType.Email,
                            errorText = stringResource(id = R.string.register_email_error),
                            hasError = hasEmailError,
                            imeAction = ImeAction.Next,
                            onValueChange = { email ->
                                signInViewModel.setEmail(email = email)
                            },
                        )
                        PasswordTextFiled(
                            modifier = Modifier.padding(top = 24.dp),
                            textState = password,
                            label = stringResource(id = R.string.password),
                            hasPasswordError = hasPasswordError,
                            imeAction = ImeAction.Done,
                            errorText = stringResource(id = R.string.password_error),
                            onValueChange = { password ->
                                signInViewModel.setPassword(password = password)
                            },
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = checkedState,
                                    onCheckedChange = { isChecked ->
                                        signInViewModel.changeRememberPasswordState(isChecked)
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = AppTheme.colors.lightPink),
                                )
                                Text(
                                    text = stringResource(id = R.string.remember_password),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.karm_light)),
                                    fontWeight = FontWeight(700),
                                    color = AppTheme.colors.darkPink,
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .height(32.dp)
                                .fillMaxWidth(),
                            Arrangement.End
                        ) {
                            TextButton(onClick = onForgotPasswordClick) {
                                Text(
                                    text = stringResource(id = R.string.forgot_password),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.karm_light)),
                                    fontWeight = FontWeight(700),
                                    color = AppTheme.colors.darkPink,
                                )
                            }
                        }
                        AuthButton(
                            modifier = Modifier
                                .padding(horizontal = 32.dp, vertical = 16.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            buttonTitle = stringResource(id = R.string.sign_in),
                            enabled = loginButtonEnabled,
                            onClick = { signInViewModel.loginAccount() },
                        )
                    }
                }
            }
            if (showLoading)
                CircularProgress()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    SignInScreen(signInViewModel = getViewModel())
}