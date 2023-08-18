package com.simply.birthdayapp.data.repositories

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.simply.birthdayapp.ShopsQuery
import com.simply.birthdayapp.presentation.ui.models.Shop
import com.simply.birthdayapp.type.AvgPriceFilter
import com.simply.birthdayapp.type.ShopFilter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ShopsRepository {
    suspend fun getShops(maxPrice: Int): List<Shop>
}

class ShopsRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ShopsRepository {
    override suspend fun getShops(maxPrice: Int): List<Shop> = withContext(dispatcher) {
        val filter = ShopFilter(
            avgPrice = Optional.present(
                AvgPriceFilter(
                    lte = Optional.present(maxPrice),
                    gte = Optional.present(0),
                )
            )
        )
        apolloClient
            .query(ShopsQuery(filter))
            .execute()
            .data
            ?.shops
            ?.map(ShopsQuery.Shop::toShop)
            ?: emptyList()
    }
}

private fun ShopsQuery.Shop.toShop(): Shop = Shop(
    id = id,
    name = name,
    image = image,
    avgPrice = avgPrice,
    isFavorite = isFavorite,
)