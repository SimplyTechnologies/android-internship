package com.simply.birthdayapp.data.mappers

import com.simply.birthdayapp.data.entities.RegisterInputEntity
import com.simply.birthdayapp.type.SignUpInput

fun RegisterInputEntity.toSineUpInput() =
    SignUpInput(
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        password = this.password
    )