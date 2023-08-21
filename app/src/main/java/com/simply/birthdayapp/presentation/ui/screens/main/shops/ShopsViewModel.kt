package com.simply.birthdayapp.presentation.ui.screens.main.shops

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.shops.ShopsRepository
import com.simply.birthdayapp.presentation.ui.models.Shop
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

    private val _cachedShops: MutableList<Shop> = mutableListOf()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _shops: MutableStateFlow<List<Shop>> = MutableStateFlow(emptyList())
    private val _scrollPosition: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _searchBarQuery: MutableStateFlow<String> = MutableStateFlow("")

    val loading: StateFlow<Boolean> = _loading.asStateFlow()
    val shops: StateFlow<List<Shop>> = _shops.asStateFlow()
    val scrollPosition: StateFlow<Int> = _scrollPosition.asStateFlow()
    val searchBarQuery: StateFlow<String> = _searchBarQuery.asStateFlow()

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

    private fun observeSearchBarQuery() {
        _searchBarQuery
            .debounce(300L)
            .distinctUntilChanged()
            .onEach { filterShops(it) }
            .launchIn(viewModelScope)
    }

    private fun fetchShops() {
        viewModelScope.launch(ioCatchingCoroutineContext) {
            _loading.update { true }
            try {
                if (_cachedShops.isEmpty()) _cachedShops.addAll(shopsRepository.getShops())
                filterShops("")
            } finally {
                _loading.update { false }
            }
        }
    }

    private fun filterShops(namePrefix: String) {
        viewModelScope.launch(ioCatchingCoroutineContext) {
            _shops.update { _cachedShops.filter { it.name.lowercase().startsWith(namePrefix.lowercase()) } }
        }
    }
}