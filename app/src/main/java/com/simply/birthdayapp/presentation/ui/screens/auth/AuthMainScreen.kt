package com.simply.birthdayapp.presentation.ui.screens.auth

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
fun AuthMainScreen() {
    val authNavController = rememberNavController()

    NavHost(
        navController = authNavController,
        startDestination = AuthScreen.LandingScreen.route,
        route = "root",
    ) {
        composable(AuthScreen.LandingScreen.route) {
            LandingScreen(
                onSignInClick = { authNavController.navigate(AuthScreen.SignInScreen.route) },
                onRegisterClick = { authNavController.navigate(AuthScreen.RegisterScreen.route) },
            )
        }
        composable(AuthScreen.RegisterScreen.route) { RegisterScreen() }
        composable(AuthScreen.SignInScreen.route) { SignInScreen() }
    }
}