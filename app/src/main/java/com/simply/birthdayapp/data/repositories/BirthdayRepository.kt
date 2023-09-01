package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.CreateBirthdayMutation
import com.simply.birthdayapp.DeleteBirthdayMutation
import com.simply.birthdayapp.UpdateBirthdayMutation
import com.simply.birthdayapp.data.entities.CreateBirthdayEntity
import com.simply.birthdayapp.data.entities.UpdateBirthdayEntity
import com.simply.birthdayapp.data.localdatastore.DataStoreManager
import com.simply.birthdayapp.data.mappers.toCreateBirthdayInput
import com.simply.birthdayapp.data.mappers.toUpdateBirthdayInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

interface BirthdayRepository {
    suspend fun createBirthday(createBirthday: CreateBirthdayEntity): Flow<Result<Unit>>
    suspend fun updateBirthday(id: Int, updateBirthdayEntity: UpdateBirthdayEntity): Flow<Result<Unit>>
    suspend fun deleteBirthday(id: Int): Flow<Result<Unit>>
    suspend fun getEventEmail(): String
}

class BirthdayRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val dataStoreManager: DataStoreManager,
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

    override suspend fun updateBirthday(id: Int, updateBirthdayEntity: UpdateBirthdayEntity): Flow<Result<Unit>> =
        flow {
            val response =
                apolloClient.mutation(UpdateBirthdayMutation(id, updateBirthdayEntity.toUpdateBirthdayInput()))
                    .execute()
            if (response.hasErrors()) {
                emit(Result.failure(Throwable(response.errors?.firstOrNull()?.message)))
            } else {
                emit(Result.success(Unit))
            }
        }

    override suspend fun deleteBirthday(id: Int): Flow<Result<Unit>> = flow {
        val response = apolloClient.mutation(DeleteBirthdayMutation(id)).execute()
        if (response.hasErrors()) {
            emit(Result.failure(Throwable(response.errors?.firstOrNull()?.message)))
        } else {
            emit(Result.success(Unit))
        }
    }

    override suspend fun getEventEmail(): String {
        return dataStoreManager.getEventEmail().first()
    }
}