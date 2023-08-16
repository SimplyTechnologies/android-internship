package com.simply.birthdayapp.presentation.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.navigation.BottomNavigation

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    BottomNavBarScaffold(navController) {
        BottomNavigation(navController)
    }
}