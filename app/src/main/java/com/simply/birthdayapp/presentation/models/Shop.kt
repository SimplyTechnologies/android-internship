package com.simply.birthdayapp.presentation.models

data class Shop(
    val id: Int,
    val name: String,
    val image: ByteArray,
    val isFavourite: Boolean,
    val isLoadingFavourite: Boolean = false,
    val formattedPhoneNumber: String?,
    val address: String,
    val addressQuery: String,
    val website: String?,
)