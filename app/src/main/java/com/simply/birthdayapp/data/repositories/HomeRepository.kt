package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.BirthdayQuery
import com.simply.birthdayapp.data.mappers.toBirthday
import com.simply.birthdayapp.presentation.models.Birthday
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface HomeRepository {
    suspend fun getBirthdays(): Flow<Result<List<Birthday>>>
}

class HomeRepositoryImpl(
    private val apolloClient: ApolloClient,
) : HomeRepository {
    override suspend fun getBirthdays(): Flow<Result<List<Birthday>>> = flow {
        val response = apolloClient.query(BirthdayQuery()).execute()
        if (response.hasErrors()) {
            emit(Result.failure(Throwable(response.errors?.firstOrNull()?.message)))
        } else {
            emit(Result.success(response.data?.birthdays?.map { it.toBirthday() } ?: emptyList()))
        }
    }
}