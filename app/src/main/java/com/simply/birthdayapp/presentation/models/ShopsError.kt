package com.simply.birthdayapp.presentation.models

import com.simply.birthdayapp.R

sealed class ShopsError(val messageId: Int) {
    data object LoadShops : ShopsError(R.string.failed_to_load_shops)
    data object AddShopToFavourites : ShopsError(R.string.failed_to_add_shop_to_favourites)
    data object RemoveShopFromFavourites : ShopsError(R.string.failed_to_remove_shop_from_favourites)
}