package com.simply.birthdayapp.presentation.ui.models

data class Shop(
    val id: Int,
    val name: String,
    val image: ByteArray,
    val isFavorite: Boolean?,
)