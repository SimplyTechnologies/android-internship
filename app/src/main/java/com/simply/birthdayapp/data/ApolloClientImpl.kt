package com.simply.birthdayapp.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.simply.birthdayapp.BuildConfig
import com.simply.birthdayapp.data.localdatastore.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

fun createApolloClient(dataStoreManager: DataStoreManager): ApolloClient {
    return ApolloClient.Builder()
        .serverUrl(BuildConfig.SERVER_URL)
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor(dataStoreManager))
                .build()
        )
        .build()
}

private class AuthorizationInterceptor(private val dataStoreManager: DataStoreManager) :
    Interceptor {
    val token = runBlocking { dataStoreManager.getAccessToken().first() }
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                addHeader("Authorization", "Bearer $token")
            }
            .build()
        return chain.proceed(request)
    }
}