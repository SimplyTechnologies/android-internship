package com.simply.birthdayapp.di

import com.simply.birthdayapp.presentation.ui.screens.auth.AuthMainViewModel
import com.simply.birthdayapp.presentation.ui.screens.auth.register.RegisterViewModel
import com.simply.birthdayapp.presentation.ui.screens.auth.signIn.SignInViewModel
import com.simply.birthdayapp.presentation.ui.screens.auth.signIn.forgotpassword.ForgotPasswordViewModel
import com.simply.birthdayapp.presentation.ui.screens.main.MainViewModel
import com.simply.birthdayapp.presentation.ui.screens.main.home.HomeViewModel
import com.simply.birthdayapp.presentation.ui.screens.main.home.birthday.BirthdayViewModel
import com.simply.birthdayapp.presentation.ui.screens.main.profile.ProfileViewModel
import com.simply.birthdayapp.presentation.ui.screens.main.shops.ShopsViewModel
import com.simply.birthdayapp.presentation.ui.screens.main.shops.details.ShopDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::BirthdayViewModel)
    viewModelOf(::ShopsViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::AuthMainViewModel)
    viewModelOf(::ShopDetailsViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::MainViewModel)
}