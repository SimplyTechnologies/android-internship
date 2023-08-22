package com.simply.birthdayapp.di

import org.koin.dsl.module

val applicationModule = module {
    includes(
        dataModule,
        presentationModule,
    )
}