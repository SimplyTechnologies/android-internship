package com.simply.birthdayapp.presentation.ui.screens.auth.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.components.LandingButton
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun LandingScreen(
    onSignInClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.backgroundPink)
            .padding(horizontal = 54.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
        LandingButton(
            shape = AppTheme.shapes.risingStartRoundedEndCorners,
            buttonTitle = stringResource(id = R.string.sign_in),
            backgroundColor = AppTheme.colors.lightPink,
            textColor = AppTheme.colors.darkPink,
            onClick = { onSignInClick() })

        LandingButton(
            shape = AppTheme.shapes.risingEndRoundedStartCorners,
            buttonTitle = stringResource(id = R.string.register),
            backgroundColor = AppTheme.colors.darkPink,
            textColor = AppTheme.colors.lightPink,
            onClick = { onRegisterClick() })
    }
}

@Preview(showBackground = true)
@Composable
private fun LandingScreenPreview() {
    LandingScreen()
}