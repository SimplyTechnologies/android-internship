package com.simply.birthdayapp.data.mappers

import com.simply.birthdayapp.LoginMutation
import com.simply.birthdayapp.data.entities.LoginAccessTokenEntity

fun LoginMutation.Login.toLoginAccessToken() =
    LoginAccessTokenEntity(accessToken = this.access_token)