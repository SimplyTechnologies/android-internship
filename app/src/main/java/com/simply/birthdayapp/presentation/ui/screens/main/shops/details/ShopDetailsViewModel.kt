package com.simply.birthdayapp.presentation.ui.screens.main.shops.details

import androidx.lifecycle.ViewModel
import com.simply.birthdayapp.presentation.models.Shop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShopDetailsViewModel : ViewModel() {
    private val _lastClickedShop: MutableStateFlow<Shop?> = MutableStateFlow(null)
    val lastClickedShop: StateFlow<Shop?> = _lastClickedShop.asStateFlow()

    fun setLastClickedShop(shop: Shop) {
        _lastClickedShop.update { shop }
    }
}