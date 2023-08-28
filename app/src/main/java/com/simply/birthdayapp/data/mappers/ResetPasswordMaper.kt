package com.simply.birthdayapp.data.mappers

import com.simply.birthdayapp.data.entity.ForgotPasswordEntity
import com.simply.birthdayapp.type.ResetPasswordInput

fun ForgotPasswordEntity.toPasswordInput() =
    ResetPasswordInput(
        hash = this.hash,
        password = this.password,
        email = this.email
    )