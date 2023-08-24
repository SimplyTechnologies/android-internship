package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.AddShopToFavouriteMutation
import com.simply.birthdayapp.RemoveShopFromFavouriteMutation
import com.simply.birthdayapp.ShopsQuery
import com.simply.birthdayapp.data.mappers.toShop
import com.simply.birthdayapp.presentation.models.Shop
import com.simply.birthdayapp.type.ShopFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ShopsRepository {
    suspend fun getShops(): List<Shop>
    suspend fun addShopToFavourites(shopId: Int): Int
    suspend fun removeShopFromFavourites(shopId: Int): Int
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

    override suspend fun addShopToFavourites(shopId: Int): Int = withContext(Dispatchers.IO) {
        apolloClient
            .mutation(AddShopToFavouriteMutation(shopId))
            .execute()
            .data
            ?.addShopToFavorite
            ?.shopId
            ?: throw FailedToAddShopToFavourites()
    }

    override suspend fun removeShopFromFavourites(shopId: Int): Int = withContext(Dispatchers.IO) {
        apolloClient
            .mutation(RemoveShopFromFavouriteMutation(shopId))
            .execute()
            .data
            ?.removeShopFromFavorite
            ?.shopId
            ?: throw FailedToRemoveShopFromFavourites()
    }
}

class FailedToAddShopToFavourites : RuntimeException()
class FailedToRemoveShopFromFavourites : RuntimeException()