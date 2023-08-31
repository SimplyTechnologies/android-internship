package com.simply.birthdayapp.data.entities

data class UpdateBirthdayEntity(
    val name: String?,
    val imageBase64: String?,
    val relation: String?,
    val date: String,
)