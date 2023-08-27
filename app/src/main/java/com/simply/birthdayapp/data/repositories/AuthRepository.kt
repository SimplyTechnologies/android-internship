package com.simply.birthdayapp.data.repositories

import com.simply.birthdayapp.data.localdatastore.DataStoreManager
import kotlinx.coroutines.flow.first

interface AuthRepository {

    suspend fun getRememberPassword(): Boolean
}

class AuthRepositoryImpl(private val dataStoreManager: DataStoreManager) : AuthRepository {
    override suspend fun getRememberPassword(): Boolean {
        return dataStoreManager.getRememberPassword().first() &&
                dataStoreManager.getAccessToken().first().isNotEmpty()
    }
}