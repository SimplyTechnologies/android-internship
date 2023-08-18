package com.simply.birthdayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.repositories.ShopsRepository
import com.simply.birthdayapp.presentation.ui.models.Shop
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ShopsScreenUiState(
    val shopNameQuery: String = "",
    val loadingShops: Boolean = false,
    val filteredShops: List<Shop> = emptyList(),
)

@OptIn(FlowPreview::class)
class ShopsViewModel(
    private val shopsRepository: ShopsRepository,
) : ViewModel() {
    private val _cachedShops: MutableList<Shop> = mutableListOf()

    private val _shopNameSearchQuery: MutableStateFlow<String> = MutableStateFlow("")
    private val _filteredShops: MutableStateFlow<List<Shop>> = MutableStateFlow(emptyList())
    private val _loadingShops: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val ioCatchingCoroutineContext: CoroutineContext = Dispatchers.IO + CoroutineExceptionHandler { _, cause ->
        Log.d(this::class.java.simpleName, Log.getStackTraceString(cause))
    }

    val shopsScreenUiState =
        combine(
            _shopNameSearchQuery,
            _loadingShops,
            _filteredShops
        ) { shopNameSearchQuery, loadingShops, filteredShops ->
            ShopsScreenUiState(
                shopNameQuery = shopNameSearchQuery,
                loadingShops = loadingShops,
                filteredShops = filteredShops,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ShopsScreenUiState(),
        )

    init {
        cacheShops()
    }

    fun onSearchShopsByName(shopNameSearchQuery: String) {
        _shopNameSearchQuery.update { shopNameSearchQuery }
    }

    private fun cacheShops() = viewModelScope.launch(ioCatchingCoroutineContext) {
        _loadingShops.update { true }
        try {
            if (_cachedShops.isNotEmpty()) return@launch
            val shops = shopsRepository.getShops()
            _cachedShops += shops
            observeShopNameSearchQuery()
        } finally {
            _loadingShops.update { false }
        }
    }

    private fun observeShopNameSearchQuery() {
        _shopNameSearchQuery
            .debounce(300)
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .onEach { shopNameSearchQuery ->
                try {
                    filterShopsByNamePrefix(shopNameSearchQuery)
                } catch (cause: NumberFormatException) {
                    Log.d(this::class.java.simpleName, Log.getStackTraceString(cause))
                }
            }
            .catch { cause -> Log.d(this::class.java.simpleName, Log.getStackTraceString(cause)) }
            .launchIn(viewModelScope)
    }

    private fun filterShopsByNamePrefix(namePrefix: String) {
        viewModelScope.launch(ioCatchingCoroutineContext) {
            val filteredShops = _cachedShops.filter { shop -> shop.name.lowercase().startsWith(namePrefix.lowercase()) }
            _filteredShops.update { filteredShops }
        }
    }
}