package com.simply.birthdayapp.di

import com.simply.birthdayapp.data.createApolloClient
import com.simply.birthdayapp.data.shops.ShopsRepository
import com.simply.birthdayapp.data.shops.ShopsRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule: Module
    get() = module {
        single { createApolloClient() }
        singleOf(::ShopsRepositoryImpl) { bind<ShopsRepository>() }
    }