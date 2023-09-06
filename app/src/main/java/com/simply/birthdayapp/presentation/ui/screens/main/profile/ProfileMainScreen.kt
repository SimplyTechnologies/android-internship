package com.simply.birthdayapp.presentation.ui.screens.main.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.getViewModel

sealed class ProfileRootScreen(val route: String) {
    data object ProfileScreen : ProfileRootScreen("profile_screen")
    data object EditAccountScreen : ProfileRootScreen("edit_account_screen")
    data object ChangePasswordScreen : ProfileRootScreen("change_password_screen")
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileMainScreen(
    profileViewModel: ProfileViewModel = getViewModel(),
    onSignOutClicked: () -> Unit = {},
    onChangePasswordSuccess: () -> Unit = {},
) {
    val nestedNavController = rememberNavController()
    val keyboardController = LocalSoftwareKeyboardController.current

    fun navToChangePasswordScreen() {
        nestedNavController.navigate(ProfileRootScreen.ChangePasswordScreen.route) {
            popUpTo(ProfileRootScreen.ChangePasswordScreen.route) {
                inclusive = true
            }
        }
    }

    fun navToProfileScreen() {
        keyboardController?.hide()
        nestedNavController.navigate(ProfileRootScreen.ProfileScreen.route) {
            popUpTo(ProfileRootScreen.ProfileScreen.route) {
                inclusive = true
            }
        }
    }

    fun navToEditAccountScreen() {
        nestedNavController.navigate(ProfileRootScreen.EditAccountScreen.route) {
            popUpTo(ProfileRootScreen.EditAccountScreen.route) {
                inclusive = true
            }
        }
    }
    NavHost(
        navController = nestedNavController,
        startDestination = ProfileRootScreen.ProfileScreen.route,
        route = "root",
    ) {
        composable(ProfileRootScreen.ProfileScreen.route) {
            ProfileScreen(
                profileViewModel = profileViewModel,
                onSignOutClicked = onSignOutClicked,
                navToChangePasswordScreen = { navToChangePasswordScreen() },
                navToEditAccountScreen = { navToEditAccountScreen() },
            )
        }
        composable(ProfileRootScreen.ChangePasswordScreen.route) {
            ChangePasswordScreen(
                profileViewModel = profileViewModel,
                onChangePasswordSuccess = onChangePasswordSuccess,
                navToProfileScreen = { navToProfileScreen() },
            )
        }
        composable(ProfileRootScreen.EditAccountScreen.route) {
            EditAccountScreen(
                profileViewModel = profileViewModel,
                onBackClick = { navToProfileScreen() },
                daneClick = { navToProfileScreen() },
            )
        }
    }
}