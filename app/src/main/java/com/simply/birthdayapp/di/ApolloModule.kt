package com.simply.birthdayapp.di

import com.simply.birthdayapp.data.createApolloClient
import org.koin.dsl.module

val apolloModule = module {
    single { createApolloClient() }
}