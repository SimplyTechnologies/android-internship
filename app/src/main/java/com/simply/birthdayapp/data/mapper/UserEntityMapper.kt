package com.simply.birthdayapp.data.mapper

import com.simply.birthdayapp.SignUpMutation
import com.simply.birthdayapp.data.entity.RegisteredUserEntity

fun SignUpMutation.SignUp.toRegisteredUser(): RegisteredUserEntity {
    return RegisteredUserEntity(email = this.email)
}