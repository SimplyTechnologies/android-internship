package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.CreateBirthdayMutation
import com.simply.birthdayapp.data.entities.CreateBirthdayEntity
import com.simply.birthdayapp.data.mappers.toCreateBirthdayInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AddBirthdayRepository {
    suspend fun createBirthday(createBirthday: CreateBirthdayEntity)
}

class AddBirthdayRepositoryImpl(
    private val apolloClient: ApolloClient,
) : AddBirthdayRepository {
    override suspend fun createBirthday(createBirthday: CreateBirthdayEntity): Unit = withContext(Dispatchers.IO) {
        apolloClient.mutation(CreateBirthdayMutation(createBirthday.toCreateBirthdayInput())).execute()
    }
}