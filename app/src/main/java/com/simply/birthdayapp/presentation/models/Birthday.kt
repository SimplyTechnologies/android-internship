package com.simply.birthdayapp.presentation.models

data class Birthday(
    val id: Int,
    val name: String,
    val image: ByteArray?,
    val relation: RelationshipEnum,
    val dateUtc: String,
)