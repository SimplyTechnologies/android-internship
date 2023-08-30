package com.simply.birthdayapp.presentation.models

data class Profile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val image: ByteArray?,
    val email: String,
)