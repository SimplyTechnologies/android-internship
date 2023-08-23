package com.simply.birthdayapp.presentation.ui.screens.auth.signIn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.simply.birthdayapp.presentation.ui.extenstions.isValidEmail
import com.simply.birthdayapp.presentation.ui.screens.auth.register.RegisterViewModel
import com.simply.birthdayapp.presentation.ui.theme.BackgroundColor
import com.simply.birthdayapp.presentation.ui.theme.Primary1
import com.simply.birthdayapp.presentation.ui.theme.Primary2
import org.koin.androidx.compose.get
import com.simply.birthdayapp.presentation.ui.theme.Primary

@Composable
fun SignInScreen(
    registerViewModel: RegisterViewModel,
    onSignInBackClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    val registeredEmail by registerViewModel.registeredEmail.collectAsState()
    var email by remember { mutableStateOf(registeredEmail) }
    var password by remember { mutableStateOf("") }
    val checkedState = remember { mutableStateOf(false) }
    var hasEmailError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { AppBaseTopBar(onBackClick = onSignInBackClick) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = Primary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
                        modifier = Modifier
                            .padding(top = 16.dp),
                        text = stringResource(id = R.string.sign_in),
                        fontSize = 20.sp,
                        color = Primary2,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.karm_light)),
                    )
                    BaseTextField(
                        textState = email,
                        label = stringResource(id = R.string.email),
                        onValueChange = { input ->
                            email = input
                            hasEmailError = !input.isValidEmail()
                        },
                        keyboardType = KeyboardType.Email
                    )
                    if (hasEmailError) {
                        Row(
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth(),
                            Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                textAlign = TextAlign.Start,
                                text = stringResource(id = R.string.register_email_error),
                                color = Color.Red,
                                fontFamily = FontFamily(Font(R.font.karm_light)),
                                fontSize = 16.sp
                            )
                        }
                    }
                    PasswordTextFiled(
                        textState = password,
                        label = stringResource(id = R.string.password),
                        onValueChange = { input ->
                            password = input
                        }
                    )
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = checkedState.value,
                                onCheckedChange = { checkedState.value = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Primary1
                                )
                            )
                            Text(
                                text = stringResource(id = R.string.remember_password),
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.karm_light)),
                                fontWeight = FontWeight(700),
                                color = Primary2
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
                                color = Primary2
                            )
                        }
                    }
                    AuthButton(
                        shape = RoundedCornerShape(24.dp),
                        buttonTitle = stringResource(id = R.string.register),
                        backgroundColor = Primary1,
                        textColor = Primary2
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    SignInScreen(registerViewModel = get())
}