package com.simply.birthdayapp.presentation.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.screens.auth.landing.LandingScreen
import com.simply.birthdayapp.presentation.ui.screens.auth.register.RegisterScreen
import com.simply.birthdayapp.presentation.ui.screens.auth.signIn.SignInScreen
import org.koin.androidx.compose.getViewModel

sealed class AuthScreen(val route: String) {
    data object LandingScreen : AuthScreen("landing-screen")
    data object RegisterScreen : AuthScreen("register-screen")
    data object SignInScreen : AuthScreen("sign-in-screen")
}

@Composable
fun AuthMainScreen() {
    val nestedNavController = rememberNavController()
    fun navigateToLandingScreen() {
        nestedNavController.navigate(AuthScreen.LandingScreen.route) {
            popUpTo(AuthScreen.LandingScreen.route) {
                inclusive = true
            }
        }
    }
    NavHost(
        navController = nestedNavController,
        startDestination = AuthScreen.LandingScreen.route,
        route = "root",
    ) {
        composable(AuthScreen.LandingScreen.route) {
            LandingScreen(
                onSignInClick = { nestedNavController.navigate(AuthScreen.SignInScreen.route) },
                onRegisterClick = { nestedNavController.navigate(AuthScreen.RegisterScreen.route) }
            )
        }
        composable(AuthScreen.RegisterScreen.route) {
            RegisterScreen(
                registerViewModel = getViewModel(),
                onRegisterBackClick = { navigateToLandingScreen() }
            )
        }
        composable(AuthScreen.SignInScreen.route) {
            SignInScreen(onSignInBackClick = { navigateToLandingScreen() })
        }
    }
}