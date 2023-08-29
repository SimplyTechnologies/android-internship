package com.simply.birthdayapp.data.mappers

import com.simply.birthdayapp.data.entities.LoginInputEntity
import com.simply.birthdayapp.type.LoginInput

fun LoginInputEntity.toLoginInput() =
    LoginInput(
        email = this.email,
        password = this.password
    )