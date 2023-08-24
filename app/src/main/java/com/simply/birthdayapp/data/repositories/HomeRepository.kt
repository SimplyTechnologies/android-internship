package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.BirthdayQuery
import com.simply.birthdayapp.data.mappers.toBirthday
import com.simply.birthdayapp.presentation.models.Birthday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface HomeRepository {
    suspend fun getBirthdays(): List<Birthday>
}

class HomeRepositoryImpl(
    private val apolloClient: ApolloClient,
) : HomeRepository {
    override suspend fun getBirthdays(): List<Birthday> = withContext(Dispatchers.IO) {
        apolloClient
            .query(BirthdayQuery())
            .execute()
            .data
            ?.birthdays
            ?.map { it.toBirthday() } ?: emptyList()
    }
}