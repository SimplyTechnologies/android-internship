package com.simply.birthdayapp.presentation.ui.screens.main.profile

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.getViewModel

sealed class ProfileRootScreen(val route: String) {
    data object ProfileScreen : ProfileRootScreen("profile_screen")
    data object EditAccountScreen : ProfileRootScreen("edit_account_screen")
    data object ChangePasswordScreen : ProfileRootScreen("change_password_screen")
}

@Composable
fun ProfileMainScreen(
    onSignOutClicked: () -> Unit = {},
    profileViewModel: ProfileViewModel = getViewModel(),
) {
    val nestedNavController = rememberNavController()

    fun navToChangePasswordScreen() {
        nestedNavController.navigate(ProfileRootScreen.ChangePasswordScreen.route) {
            popUpTo(ProfileRootScreen.ChangePasswordScreen.route) {
                inclusive = true
            }
        }
    }

    fun navToProfileScreen() {
        nestedNavController.navigate(ProfileRootScreen.ProfileScreen.route) {
            popUpTo(ProfileRootScreen.ProfileScreen.route) {
                inclusive = true
            }
        }
    }

    fun navToEditAccountScreen() {
        nestedNavController.navigate(ProfileRootScreen.EditAccountScreen.route) {
            profileViewModel.setName(name = profileViewModel.profile.value?.firstName.toString())
            profileViewModel.setSurName(surName = profileViewModel.profile.value?.lastName.toString())
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
                onSignOutClicked = onSignOutClicked,
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