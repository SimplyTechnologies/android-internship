package com.simply.birthdayapp.di

import com.simply.birthdayapp.data.ApolloClientImpl
import org.koin.dsl.module

val apolloModule = module {
    single { ApolloClientImpl.create() }
}