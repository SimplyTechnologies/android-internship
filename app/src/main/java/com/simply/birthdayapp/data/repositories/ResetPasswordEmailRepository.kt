package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.ResetPasswordEmailMutation
import com.simply.birthdayapp.data.mapper.toGetCode
import com.simply.birthdayapp.presentation.ui.models.GetCodeResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ForgotPasswordRepository {
    suspend fun resetPasswordEmail(email: String): Flow<Result<GetCodeResponseModel?>>
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
}