package com.simply.birthdayapp.di

import com.simply.birthdayapp.data.createApolloClient
import com.simply.birthdayapp.data.localdatastore.DataStoreManager
import com.simply.birthdayapp.data.repositories.ForgotPasswordRepository
import com.simply.birthdayapp.data.repositories.ForgotPasswordRepositoryImpl
import com.simply.birthdayapp.data.repositories.AuthRepository
import com.simply.birthdayapp.data.repositories.AuthRepositoryImpl
import com.simply.birthdayapp.data.repositories.LoginRepository
import com.simply.birthdayapp.data.repositories.LoginRepositoryImpl
import com.simply.birthdayapp.data.repositories.RegisterRepository
import com.simply.birthdayapp.data.repositories.RegisterRepositoryImpl
import com.simply.birthdayapp.data.repositories.ShopsRepository
import com.simply.birthdayapp.data.repositories.ShopsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single { DataStoreManager(context = androidContext()) }
    single { createApolloClient(get()) }
    singleOf(::ShopsRepositoryImpl) { bind<ShopsRepository>() }
    singleOf(::RegisterRepositoryImpl) { bind<RegisterRepository>() }
    singleOf(::LoginRepositoryImpl) { bind<LoginRepository>() }
    singleOf(::ForgotPasswordRepositoryImpl) { bind<ForgotPasswordRepository>() }
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
}