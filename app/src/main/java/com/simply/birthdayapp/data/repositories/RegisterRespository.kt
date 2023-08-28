package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.SignUpMutation
import com.simply.birthdayapp.data.entity.RegisterInputEntity
import com.simply.birthdayapp.data.localdatastore.DataStoreManager
import com.simply.birthdayapp.data.mappers.toRegisteredUser
import com.simply.birthdayapp.data.mappers.toSineUpInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface RegisterRepository {
    suspend fun createAccount(registerInputEntity: RegisterInputEntity): Flow<Result<Unit>>
    suspend fun setEmail(email: String)
}

class RegisterRepositoryImpl(
    private val dataStoreManager: DataStoreManager,
    private val apolloClient: ApolloClient
) : RegisterRepository {
    override suspend fun createAccount(registerInputEntity: RegisterInputEntity) = flow {
        val input = registerInputEntity.toSineUpInput()
        val result = apolloClient
            .mutation(SignUpMutation(input))
            .execute()
        if (result.hasErrors()) {
            val errorMessage = result.errors?.firstOrNull()?.message
            emit(Result.failure(Throwable(errorMessage)))
        } else {
            setEmail(result.data?.signUp?.toRegisteredUser()?.email ?: "")
            emit(Result.success(Unit))
        }
    }

    override suspend fun setEmail(email: String) {
        dataStoreManager.setUserEmail(email)
    }
}