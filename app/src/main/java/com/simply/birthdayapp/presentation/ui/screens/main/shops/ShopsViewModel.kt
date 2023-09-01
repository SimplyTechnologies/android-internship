package com.simply.birthdayapp.presentation.ui.screens.main.shops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.repositories.ShopsRepository
import com.simply.birthdayapp.presentation.models.Shop
import com.simply.birthdayapp.presentation.models.ShopsGeneralError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private val _lastAddedToFavouritesShopId: MutableStateFlow<Int?> = MutableStateFlow(null)
    val lastAddedToFavouritesShopId: StateFlow<Int?> = _lastAddedToFavouritesShopId.asStateFlow()

    private val _lastRemovedFromFavouritesShopId: MutableStateFlow<Int?> = MutableStateFlow(null)
    val lastRemovedFromFavouritesShopId: StateFlow<Int?> = _lastRemovedFromFavouritesShopId.asStateFlow()

    private val _lastServerErrorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val lastServerErrorMessage: StateFlow<String?> = _lastServerErrorMessage.asStateFlow()

    private val _lastGeneralError: MutableStateFlow<ShopsGeneralError?> = MutableStateFlow(null)
    val lastGeneralError: StateFlow<ShopsGeneralError?> = _lastGeneralError.asStateFlow()

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
            _cachedShops.indexOfFirst { it.id == shop.id }.takeIf { it != -1 }?.let {
                if (shop.isFavourite) removeShopFromFavourites(shop, it)
                else addShopToFavourites(shop, it)
            }
        }
    }

    fun clearLastAddedToFavouritesShopId() {
        _lastAddedToFavouritesShopId.update { null }
    }

    fun clearLastRemovedFromFavouritesShopId() {
        _lastRemovedFromFavouritesShopId.update { null }
    }

    fun clearLastServerErrorMessage() {
        _lastServerErrorMessage.update { null }
    }

    fun clearLastGeneralError() {
        _lastGeneralError.update { null }
    }

    fun onPullRefresh() {
        fetchShops()
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
        shopsRepository.getShops()
            .onStart { _loading.update { true } }
            .onCompletion { _loading.update { false } }
            .onEach { result ->
                result.onSuccess {
                    _cachedShops = it.toMutableList()
                    filterShops()
                }
                result.onFailure { cause -> _lastServerErrorMessage.update { cause.message } }
            }
            .catch { _lastGeneralError.update { ShopsGeneralError.FailedToLoadShops } }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun addShopToFavourites(
        shop: Shop,
        cachedShopIndex: Int,
    ) {
        shopsRepository.addShopToFavourites(shop.id)
            .onStart { setCachedShopIsLoadingFavourite(cachedShopIndex, true) }
            .onCompletion { setCachedShopIsLoadingFavourite(cachedShopIndex, false) }
            .onEach { result ->
                result.onSuccess {
                    setCachedShopIsFavourite(cachedShopIndex, true)
                    _lastAddedToFavouritesShopId.update { shop.id }
                }
                result.onFailure { cause -> _lastServerErrorMessage.update { cause.message } }
            }
            .catch { _lastGeneralError.update { ShopsGeneralError.FailedToAddShopToFavourites } }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun removeShopFromFavourites(
        shop: Shop,
        cachedShopIndex: Int,
    ) {
        shopsRepository.removeShopFromFavourites(shop.id)
            .onStart { setCachedShopIsLoadingFavourite(cachedShopIndex, true) }
            .onCompletion { setCachedShopIsLoadingFavourite(cachedShopIndex, false) }
            .onEach { result ->
                result.onSuccess {
                    setCachedShopIsFavourite(cachedShopIndex, false)
                    _lastRemovedFromFavouritesShopId.update { shop.id }
                }
                result.onFailure { cause -> _lastServerErrorMessage.update { cause.message } }
            }
            .catch { _lastGeneralError.update { ShopsGeneralError.FailedToRemoveShopFromFavourites } }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private suspend fun setCachedShopIsLoadingFavourite(
        cachedShopIndex: Int,
        isLoadingFavourite: Boolean,
    ) = withContext(Dispatchers.Default) {
        _cachedShops[cachedShopIndex] = _cachedShops[cachedShopIndex].copy(isLoadingFavourite = isLoadingFavourite)
        filterShops()
    }

    private suspend fun setCachedShopIsFavourite(
        cachedShopIndex: Int,
        isFavourite: Boolean,
    ) = withContext(Dispatchers.Default) {
        _cachedShops[cachedShopIndex] = _cachedShops[cachedShopIndex].copy(isFavourite = isFavourite)
        filterShops()
    }

    private suspend fun filterShops() = withContext(Dispatchers.Default) {
        _numOfShopsLoadingIsFavourite.update { _cachedShops.count { it.isLoadingFavourite } }
        _shops.update {
            when {
                _searchBarQuery.value.isEmpty() -> _cachedShops
                _searchBarQuery.value.isBlank() -> emptyList()
                else -> _cachedShops.filter { it.name.lowercase().contains(_searchBarQuery.value.lowercase()) }
            }
        }
    }
}