package com.simply.birthdayapp.presentation.models

data class Birthday(
    val name: String,
    val image: ByteArray?,
    val relation: String,
    val date: String,
)