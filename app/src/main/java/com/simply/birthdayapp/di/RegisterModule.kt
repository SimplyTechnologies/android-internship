package com.simply.birthdayapp.di

import com.simply.birthdayapp.data.repositories.RegisterRepository
import com.simply.birthdayapp.data.repositories.RegisterRepositoryImpl
import com.simply.birthdayapp.presentation.ui.screens.auth.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registerModule = module {
    single<RegisterRepository> { RegisterRepositoryImpl(get()) }
    viewModel { RegisterViewModel(get()) }
}