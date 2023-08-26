package com.simply.birthdayapp.data.mappers

import com.apollographql.apollo3.api.Optional
import com.simply.birthdayapp.data.entities.CreateBirthdayEntity
import com.simply.birthdayapp.type.CreateBirthdayInput

fun CreateBirthdayEntity.toCreateBirthdayInput(): CreateBirthdayInput = CreateBirthdayInput(
    name = name,
    image = if (imageBase64 != null) Optional.present(imageBase64) else Optional.absent(),
    relation = relation,
    date = date,
)