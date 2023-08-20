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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(FlowPreview::class)
class ShopsViewModel(
    private val shopsRepository: ShopsRepository,
) : ViewModel() {
    private val ioCatchingCoroutineContext: CoroutineContext = Dispatchers.IO + CoroutineExceptionHandler { _, cause ->
        Log.d(this::class.java.simpleName, Log.getStackTraceString(cause))
    }

    private val _cachedShops: MutableList<Shop> = mutableListOf()

    private val _loadingAllShops: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _allShops: MutableStateFlow<List<Shop>> = MutableStateFlow(emptyList())
    private val _filteredShops: MutableStateFlow<List<Shop>> = MutableStateFlow(emptyList())
    private val _searchBarQuery: MutableStateFlow<String> = MutableStateFlow("")
    private val _searchBarActive: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val loadingAllShops: StateFlow<Boolean> = _loadingAllShops.asStateFlow()
    val allShops: StateFlow<List<Shop>> = _allShops.asStateFlow()
    val filteredShops: StateFlow<List<Shop>> = _filteredShops.asStateFlow()
    val searchBarQuery: StateFlow<String> = _searchBarQuery.asStateFlow()
    val searchBarActive: StateFlow<Boolean> = _searchBarActive.asStateFlow()

    init {
        observeSearchBarQuery()
        updateAllShops()
    }

    fun onSearchBarQueryChange(searchBarQuery: String) {
        _searchBarQuery.update { searchBarQuery }
    }

    fun onSearchBarActiveChange(searchBarActive: Boolean) {
        _searchBarActive.update { searchBarActive }
    }

    private fun observeSearchBarQuery() {
        _searchBarQuery
            .debounce(300)
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .onEach { searchBarQuery -> updateFilteredShops(searchBarQuery) }
            .catch { cause -> Log.d(this::class.java.simpleName, Log.getStackTraceString(cause)) }
            .launchIn(viewModelScope)
    }

    private suspend fun fetchCachedShops() = withContext(ioCatchingCoroutineContext) {
        if (_cachedShops.isNotEmpty()) return@withContext
        val shops = shopsRepository.getShops()
        _cachedShops += shops
    }

    private fun updateAllShops() {
        viewModelScope.launch(ioCatchingCoroutineContext) {
            _loadingAllShops.update { true }
            try {
                fetchCachedShops()
            } finally {
                _allShops.update { _cachedShops }
                _loadingAllShops.update { false }
            }
        }
    }

    private fun updateFilteredShops(searchBarQuery: String) {
        viewModelScope.launch(ioCatchingCoroutineContext) {
            val filteredShops = _cachedShops.filter { cachedShop ->
                searchBarQuery.isNotEmpty() &&
                        cachedShop.name.lowercase().startsWith(searchBarQuery.lowercase())
            }
            _filteredShops.update { filteredShops }
        }
    }
}