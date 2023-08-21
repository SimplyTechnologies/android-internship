package com.simply.birthdayapp.presentation.navigation

import androidx.compose.ui.graphics.painter.Painter

data class NavBarItem(
    val name: String,
    val route: String,
    val icon: Painter,
)