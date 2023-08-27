package com.simply.birthdayapp.data.mapper

import com.simply.birthdayapp.LoginMutation
import com.simply.birthdayapp.data.entity.LoginAccessTokenEntity

fun LoginMutation.Login.toLoginAccessToken(): LoginAccessTokenEntity {
    return LoginAccessTokenEntity(accessToken = this.access_token)
}