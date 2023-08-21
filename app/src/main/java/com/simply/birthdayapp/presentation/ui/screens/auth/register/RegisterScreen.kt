package com.simply.birthdayapp.presentation.ui.screens.auth.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
import com.simply.birthdayapp.presentation.ui.theme.BackgroundColor
import com.simply.birthdayapp.presentation.ui.theme.Primary1
import com.simply.birthdayapp.presentation.ui.theme.Primary2
import org.koin.androidx.compose.get

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel,
    onRegisterBackClick: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {}
) {
    val name by registerViewModel.name.collectAsState()
    val surName by registerViewModel.surName.collectAsState()
    val email by registerViewModel.email.collectAsState()
    val password by registerViewModel.password.collectAsState()
    val repeatPassword by registerViewModel.repeatPassword.collectAsState()
    val hasEmailError by registerViewModel.hasEmailError.collectAsState()
    val registrationSuccess by registerViewModel.registrationSuccessState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(registrationSuccess) {
        if (registrationSuccess) {
            Toast.makeText(context, R.string.register_success, Toast.LENGTH_SHORT).show()
            onRegisterSuccess()
            registerViewModel.resetSuccessState()
        }
    }

    //TODO: Alert for error case using

    Scaffold(
        topBar = { AppBaseTopBar(onBackClick = onRegisterBackClick) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = BackgroundColor),
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
                        .background(Color.White)
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(id = R.string.register),
                        fontSize = 20.sp,
                        color = Primary2,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.karm_light)),
                    )
                    BaseTextField(
                        textState = name,
                        label = stringResource(id = R.string.name),
                        shape = RoundedCornerShape(13.dp),
                        onValueChange = { input ->
                            registerViewModel.setName(input)
                        }
                    )
                    BaseTextField(
                        textState = surName,
                        label = stringResource(id = R.string.surname),
                        shape = RoundedCornerShape(13.dp),
                        onValueChange = { surName ->
                            registerViewModel.setSurName(surName)
                        }
                    )
                    BaseTextField(
                        textState = email,
                        label = stringResource(id = R.string.email),
                        shape = RoundedCornerShape(13.dp),
                        onValueChange = { email ->
                            registerViewModel.setEmail(email)
                        },
                        keyboardType = KeyboardType.Email,
                    )
                    if (hasEmailError) {
                        Row(
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                textAlign = TextAlign.Start,
                                text = stringResource(id = R.string.register_email_error),
                                color = Color.Red,
                                fontFamily = FontFamily(Font(resId = R.font.karm_light)),
                                fontSize = 16.sp,
                            )
                        }
                    }
                    PasswordTextFiled(
                        textState = password,
                        label = stringResource(id = R.string.password),
                        onValueChange = { password ->
                            registerViewModel.setPassword(password = password)
                        }
                    )
                    PasswordTextFiled(
                        textState = repeatPassword,
                        label = stringResource(id = R.string.repeat_password),
                        onValueChange = { repeatPassword ->
                            registerViewModel.setRepeatPassword(repeatPassword = repeatPassword)
                        }
                    )
                    AuthButton(
                        shape = RoundedCornerShape(24.dp),
                        buttonTitle = stringResource(id = R.string.register),
                        backgroundColor = Primary1,
                        textColor = Primary2,
                        enabled = !hasEmailError,
                        onClick = {
                            registerViewModel.registerAccount()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(registerViewModel = get())
}