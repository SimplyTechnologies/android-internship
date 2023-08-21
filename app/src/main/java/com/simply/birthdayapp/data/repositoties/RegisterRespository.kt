package com.simply.birthdayapp.data.repositoties

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.SignUpMutation
import com.simply.birthdayapp.data.entity.RegisterInputEntity
import com.simply.birthdayapp.data.entity.RegisteredUserEntity
import com.simply.birthdayapp.data.mapper.toRegisteredUser
import com.simply.birthdayapp.data.mapper.toSineUpInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface RegisterRepository {
    suspend fun createAccount(registerInputEntity: RegisterInputEntity): Flow<Result<RegisteredUserEntity?>>
}

class RegisterRepositoryImpl(private val apolloClient: ApolloClient) : RegisterRepository {
    override suspend fun createAccount(registerInputEntity: RegisterInputEntity) = flow {
        val result =
            apolloClient.mutation(SignUpMutation(registerInputEntity.toSineUpInput())).execute()
        if (result.hasErrors()) {
            val errorMessage = result.errors?.get(0)?.message
            emit(Result.failure(Throwable(errorMessage)))
        }
        emit(Result.success(result.data?.signUp?.toRegisteredUser()))
    }
}