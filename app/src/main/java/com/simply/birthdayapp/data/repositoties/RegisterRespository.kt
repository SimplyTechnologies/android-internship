package com.simply.birthdayapp.data.repositoties

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.SignUpMutation
import com.simply.birthdayapp.data.entity.RegisterInputEntity
import com.simply.birthdayapp.type.SignUpInput

interface RegisterRepository {
    suspend fun createAccount(registerInputEntity: RegisterInputEntity)
}

class RegisterRepositoryImpl(private val apolloClient: ApolloClient) : RegisterRepository {
    override suspend fun createAccount(registerInputEntity: RegisterInputEntity) {
        //TODO: create mapper
        apolloClient.mutation(
            SignUpMutation(
                SignUpInput(
                    email = registerInputEntity.email,
                    firstName = registerInputEntity.firstName,
                    lastName = registerInputEntity.lastName,
                    password = registerInputEntity.password
                )
            )
        )
    }
}