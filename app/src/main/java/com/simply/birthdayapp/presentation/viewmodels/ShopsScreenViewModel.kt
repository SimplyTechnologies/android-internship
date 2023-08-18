package com.simply.birthdayapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.repositories.ShopsRepository
import com.simply.birthdayapp.presentation.ui.models.Shop
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class ShopsScreenViewModel(
    private val shopsRepository: ShopsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    private val _shops: MutableStateFlow<List<Shop>> = MutableStateFlow(emptyList())
    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val uiState = combine(_query, _loading, _shops) { query, loading, shops ->
        if (query.isBlank()) cleanSearch()
        ShopsScreenUiState(
            query = query,
            loading = loading,
            shops = shops,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ShopsScreenUiState(),
    )

    init {
        _query
            .debounce(300)
            .filter { query -> query.isNotBlank() }
            .distinctUntilChanged()
            .flowOn(dispatcher)
            .onEach { query ->
                try {
                    getShops(query.toInt())
                } catch (cause: NumberFormatException) {
                    Log.d(this::class.java.simpleName, Log.getStackTraceString(cause))
                }

            }
            .launchIn(viewModelScope)
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, cause ->
            Log.d(this::class.java.simpleName, Log.getStackTraceString(cause))
        }

    fun onSearch(query: String) {
        _query.update { query }
    }

    private fun getShops(maxPrice: Int) {
        viewModelScope.launch(dispatcher + coroutineExceptionHandler) {
            _loading.update { true }
            try {
                val shops = shopsRepository.getShops(maxPrice)
                _shops.update { shops }
            } finally {
                _loading.update { false }
            }
        }
    }

    private fun cleanSearch() {
        _shops.update { emptyList() }
    }
}

data class ShopsScreenUiState(
    val query: String = "",
    val loading: Boolean = false,
    val shops: List<Shop> = emptyList(),
)