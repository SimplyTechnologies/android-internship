package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.CreateBirthdayMutation
import com.simply.birthdayapp.data.entities.CreateBirthdayEntity
import com.simply.birthdayapp.data.mappers.toCreateBirthdayInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface BirthdayRepository {
    suspend fun createBirthday(createBirthday: CreateBirthdayEntity): Flow<Result<Unit>>
}

class BirthdayRepositoryImpl(
    private val apolloClient: ApolloClient,
) : BirthdayRepository {
    override suspend fun createBirthday(createBirthday: CreateBirthdayEntity): Flow<Result<Unit>> = flow {
        val response = apolloClient.mutation(CreateBirthdayMutation(createBirthday.toCreateBirthdayInput())).execute()
        if (response.hasErrors()) {
            emit(Result.failure(Throwable(response.errors?.firstOrNull()?.message)))
        } else {
            if (response.data == null) {
                emit(Result.failure(Exception()))
            } else {
                emit(Result.success(Unit))
            }
        }
    }
}