package com.simply.birthdayapp.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.simply.birthdayapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

fun createApolloClient(): ApolloClient {
    return ApolloClient.Builder()
        .serverUrl(BuildConfig.SERVER_URL)
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor())
                .build()
        )
        .build()
}

private class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                //Todo: change access_token_value to real token
                addHeader("Authorization", "access_token_value")
            }
            .build()
        return chain.proceed(request)
    }
}