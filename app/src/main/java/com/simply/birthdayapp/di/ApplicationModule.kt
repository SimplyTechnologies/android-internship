package com.simply.birthdayapp.di

import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModule: Module
    get() = module {
        includes(
            dataModule,
            presentationModule,
        )
    }