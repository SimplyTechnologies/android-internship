package com.simply.birthdayapp.di

import com.simply.birthdayapp.presentation.ui.screens.main.home.birthday.BirthdayViewModel
import com.simply.birthdayapp.presentation.ui.screens.auth.register.RegisterViewModel
import com.simply.birthdayapp.presentation.ui.screens.main.shops.ShopsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::BirthdayViewModel)
    viewModelOf(::ShopsViewModel)
    viewModelOf(::RegisterViewModel)
}