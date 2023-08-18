package com.simply.birthdayapp.presentation.ui.models

data class Shop(
    val id: Int,
    val name: String,
    val image: String,
    val avgPrice: Int?,
    val isFavorite: Boolean?
)