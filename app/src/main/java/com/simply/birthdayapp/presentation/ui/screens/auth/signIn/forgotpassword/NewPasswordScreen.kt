package com.simply.birthdayapp.presentation.ui.screens.auth.signIn.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
fun NewPasswordScreen(
    forgotPasswordViewModel: ForgotPasswordViewModel,
    ) {
    Scaffold(
        topBar = { AppBaseTopBar() }
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
                    .padding(top = 100.dp, start = 70.dp)
                    .align(Alignment.Start),
                text = "New password",
                color = AppTheme.colors.darkPink,
                style = AppTheme.typography.bold,
                fontSize = 18.sp,
            )

            PasswordTextFiled(
                modifier = Modifier.padding(top = 4.dp),
                focusedContainerColor = AppTheme.colors.white,
                unfocusedContainerColor = AppTheme.colors.white,
                label = stringResource(id = R.string.password),
                errorText = stringResource(id = R.string.password_error),
                shape = AppTheme.shapes.circle,
                onValueChange = { password -> },
            )

            Text(
                modifier = Modifier
                    .padding(top = 24.dp, start = 70.dp)
                    .align(Alignment.Start),
                text = "Repeat New Password",
                color = AppTheme.colors.darkPink,
                style = AppTheme.typography.bold,
                fontSize = 18.sp,
            )

            PasswordTextFiled(
                modifier = Modifier.padding(top = 4.dp),
                focusedContainerColor = AppTheme.colors.white,
                unfocusedContainerColor = AppTheme.colors.white,
                label = stringResource(id = R.string.password),
                errorText = stringResource(id = R.string.password_error),
                shape = AppTheme.shapes.circle,
                onValueChange = { password -> },
            )

            AuthButton(
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 24.dp)
                    .padding(top = 200.dp)
                    .height(41.dp)
                    .width(200.dp),

                backgroundColor = AppTheme.colors.lightPink,
                shape = RoundedCornerShape(13.dp),
                buttonTitle = stringResource(id = R.string.done),
                onClick = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewPasswordScreenPreview() {
    NewPasswordScreen(forgotPasswordViewModel = getViewModel())
}