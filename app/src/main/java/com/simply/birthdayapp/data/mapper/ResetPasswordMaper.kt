package com.simply.birthdayapp.data.mapper

import com.simply.birthdayapp.data.entity.ForgotPasswordEntity
import com.simply.birthdayapp.type.ResetPasswordInput

fun ForgotPasswordEntity.toPasswordInput(): ResetPasswordInput {
    return ResetPasswordInput(
        hash = this.hash,
        password = this.password,
        email = this.email
    )
}