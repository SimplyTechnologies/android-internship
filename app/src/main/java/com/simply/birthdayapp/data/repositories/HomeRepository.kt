package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.simply.birthdayapp.BirthdayQuery
import com.simply.birthdayapp.data.mappers.toBirthday
import com.simply.birthdayapp.presentation.models.Birthday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface HomeRepository {
    suspend fun getBirthdays(): Result<List<Birthday>>
}

class HomeRepositoryImpl(
    private val apolloClient: ApolloClient,
) : HomeRepository {
    override suspend fun getBirthdays(): Result<List<Birthday>> = withContext(Dispatchers.IO) {
        val response = try {
            apolloClient.query(BirthdayQuery()).execute()
        } catch (e: ApolloException) {
            return@withContext Result.failure(e)
        }
        if (response.hasErrors()) {
            return@withContext Result.failure(Exception(response.errors?.get(0)?.message))
        } else {
            return@withContext Result.success(response.data?.birthdays?.map { it.toBirthday() } ?: emptyList())
        }
    }
}