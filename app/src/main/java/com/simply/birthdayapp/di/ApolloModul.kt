package com.simply.birthdayapp.di

import com.simply.birthdayapp.data.creatApolloClient
import org.koin.dsl.module

val apolloModule = module {
    single { creatApolloClient() }
}