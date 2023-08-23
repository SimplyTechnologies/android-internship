package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.ShopsQuery
import com.simply.birthdayapp.data.mappers.toShop
import com.simply.birthdayapp.presentation.ui.models.Shop
import com.simply.birthdayapp.type.ShopFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ShopsRepository {
    suspend fun getShops(): List<Shop>
}

class ShopsRepositoryImpl(
    private val apolloClient: ApolloClient,
) : ShopsRepository {
    override suspend fun getShops(): List<Shop> = withContext(Dispatchers.IO) {
        apolloClient
            .query(ShopsQuery(ShopFilter()))
            .execute()
            .data
            ?.shops
            ?.map(ShopsQuery.Shop::toShop)
            ?: emptyList()
    }
}