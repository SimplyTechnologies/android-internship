package com.simply.birthdayapp.presentation.ui.screens.auth.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.components.LandingButton
import com.simply.birthdayapp.presentation.ui.theme.BackgroundColor
import com.simply.birthdayapp.presentation.ui.theme.Primary1
import com.simply.birthdayapp.presentation.ui.theme.Primary2

@Composable
fun LandingScreen(
    onSignInClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .padding(horizontal = 54.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
        LandingButton(
            shape = RoundedCornerShape(
                topStart = 42.dp,
                topEnd = 26.dp,
                bottomEnd = 26.dp
            ),
            buttonTitle = stringResource(id = R.string.sign_in),
            backgroundColor = Primary1,
            textColor = Primary2,
            onClick = { onSignInClick() })

        LandingButton(
            shape = RoundedCornerShape(
                bottomEnd = 42.dp,
                topStart = 26.dp,
                bottomStart = 26.dp
            ),
            buttonTitle = stringResource(id = R.string.register),
            backgroundColor = Primary2,
            textColor = Primary1,
            onClick = { onRegisterClick() })
    }
}

@Preview(showBackground = true)
@Composable
private fun LandingScreenPreview() {
    LandingScreen()
}