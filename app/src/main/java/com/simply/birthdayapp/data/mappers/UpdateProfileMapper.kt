package com.simply.birthdayapp.data.mappers

import com.apollographql.apollo3.api.Optional
import com.simply.birthdayapp.data.entities.UpdateProfileEntity
import com.simply.birthdayapp.type.UpdateProfileInput

fun UpdateProfileEntity.toUpdateProfile() =
    UpdateProfileInput(
        firstName = Optional.present(firstName),
        lastName = Optional.present(lastName),
        image = Optional.present(image ?: ""),
    )