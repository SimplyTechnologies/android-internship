package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.ChangePasswordMutation
import com.simply.birthdayapp.ProfileQuery
import com.simply.birthdayapp.UpdateProfileMutation
import com.simply.birthdayapp.data.entities.UpdateProfileEntity
import com.simply.birthdayapp.data.localdatastore.DataStoreManager
import com.simply.birthdayapp.data.mappers.toProfile
import com.simply.birthdayapp.data.mappers.toUpdateProfile
import com.simply.birthdayapp.presentation.models.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ProfileRepository {
    suspend fun getUser(): Flow<Result<Profile?>>
    suspend fun updateProfile(updateProfileEntity: UpdateProfileEntity): Flow<Result<Unit>>
    suspend fun changePassword(oldPassword: String, newPassword: String): Flow<Result<Unit>>
    suspend fun signOut()
}

class ProfileRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val dataStoreManager: DataStoreManager,
) : ProfileRepository {
    override suspend fun getUser(): Flow<Result<Profile?>> = flow {
        val response = apolloClient.query(ProfileQuery()).execute()
        if (response.hasErrors()) {
            emit(Result.failure(Throwable(response.errors?.firstOrNull()?.message)))
        } else {
            emit(Result.success(response.data?.profile?.toProfile()))
        }
    }

    override suspend fun updateProfile(updateProfileEntity: UpdateProfileEntity): Flow<Result<Unit>> =
        flow {
            val input = updateProfileEntity.toUpdateProfile()
            val result = apolloClient
                .mutation(UpdateProfileMutation(input))
                .execute()
            if (result.hasErrors()) {
                val errorMessage = result.errors?.firstOrNull()?.message
                emit(Result.failure(Throwable(errorMessage)))
            } else {
                emit(Result.success(Unit))
            }
        }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Flow<Result<Unit>> = flow {
        val response =
            apolloClient.mutation(ChangePasswordMutation(oldPassword, newPassword)).execute()
        if (response.hasErrors()) {
            val errorMessage = response.errors?.firstOrNull()?.message
            emit(Result.failure(Throwable(errorMessage)))
        } else {
            emit(Result.success(Unit))
        }
    }

    override suspend fun signOut() {
        dataStoreManager.clearDataStore()
    }
}