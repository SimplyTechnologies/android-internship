package com.simply.birthdayapp.presentation.ui.screens.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.ui.theme.AppTheme

@Composable
fun RegisterScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.backgroundPink),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(R.string.register))
    }
}

@Preview(showBackground = false)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen()
}