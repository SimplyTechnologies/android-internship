package com.simply.birthdayapp.presentation.ui.screens.auth.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.simply.birthdayapp.presentation.ui.components.PasswordTextFiled
import com.simply.birthdayapp.presentation.ui.theme.AppTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = getViewModel(),
    onRegisterBackClick: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {},
    onRegisterError: () -> Unit = {},
) {
    val name by registerViewModel.name.collectAsState()
    val surName by registerViewModel.surName.collectAsState()
    val email by registerViewModel.email.collectAsState()
    val password by registerViewModel.password.collectAsState()
    val repeatPassword by registerViewModel.repeatPassword.collectAsState()
    val hasEmailError by registerViewModel.hasEmailError.collectAsState()
    val hasPasswordError by registerViewModel.hasPasswordError.collectAsState()
    val hasRepeatPasswordError by registerViewModel.hasRepeatPasswordError.collectAsState()
    val registrationSuccess by registerViewModel.registrationSuccessState.collectAsState()
    val registrationErrorState by registerViewModel.registrationErrorState.collectAsState()
    val registrationErrorMessage by registerViewModel.registerErrorMessage.collectAsState()
    val registerButtonEnabled by registerViewModel.enableRegisterButton.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(registrationSuccess) {
        if (registrationSuccess) {
            Toast.makeText(context, R.string.register_success, Toast.LENGTH_SHORT).show()
            onRegisterSuccess()
            registerViewModel.resetSuccessState()
        }
    }
    LaunchedEffect(registrationErrorMessage) {
        if (registrationErrorMessage.isNotEmpty()) {
            Toast.makeText(context, registrationErrorMessage, Toast.LENGTH_SHORT).show()
            onRegisterError()
            registerViewModel.resetRegisterErrorMessage()
        }
    }
    if (registrationErrorState) {
        AlertDialog(
            containerColor = AppTheme.colors.white,
            titleContentColor = AppTheme.colors.black,
            textContentColor = AppTheme.colors.black,
            onDismissRequest = { registerViewModel.registerErrorState() },
            title = { Text(text = stringResource(id = R.string.registration_error)) },
            text = { Text(text = stringResource(R.string.network_error)) },
            confirmButton = {
                TextButton(onClick = { registerViewModel.registerErrorState() }) {
                    Text(text = stringResource(id = R.string.ok), color = AppTheme.colors.black)
                }
            },
        )
    }
    Scaffold(
        topBar = { AppBaseTopBar(onBackClick = onRegisterBackClick) }
    ) {
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
                        text = stringResource(id = R.string.register),
                        fontSize = 20.sp,
                        color = AppTheme.colors.darkPink,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.karm_light)),
                    )
                    BaseTextField(
                        modifier = Modifier.padding(top = 24.dp),
                        textState = name,
                        label = stringResource(id = R.string.name),
                        shape = AppTheme.shapes.smallRoundedCorners,
                        onValueChange = { input -> registerViewModel.setName(input) },
                    )
                    BaseTextField(
                        modifier = Modifier.padding(top = 24.dp),
                        textState = surName,
                        label = stringResource(id = R.string.surname),
                        shape = AppTheme.shapes.smallRoundedCorners,
                        onValueChange = { surName -> registerViewModel.setSurName(surName) },
                    )
                    BaseTextField(
                        modifier = Modifier.padding(top = 24.dp),
                        textState = email,
                        label = stringResource(id = R.string.email),
                        shape = AppTheme.shapes.smallRoundedCorners,
                        keyboardType = KeyboardType.Email,
                        errorText = stringResource(id = R.string.register_email_error),
                        hasError = hasEmailError,
                        onValueChange = { email -> registerViewModel.setEmail(email) },
                    )
                    PasswordTextFiled(
                        modifier = Modifier.padding(top = 24.dp),
                        textState = password,
                        label = stringResource(id = R.string.password),
                        hasPasswordError = hasPasswordError,
                        errorText = stringResource(id = R.string.password_error),
                        onValueChange = { password -> registerViewModel.setPassword(password) },
                    )
                    PasswordTextFiled(
                        modifier = Modifier.padding(top = 24.dp),
                        textState = repeatPassword,
                        label = stringResource(id = R.string.repeat_password),
                        imeAction = ImeAction.Done,
                        hasPasswordError = hasRepeatPasswordError,
                        errorText = stringResource(id = R.string.repeat_password_error),
                        onValueChange = { repeatPassword ->
                            registerViewModel.setRepeatPassword(repeatPassword = repeatPassword)
                        },
                    )
                    AuthButton(
                        modifier = Modifier
                            .padding(horizontal = 32.dp, vertical = 16.dp)
                            .height(51.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        buttonTitle = stringResource(id = R.string.register),
                        enabled = registerButtonEnabled,
                        onClick = { registerViewModel.registerAccount() },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(registerViewModel = getViewModel())
}