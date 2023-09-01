package com.simply.birthdayapp.data.mappers

import com.apollographql.apollo3.api.Optional
import com.simply.birthdayapp.data.entities.CreateBirthdayEntity
import com.simply.birthdayapp.type.CreateBirthdayInput

fun CreateBirthdayEntity.toCreateBirthdayInput() = CreateBirthdayInput(
    name = name,
    image = Optional.presentIfNotNull(imageBase64),
    relation = relation,
    date = dateUtc,
)