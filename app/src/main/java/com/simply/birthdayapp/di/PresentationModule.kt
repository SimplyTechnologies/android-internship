package com.simply.birthdayapp.di

import com.simply.birthdayapp.presentation.viewmodels.ShopsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

val presentationModule: Module
    get() = module {
        viewModelOf(::ShopsScreenViewModel)
    }