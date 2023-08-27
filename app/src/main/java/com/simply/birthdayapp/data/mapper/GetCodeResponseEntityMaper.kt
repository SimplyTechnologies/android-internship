package com.simply.birthdayapp.data.mapper

import com.simply.birthdayapp.ResetPasswordEmailMutation
import com.simply.birthdayapp.presentation.ui.models.GetCodeResponseModel

fun ResetPasswordEmailMutation.ResetPasswordEmail.toGetCode(): GetCodeResponseModel {
    return GetCodeResponseModel(
        email = this.email,
        hash = this.hash
    )
}