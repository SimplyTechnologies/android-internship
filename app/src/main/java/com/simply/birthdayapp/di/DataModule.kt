package com.simply.birthdayapp.di

import com.simply.birthdayapp.data.createApolloClient
import com.simply.birthdayapp.data.repositories.AddBirthdayRepository
import com.simply.birthdayapp.data.repositories.AddBirthdayRepositoryImpl
import com.simply.birthdayapp.data.repositories.ShopsRepository
import com.simply.birthdayapp.data.repositories.ShopsRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single { createApolloClient() }
    singleOf(::AddBirthdayRepositoryImpl) { bind<AddBirthdayRepository>() }
    singleOf(::ShopsRepositoryImpl) { bind<ShopsRepository>() }
}