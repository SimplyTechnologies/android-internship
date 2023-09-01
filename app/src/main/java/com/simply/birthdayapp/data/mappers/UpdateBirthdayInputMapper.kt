package com.simply.birthdayapp.data.mappers

import com.apollographql.apollo3.api.Optional
import com.simply.birthdayapp.data.entities.UpdateBirthdayEntity
import com.simply.birthdayapp.type.UpdateBirthdayInput

fun UpdateBirthdayEntity.toUpdateBirthdayInput() = UpdateBirthdayInput(
    name = Optional.presentIfNotNull(name),
    image = Optional.presentIfNotNull(imageBase64),
    relation = Optional.presentIfNotNull(relation),
    date = Optional.present(date),
)