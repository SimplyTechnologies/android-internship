package com.simply.birthdayapp.presentation.ui.screens.main.shops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.repositories.ShopsRepository
import com.simply.birthdayapp.presentation.models.Shop
import com.simply.birthdayapp.presentation.models.ShopsError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class ShopsViewModel(
    private val shopsRepository: ShopsRepository,
) : ViewModel() {
    private var _cachedShops: MutableList<Shop> = mutableListOf()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _shops: MutableStateFlow<List<Shop>> = MutableStateFlow(emptyList())
    val shops: StateFlow<List<Shop>> = _shops.asStateFlow()

    private val _scrollPosition: MutableStateFlow<Int> = MutableStateFlow(0)
    val scrollPosition: StateFlow<Int> = _scrollPosition.asStateFlow()

    private val _searchBarQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchBarQuery: StateFlow<String> = _searchBarQuery.asStateFlow()

    private val _numOfShopsLoadingIsFavourite: MutableStateFlow<Int> = MutableStateFlow(0)
    val numOfShopsLoadingIsFavourite: StateFlow<Int> = _numOfShopsLoadingIsFavourite.asStateFlow()

    private val _lastFavouredShopName: MutableStateFlow<String?> = MutableStateFlow(null)
    val lastFavouredShopName: StateFlow<String?> = _lastFavouredShopName.asStateFlow()

    private val _lastShopsError: MutableStateFlow<ShopsError?> = MutableStateFlow(null)
    val lastShopsError: StateFlow<ShopsError?> = _lastShopsError.asStateFlow()

    init {
        observeSearchBarQuery()
        fetchShops()
    }

    fun onScrollPositionChange(scrollPosition: Int) {
        _scrollPosition.update { scrollPosition }
    }

    fun onSearchBarQueryChange(searchBarQuery: String) {
        _searchBarQuery.update { searchBarQuery }
    }

    fun onShopIsFavouriteChange(shop: Shop) {
        viewModelScope.launch(Dispatchers.IO) {
            val cachedShopIndex = _cachedShops.indexOfFirst { it.id == shop.id }.takeIf { it != -1 }
            setCachedShopIsLoadingFavourite(cachedShopIndex, true)
            if (shop.isFavourite) removeShopFromFavourites(shop, cachedShopIndex)
            else addShopToFavourites(shop, cachedShopIndex)
            setCachedShopIsLoadingFavourite(cachedShopIndex, false)
        }
    }

    fun clearLastFavouredShopName() {
        _lastFavouredShopName.update { null }
    }

    fun clearLastShopsError() {
        _lastShopsError.update { null }
    }

    fun onPullRefresh() {
        fetchShops()
    }

    private suspend fun removeShopFromFavourites(
        shop: Shop,
        cachedShopIndex: Int?,
    ) {
        shopsRepository.removeShopFromFavourites(shop.id)
            .onSuccess { setCachedShopIsFavourite(cachedShopIndex, false) }
            .onFailure { _lastShopsError.update { ShopsError.RemoveShopFromFavourites } }
    }

    private suspend fun addShopToFavourites(
        shop: Shop,
        cachedShopIndex: Int?,
    ) {
        shopsRepository.addShopToFavourites(shop.id)
            .onSuccess {
                setCachedShopIsFavourite(cachedShopIndex, true)
                _lastFavouredShopName.update { shop.name }
            }
            .onFailure { _lastShopsError.update { ShopsError.AddShopToFavourites } }
    }

    private fun setCachedShopIsLoadingFavourite(
        cachedShopIndex: Int?,
        isLoadingFavourite: Boolean,
    ) {
        cachedShopIndex?.let {
            _cachedShops[it] = _cachedShops[it].copy(isLoadingFavourite = isLoadingFavourite)
            filterShops()
        }
    }

    private fun setCachedShopIsFavourite(
        cachedShopIndex: Int?,
        isFavourite: Boolean,
    ) {
        cachedShopIndex?.let {
            _cachedShops[it] = _cachedShops[it].copy(isFavourite = isFavourite)
            filterShops()
        }
    }

    private fun observeSearchBarQuery() {
        _searchBarQuery
            .debounce(300L)
            .distinctUntilChanged()
            .onEach { filterShops() }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun fetchShops() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.update { true }
            shopsRepository.getShops()
                .onSuccess {
                    _cachedShops = it.toMutableList()
                    filterShops()
                }
                .onFailure { _lastShopsError.update { ShopsError.LoadShops } }
            _loading.update { false }
        }
    }

    private fun filterShops() {
        _shops.update {
            _cachedShops.filter { it.name.lowercase().startsWith(_searchBarQuery.value.lowercase()) }
        }
        _numOfShopsLoadingIsFavourite.update {
            _cachedShops.count { it.isLoadingFavourite }
        }
    }
}