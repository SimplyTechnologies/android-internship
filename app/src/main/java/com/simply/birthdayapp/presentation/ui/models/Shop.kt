package com.simply.birthdayapp.presentation.ui.models

data class Shop(
    val id: Int,
    val name: String,
    val image: ByteArray,
    val isFavorite: Boolean?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (id != (other as Shop).id) return false
        return true
    }

    override fun hashCode(): Int {
        return id
    }
}