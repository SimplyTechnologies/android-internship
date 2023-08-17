package com.simply.birthdayapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.screens.auth.landing.LandingScreen
import com.simply.birthdayapp.presentation.ui.screens.auth.register.RegisterScreen
import com.simply.birthdayapp.presentation.ui.screens.auth.signIn.SignInScreen

sealed class AuthScreen(val route: String) {
    data object LandingScreen : AuthScreen("landing-screen")
    data object RegisterScreen : AuthScreen("register-screen")
    data object SignInScreen : AuthScreen("sign-in-screen")
}

@Composable
fun AuthNavigation() {
    val nestedNavController = rememberNavController()
    NavHost(
        navController = nestedNavController,
        startDestination = AuthScreen.LandingScreen.route,
        route = "root",
    ) {
        composable(AuthScreen.LandingScreen.route) {
            LandingScreen(
                onSignInClick = { nestedNavController.navigate(AuthScreen.SignInScreen.route) },
                onRegisterClick = { nestedNavController.navigate(AuthScreen.RegisterScreen.route) })
        }
        composable(AuthScreen.RegisterScreen.route) { RegisterScreen() }
        composable(AuthScreen.SignInScreen.route) { SignInScreen() }
    }
}

