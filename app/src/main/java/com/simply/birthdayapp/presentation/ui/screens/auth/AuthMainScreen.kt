package com.simply.birthdayapp.presentation.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.screens.auth.landing.LandingScreen
import com.simply.birthdayapp.presentation.ui.screens.auth.register.RegisterScreen
import com.simply.birthdayapp.presentation.ui.screens.auth.signIn.SignInScreen
import com.simply.birthdayapp.presentation.ui.screens.auth.signIn.forgotpassword.ForgotMainScreen
import org.koin.androidx.compose.getViewModel

sealed class AuthScreen(val route: String) {
    data object LandingScreen : AuthScreen("landing-screen")
    data object RegisterScreen : AuthScreen("register-screen")
    data object SignInScreen : AuthScreen("sign-in-screen")
    data object ForgotPasswordMainScreen : AuthScreen("forgot-password-main-screen")
}

@Composable
fun AuthMainScreen(
    navigateToMainScreen: () -> Unit = {},
    authMainViewModel: AuthMainViewModel = getViewModel(),
) {
    val hasRememberPassword by authMainViewModel.rememberPassword.collectAsState()
    val nestedNavController = rememberNavController()
    LaunchedEffect(hasRememberPassword) {
        if (hasRememberPassword) {
            navigateToMainScreen()
        }
    }
    fun navigateToLandingScreen() {
        nestedNavController.navigate(AuthScreen.LandingScreen.route) {
            popUpTo(AuthScreen.LandingScreen.route) {
                inclusive = true
            }
        }
    }

    fun navigateToSignInScreen() {
        nestedNavController.navigate(AuthScreen.SignInScreen.route) {
            popUpTo(AuthScreen.SignInScreen.route) {
                inclusive = true
            }
        }
    }

    fun navigateToForgotMainScreen() {
        nestedNavController.navigate(AuthScreen.ForgotPasswordMainScreen.route) {
            popUpTo(AuthScreen.ForgotPasswordMainScreen.route) {
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

                onRegisterBackClick = { navigateToLandingScreen() },
                onRegisterSuccess = { navigateToSignInScreen() },
                onRegisterError = { navigateToSignInScreen() }
            )
        }
        composable(AuthScreen.SignInScreen.route) {
            SignInScreen(
                onSignInBackClick = { navigateToLandingScreen() },
                onLoginSuccass = navigateToMainScreen,
                onForgotPasswordClick = { navigateToForgotMainScreen() }

            )
        }
        composable(AuthScreen.ForgotPasswordMainScreen.route) {
            ForgotMainScreen(
                onBack = { navigateToSignInScreen() }
            )
        }
    }
}