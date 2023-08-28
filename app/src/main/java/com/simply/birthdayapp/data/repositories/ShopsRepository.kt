package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.simply.birthdayapp.AddShopToFavouriteMutation
import com.simply.birthdayapp.RemoveShopFromFavouriteMutation
import com.simply.birthdayapp.ShopsQuery
import com.simply.birthdayapp.data.mappers.toShop
import com.simply.birthdayapp.presentation.models.Shop
import com.simply.birthdayapp.type.ShopFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ShopsRepository {
    fun getShops(): Flow<Result<List<Shop>>>
    fun addShopToFavourites(shopId: Int): Flow<Result<Int>>
    fun removeShopFromFavourites(shopId: Int): Flow<Result<Int>>
}

class ShopsRepositoryImpl(
    private val apolloClient: ApolloClient,
) : ShopsRepository {
    override fun getShops(): Flow<Result<List<Shop>>> = flow {
        val response = apolloClient.query(ShopsQuery(ShopFilter())).execute()
        if (response.hasErrors()) emit(Result.failure(Throwable()))
        else emit(Result.success(response.data?.shops?.map(ShopsQuery.Shop::toShop) ?: emptyList()))
    }

    override fun addShopToFavourites(shopId: Int): Flow<Result<Int>> = flow {
        val response = apolloClient.mutation(AddShopToFavouriteMutation(shopId)).execute()
        if (response.hasErrors()) {
            emit(Result.failure(Throwable()))
        } else {
            val responseShopId = response.data?.addShopToFavorite?.shopId
            if (responseShopId == null) emit(Result.failure(Throwable()))
            else emit(Result.success(responseShopId))
        }
    }

    override fun removeShopFromFavourites(shopId: Int): Flow<Result<Int>> = flow {
        val response = apolloClient.mutation(RemoveShopFromFavouriteMutation(shopId)).execute()
        if (response.hasErrors()) {
            emit(Result.failure(Throwable()))
        } else {
            val responseShopId = response.data?.removeShopFromFavorite?.shopId
            if (responseShopId == null) emit(Result.failure(Throwable()))
            else emit(Result.success(responseShopId))
        }
    }
}