package com.simply.birthdayapp.presentation.ui

import androidx.compose.runtime.Composable
import com.simply.birthdayapp.presentation.navigation.TopLevelNavigation
import com.simply.birthdayapp.presentation.ui.theme.BirthdayAppTheme

@Composable
fun BirthdayApp() = BirthdayAppTheme {
    TopLevelNavigation()
}