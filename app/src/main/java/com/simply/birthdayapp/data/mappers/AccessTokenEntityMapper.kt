package com.simply.birthdayapp.data.mappers

import com.simply.birthdayapp.LoginMutation
import com.simply.birthdayapp.data.entity.LoginAccessTokenEntity

fun LoginMutation.Login.toLoginAccessToken() =
    LoginAccessTokenEntity(accessToken = this.access_token)