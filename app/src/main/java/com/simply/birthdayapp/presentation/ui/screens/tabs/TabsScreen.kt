package com.simply.birthdayapp.presentation.ui.screens.tabs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.navigation.TabsNavigation

@Composable
fun TabsScreen() {
    val navController = rememberNavController()

    BottomNavBarScaffold(navController) {
        TabsNavigation(navController)
    }
}