package com.simply.birthdayapp.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

val commonModule: Module
    get() = module {
        single { Dispatchers.IO }
    }