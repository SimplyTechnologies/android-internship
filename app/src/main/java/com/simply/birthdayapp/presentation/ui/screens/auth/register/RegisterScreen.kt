package com.simply.birthdayapp.presentation.ui.screens.auth.register

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
import com.simply.birthdayapp.presentation.ui.theme.BackgroundColor
import com.simply.birthdayapp.presentation.ui.theme.Primary1
import com.simply.birthdayapp.presentation.ui.theme.Primary2

@Composable
fun RegisterScreen(onRegisterBackClick: () -> Unit = {}) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatpassword by remember { mutableStateOf("") }
    var hasEmailError by remember { mutableStateOf(false) }


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
                        text = stringResource(R.string.register),
                        fontSize = 50.sp,
                        color = Primary2,
                        fontWeight = FontWeight(700),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.karm_light)),
                    )
                    BaseTextField(
                        textState = name,
                        label = stringResource(R.string.name),
                        onValueChange = { input ->
                            name = input
                        }
                    )
                    BaseTextField(
                        textState = surname,
                        label = stringResource(R.string.surname),
                        onValueChange = { input ->
                            surname = input
                        }
                    )
                    BaseTextField(
                        textState = email,
                        label = "Email",
                        onValueChange = { input ->
                            email = input
                            hasEmailError = !input.isValidEmail()
                        },
                        keyboardType = KeyboardType.Email,
                    )
                    if (hasEmailError) {
                        Row(
                            modifier = Modifier
                                .padding(2.dp)
                                .fillMaxWidth(),
                            Arrangement.SpaceBetween,
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                textAlign = TextAlign.Start,
                                text = stringResource(R.string.register_email_error),
                                color = Color.Red,
                                fontFamily = FontFamily(Font(R.font.karm_light)),
                                fontSize = 16.sp,
                            )
                        }
                    }
                    PasswordTextFiled(
                        textState = password,
                        label = stringResource(R.string.password),
                        onValueChange = { input ->
                            password = input
                        }
                    )
                    BaseTextField(
                        textState = repeatpassword,
                        label = stringResource(R.string.repeat_password),
                        onValueChange = { input ->
                            repeatpassword = input
                        }
                    )
                    AuthButton(
                        shape = RoundedCornerShape(24.dp),
                        buttonTitle = stringResource(R.string.register),
                        backgroundColor = Primary1,
                        textColor = Primary2,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen()
}