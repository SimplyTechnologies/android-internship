package com.simply.birthdayapp.data.entities

data class CreateBirthdayEntity(
    val name: String,
    val imageBase64: String?,
    val relation: String,
    val dateUtc: String,
)