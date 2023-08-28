package com.simply.birthdayapp.data.mappers

import com.simply.birthdayapp.data.entity.LoginInputEntity
import com.simply.birthdayapp.type.LoginInput

fun LoginInputEntity.toLoginInput(): LoginInput {
    return LoginInput(
        email = this.email,
        password = this.password
    )
}