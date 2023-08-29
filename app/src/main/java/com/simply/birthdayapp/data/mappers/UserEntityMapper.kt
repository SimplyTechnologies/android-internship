package com.simply.birthdayapp.data.mappers

import com.simply.birthdayapp.SignUpMutation
import com.simply.birthdayapp.data.entities.RegisteredUserEntity

fun SignUpMutation.SignUp.toRegisteredUser(): RegisteredUserEntity {
    return RegisteredUserEntity(email = this.email)
}