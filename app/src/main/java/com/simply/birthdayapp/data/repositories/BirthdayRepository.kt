package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.CreateBirthdayMutation
import com.simply.birthdayapp.data.entities.CreateBirthdayEntity
import com.simply.birthdayapp.data.mappers.toCreateBirthdayInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface BirthdayRepository {
    suspend fun createBirthday(createBirthday: CreateBirthdayEntity): Result<Unit>
}

class BirthdayRepositoryImpl(
    private val apolloClient: ApolloClient,
) : BirthdayRepository {
    override suspend fun createBirthday(createBirthday: CreateBirthdayEntity): Result<Unit> = withContext(Dispatchers.IO) {
        val response = try {
            apolloClient.mutation(CreateBirthdayMutation(createBirthday.toCreateBirthdayInput())).execute()
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }

        if (response.hasErrors()) {
            return@withContext Result.failure(Exception())
        } else {
            if (response.data == null) {
                return@withContext Result.failure(Exception())
            } else {
                return@withContext Result.success(Unit)
            }
        }
    }
}