package com.simply.birthdayapp.di

import com.simply.birthdayapp.presentation.ui.screens.main.home.addBirthday.AddBirthdayViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::AddBirthdayViewModel)
}