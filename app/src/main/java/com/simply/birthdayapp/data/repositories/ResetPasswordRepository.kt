package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.ResetPasswordEmailMutation
import com.simply.birthdayapp.ResetPasswordMutation
import com.simply.birthdayapp.data.entity.ForgotPasswordEntity
import com.simply.birthdayapp.data.mappers.toGetCode
import com.simply.birthdayapp.data.mappers.toPasswordInput
import com.simply.birthdayapp.presentation.ui.models.GetCodeResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ForgotPasswordRepository {
    suspend fun resetPasswordEmail(email: String): Flow<Result<GetCodeResponseModel?>>
    suspend fun forgotPassword(forgotPasswordEntity: ForgotPasswordEntity): Flow<Result<Unit>>
}

class ForgotPasswordRepositoryImpl(private val apolloClient: ApolloClient) :
    ForgotPasswordRepository {
    override suspend fun resetPasswordEmail(email: String) = flow {
        val result = apolloClient
            .mutation(ResetPasswordEmailMutation(email))
            .execute()
        if (result.hasErrors()) {
            val errorMessage = result.errors?.firstOrNull()?.message
            emit(Result.failure(Throwable(errorMessage)))
        } else
            emit(Result.success(result.data?.resetPasswordEmail?.toGetCode()))
    }

    override suspend fun forgotPassword(forgotPasswordEntity: ForgotPasswordEntity) = flow {
        val input = forgotPasswordEntity.toPasswordInput()
        val result = apolloClient
            .mutation(ResetPasswordMutation(input))
            .execute()
        if (result.hasErrors()) {
            val errorMessage = result.errors?.firstOrNull()?.message
            emit(Result.failure(Throwable(errorMessage)))
        } else {
            emit(Result.success(Unit))
        }
    }
}