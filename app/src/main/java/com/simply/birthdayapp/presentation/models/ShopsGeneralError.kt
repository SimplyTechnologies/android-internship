package com.simply.birthdayapp.presentation.models

import androidx.annotation.StringRes
import com.simply.birthdayapp.R

sealed class ShopsGeneralError(@StringRes val messageId: Int) {
    data object FailedToLoadShops : ShopsGeneralError(R.string.failed_to_load_shops)
    data object FailedToAddShopToFavourites : ShopsGeneralError(R.string.failed_to_add_shop_to_favourites)
    data object FailedToRemoveShopFromFavourites : ShopsGeneralError(R.string.failed_to_remove_shop_from_favourites)
}