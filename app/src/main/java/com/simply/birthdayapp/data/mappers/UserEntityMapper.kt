package com.simply.birthdayapp.data.mappers

import com.simply.birthdayapp.SignUpMutation
import com.simply.birthdayapp.presentation.ui.models.RegisteredUser

fun SignUpMutation.SignUp.toRegisteredUser() =
    RegisteredUser(email = this.email)