package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.AddShopToFavouriteMutation
import com.simply.birthdayapp.RemoveShopFromFavouriteMutation
import com.simply.birthdayapp.ShopsQuery
import com.simply.birthdayapp.data.mapper.toShop
import com.simply.birthdayapp.presentation.models.Shop
import com.simply.birthdayapp.type.ShopFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ShopsRepository {
    suspend fun getShops(): Result<List<Shop>>
    suspend fun addShopToFavourites(shopId: Int): Result<Int>
    suspend fun removeShopFromFavourites(shopId: Int): Result<Int>
}

class ShopsRepositoryImpl(
    private val apolloClient: ApolloClient,
) : ShopsRepository {
    override suspend fun getShops(): Result<List<Shop>> = withContext(Dispatchers.IO) {
        val response = try {
            apolloClient.query(ShopsQuery(ShopFilter())).execute()
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
        if (response.hasErrors()) {
            return@withContext Result.failure(Exception())
        } else {
            return@withContext Result.success(response.data?.shops?.map(ShopsQuery.Shop::toShop) ?: emptyList())
        }
    }

    override suspend fun addShopToFavourites(shopId: Int): Result<Int> = withContext(Dispatchers.IO) {
        val response = try {
            apolloClient.mutation(AddShopToFavouriteMutation(shopId)).execute()
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
        if (response.hasErrors()) {
            return@withContext Result.failure(Exception())
        } else {
            val responseShopId = response.data?.addShopToFavorite?.shopId
            if (responseShopId == null) {
                return@withContext Result.failure(Exception())
            } else {
                return@withContext Result.success(responseShopId)
            }
        }
    }

    override suspend fun removeShopFromFavourites(shopId: Int): Result<Int> = withContext(Dispatchers.IO) {
        val response = try {
            apolloClient.mutation(RemoveShopFromFavouriteMutation(shopId)).execute()
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
        if (response.hasErrors()) {
            return@withContext Result.failure(Exception())
        } else {
            val responseShopId = response.data?.removeShopFromFavorite?.shopId
            if (responseShopId == null) {
                return@withContext Result.failure(Exception())
            } else {
                return@withContext Result.success(responseShopId)
            }
        }
    }
}