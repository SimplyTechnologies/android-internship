package com.simply.birthdayapp.presentation.ui.screens.auth.signIn.forgotpassword

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.getViewModel


sealed class ForgotScreen(val route: String) {
    data object ForgotPasswordScreen : ForgotScreen("forgot-password-screen")
    data object NewPasswordScreen : ForgotScreen("new-password-screen")
}

@Composable
fun ForgotMainScreen(
    forgotPasswordViewModel: ForgotPasswordViewModel = getViewModel(),
    onBack: () -> Unit = {},
    navToSignInScreen: () -> Unit = {}

) {
    val nestedNavController = rememberNavController()
    fun navigateToNewPasswordScreen() {
        nestedNavController.navigate(ForgotScreen.NewPasswordScreen.route) {
            popUpTo(ForgotScreen.NewPasswordScreen.route) {
                inclusive = true
            }
        }
    }

    fun navigateToForgotPasswordScreen() {
        nestedNavController.navigate(ForgotScreen.ForgotPasswordScreen.route) {
            popUpTo(ForgotScreen.ForgotPasswordScreen.route) {
                inclusive = true
            }
        }
    }
    NavHost(
        navController = nestedNavController,
        startDestination = ForgotScreen.ForgotPasswordScreen.route,
        route = "root",
    ) {

        composable(ForgotScreen.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(
                onNewPasswordButtonClick = { navigateToNewPasswordScreen() },
                forgotPasswordViewModel = forgotPasswordViewModel,
                onBackClick = onBack,
            )
        }
        composable(ForgotScreen.NewPasswordScreen.route) {
            NewPasswordScreen(
                forgotPasswordViewModel = forgotPasswordViewModel,
                onResetPasswordSuccess = navToSignInScreen,
                onCodeInvalid = { navigateToForgotPasswordScreen() },
                navToFprgotPasswordScreen = { navigateToForgotPasswordScreen() },
            )
        }
    }
}