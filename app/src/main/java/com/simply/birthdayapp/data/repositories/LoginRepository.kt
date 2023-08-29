package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.LoginMutation
import com.simply.birthdayapp.data.entities.LoginInputEntity
import com.simply.birthdayapp.data.localdatastore.DataStoreManager
import com.simply.birthdayapp.data.mappers.toLoginAccessToken
import com.simply.birthdayapp.data.mappers.toLoginInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

interface LoginRepository {
    suspend fun signInAccount(loginInputEntity: LoginInputEntity): Flow<Result<Unit>>
    suspend fun getEmail(): String
    suspend fun setRememberPassword(hasRememberPassword: Boolean)
}

class LoginRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val dataStoreManager: DataStoreManager,
) : LoginRepository {
    override suspend fun signInAccount(loginInputEntity: LoginInputEntity) = flow {
        val input = loginInputEntity.toLoginInput()
        val result = apolloClient
            .mutation(LoginMutation(input))
            .execute()
        if (result.hasErrors()) {
            val errorMessage = result.errors?.firstOrNull()?.message
            emit(Result.failure(Throwable(errorMessage)))
        } else {
            val accessToken = result.data?.login?.toLoginAccessToken()?.accessToken
            if (!accessToken.isNullOrEmpty()) {
                dataStoreManager.setAccessToken(accessToken)
                emit(Result.success(Unit))
            }
        }
    }

    override suspend fun getEmail(): String {
        return dataStoreManager.getUserEmail().first()
    }

    override suspend fun setRememberPassword(hasRememberPassword: Boolean) {
        dataStoreManager.setRememberPassword(hasRememberPassword)
    }
}