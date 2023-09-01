package com.simply.birthdayapp.presentation.ui.screens.main.shops

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simply.birthdayapp.presentation.ui.screens.main.shops.details.ShopDetailsScreen
import com.simply.birthdayapp.presentation.ui.screens.main.shops.details.ShopDetailsViewModel
import org.koin.androidx.compose.getViewModel

sealed class ShopsDestination(val route: String) {
    data object ShopsScreen : ShopsDestination("shops-screen")
    data object ShopDetailsScreen : ShopsDestination("shop-details-screen")
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShopsMainScreen(
    shopsViewModel: ShopsViewModel,
    shopDetailsViewModel: ShopDetailsViewModel = getViewModel(),
) {
    val shopsNavController = rememberNavController()
    val keyboardController = LocalSoftwareKeyboardController.current

    NavHost(
        navController = shopsNavController,
        startDestination = ShopsDestination.ShopsScreen.route,
    ) {
        composable(ShopsDestination.ShopsScreen.route) {
            ShopsScreen(
                shopsViewModel = shopsViewModel,
                onShopClick = { shop ->
                    shopDetailsViewModel.setLastClickedShop(shop)
                    keyboardController?.hide()
                    shopsNavController.navigate(ShopsDestination.ShopDetailsScreen.route)
                },
            )
        }

        composable(ShopsDestination.ShopDetailsScreen.route) {
            ShopDetailsScreen(
                shopDetailsViewModel = shopDetailsViewModel,
                navBack = { shopsNavController.navigateUp() },
            )
        }
    }
}