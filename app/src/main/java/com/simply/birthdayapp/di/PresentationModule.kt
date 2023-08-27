package com.simply.birthdayapp.di

import com.simply.birthdayapp.presentation.ui.screens.auth.AuthMainViewModel
import com.simply.birthdayapp.presentation.ui.screens.auth.register.RegisterViewModel
import com.simply.birthdayapp.presentation.ui.screens.auth.signIn.SignInViewModel
import com.simply.birthdayapp.presentation.ui.screens.auth.signIn.forgotpassword.ForgotPasswordViewModel
import com.simply.birthdayapp.presentation.ui.screens.main.shops.ShopsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::ShopsViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::AuthMainViewModel)
}