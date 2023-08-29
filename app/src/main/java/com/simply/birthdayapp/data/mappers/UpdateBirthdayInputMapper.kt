package com.simply.birthdayapp.data.mappers

import com.apollographql.apollo3.api.Optional
import com.simply.birthdayapp.data.entities.UpdateBirthdayEntity
import com.simply.birthdayapp.type.UpdateBirthdayInput

fun UpdateBirthdayEntity.toUpdateBirthdayInput() = UpdateBirthdayInput(
    name = if (name != null) Optional.present(name) else Optional.absent(),
    image = if (imageBase64 != null) Optional.present(imageBase64) else Optional.absent(),
    relation = if (relation != null) Optional.present(relation) else Optional.absent(),
    date = if (date != null) Optional.present(date) else Optional.absent(),
)