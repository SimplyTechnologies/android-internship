package com.simply.birthdayapp.presentation.ui.screens.auth.signIn.forgotpassword


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
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
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun ForgotPasswordScreen(
    forgotPasswordViewModel: ForgotPasswordViewModel,
    onBackClick: () -> Unit = {},
    onNewPasswordButtonClick: () -> Unit = {}
) {
    val getCodeErrorMessage by forgotPasswordViewModel.getCodeErrorMessage.collectAsState()
    val hasEmailError by forgotPasswordViewModel.hasEmailError.collectAsState()
    val email by forgotPasswordViewModel.email.collectAsState()
    val code by forgotPasswordViewModel.code.collectAsState()
    val showPasswordCodeSection by forgotPasswordViewModel.hasGetCodeSuccess.collectAsState()
    val getCodeErrorState by forgotPasswordViewModel.getCodeErrorState.collectAsState()
    val emailMaxLength = 30
    val codeMaxLength = 6
    val enabledGetCodeButton: Boolean = email.isNotEmpty() && !hasEmailError
    val context = LocalContext.current

    LaunchedEffect(getCodeErrorMessage) {
        if (getCodeErrorMessage.isNotEmpty()) {
            Toast.makeText(context, getCodeErrorMessage, Toast.LENGTH_SHORT).show()
            forgotPasswordViewModel.resetGetCodeErrorMessage()
        }
    }
    if (getCodeErrorState) {
        AlertDialog(
            containerColor = AppTheme.colors.white,
            titleContentColor = AppTheme.colors.black,
            textContentColor = AppTheme.colors.black,
            onDismissRequest = { forgotPasswordViewModel.getCodeErrorState() },
            title = { Text(text = stringResource(id = R.string.sign_in_error)) },
            text = { Text(text = stringResource(R.string.network_error)) },
            confirmButton = {
                TextButton(
                    onClick = { forgotPasswordViewModel.getCodeErrorState() }
                ) {
                    Text(text = stringResource(id = R.string.ok), color = AppTheme.colors.black)
                }
            },
        )
    }


    Scaffold(topBar = { AppBaseTopBar(onBackClick = onBackClick) }
    ) {
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
                    .padding(top = 100.dp),
                text = stringResource(id = R.string.email),
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
                    modifier = Modifier.padding(top = 2.dp),
                    textState = email,
                    label = stringResource(id = R.string.email),
                    focusedContainerColor = AppTheme.colors.white,
                    unfocusedContainerColor = AppTheme.colors.white,
                    shape = AppTheme.shapes.smallRoundedCorners,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done,
                    errorText = stringResource(id = R.string.register_email_error),
                    hasError = hasEmailError,
                    onValueChange = { email ->
                        if (email.length <= emailMaxLength) forgotPasswordViewModel.setEmail(email)
                    },
                )
            }
            AuthButton(
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 24.dp)
                    .height(41.dp)
                    .width(200.dp),
                backgroundColor = AppTheme.colors.white,
                shape = RoundedCornerShape(13.dp),
                enabled = enabledGetCodeButton,
                buttonTitle = stringResource(id = R.string.get_the_code),
                onClick = { forgotPasswordViewModel.getCode() },
            )
            if (showPasswordCodeSection) {
                Card(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .width(273.dp)
                        .height(143.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxSize()
                            .background(AppTheme.colors.white),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 16.dp),
                            text = "Password Code",
                            fontSize = 20.sp,
                            color = AppTheme.colors.darkPink,
                            fontWeight = FontWeight(700),
                            textAlign = TextAlign.Center,
                            style = AppTheme.typography.bold
                        )
                        BaseTextField(
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .height(48.dp)
                                .width(140.dp),
                            textState = code,
                            fontSize = 18.sp,
                            keyboardType = KeyboardType.Number,
                            focusedContainerColor = AppTheme.colors.backgroundPink,
                            unfocusedContainerColor = AppTheme.colors.backgroundPink,
                            shape = AppTheme.shapes.smallRoundedCorners,
                            onValueChange = { code ->
                                if (code.length <= codeMaxLength)
                                    forgotPasswordViewModel.setCode(code)
                            }
                        )
                    }
                }
                AuthButton(
                    modifier = Modifier
                        .padding(horizontal = 32.dp, vertical = 24.dp)
                        .padding(top = 100.dp)
                        .height(41.dp)
                        .width(200.dp),
                    backgroundColor = AppTheme.colors.lightPink,
                    shape = RoundedCornerShape(13.dp),
                    buttonTitle = stringResource(id = R.string.set_new_password),
                    enabled = code.isNotEmpty() && code.length == codeMaxLength,
                    onClick = onNewPasswordButtonClick,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(forgotPasswordViewModel = getViewModel())
}