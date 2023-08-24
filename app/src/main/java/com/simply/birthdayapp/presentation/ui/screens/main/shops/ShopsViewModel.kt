package com.simply.birthdayapp.presentation.ui.screens.main.shops

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.repositories.ShopsRepository
import com.simply.birthdayapp.presentation.models.Shop
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class ShopsViewModel(
    private val shopsRepository: ShopsRepository,
) : ViewModel() {
    private val ioCatchingCoroutineContext: CoroutineContext = Dispatchers.IO + CoroutineExceptionHandler { _, cause ->
        Log.d(this::class.java.simpleName, Log.getStackTraceString(cause))
    }

    private var _cachedShops: MutableList<Shop> = mutableListOf()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _shops: MutableStateFlow<List<Shop>> = MutableStateFlow(emptyList())
    val shops: StateFlow<List<Shop>> = _shops.asStateFlow()

    private val _scrollPosition: MutableStateFlow<Int> = MutableStateFlow(0)
    val scrollPosition: StateFlow<Int> = _scrollPosition.asStateFlow()

    private val _searchBarQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchBarQuery: StateFlow<String> = _searchBarQuery.asStateFlow()

    private val _lastFavouredShopName: MutableStateFlow<String?> = MutableStateFlow(null)
    val lastFavouredShopName: StateFlow<String?> = _lastFavouredShopName.asStateFlow()

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
        viewModelScope.launch(ioCatchingCoroutineContext) {
            _cachedShops.indexOfFirst { it.id == shop.id }.takeIf { it != -1 }?.let {
                _cachedShops[it] = _cachedShops[it].copy(isLoadingFavourite = true)
                filterShops()
            }
            try {
                if (shop.isFavourite) shopsRepository.removeShopFromFavourites(shop.id)
                else shopsRepository.addShopToFavourites(shop.id)
                _cachedShops.indexOfFirst { it.id == shop.id }.takeIf { it != -1 }?.let {
                    _cachedShops[it] = _cachedShops[it].copy(isFavourite = shop.isFavourite.not())
                    filterShops()
                    if (shop.isFavourite.not()) _lastFavouredShopName.update { shop.name }
                }
            } finally {
                _cachedShops.indexOfFirst { it.id == shop.id }.takeIf { it != -1 }?.let {
                    _cachedShops[it] = _cachedShops[it].copy(isLoadingFavourite = false)
                    filterShops()
                }
            }
        }
    }

    fun clearLastFavouredShopName() {
        _lastFavouredShopName.update { null }
    }

    fun onPullRefresh() {
        fetchShops()
    }

    private fun observeSearchBarQuery() {
        _searchBarQuery
            .debounce(300L)
            .distinctUntilChanged()
            .onEach { filterShops() }
            .launchIn(viewModelScope)
    }

    private fun fetchShops() {
        viewModelScope.launch(ioCatchingCoroutineContext) {
            _loading.update { true }
            try {
                _cachedShops = shopsRepository.getShops().toMutableList()
                filterShops()
            } finally {
                _loading.update { false }
            }
        }
    }

    private fun filterShops() {
        viewModelScope.launch(ioCatchingCoroutineContext) {
            _shops.update {
                _cachedShops.filter { it.name.lowercase().startsWith(_searchBarQuery.value.lowercase()) }
            }
        }
    }
}