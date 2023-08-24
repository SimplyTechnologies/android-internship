package com.simply.birthdayapp.presentation.ui.screens.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.repositories.HomeRepository
import com.simply.birthdayapp.presentation.models.Birthday
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val _birthdayList: MutableStateFlow<List<Birthday>> = MutableStateFlow(emptyList())
    val birthdayList: StateFlow<List<Birthday>> = _birthdayList.asStateFlow()

    private val _scrollPosition: MutableStateFlow<Int> = MutableStateFlow(0)
    val scrollPosition: StateFlow<Int> = _scrollPosition.asStateFlow()

    private val _errorState = MutableStateFlow(false)
    val errorState = _errorState.asStateFlow()

    init {
        fetchBirthdays()
    }

    fun setScrollPosition(scrollPosition: Int) {
        _scrollPosition.update { scrollPosition }
    }

    fun setErrorStateFalse() {
        _errorState.value = false
    }

    private fun fetchBirthdays() {
        viewModelScope.launch {
            homeRepository.getBirthdays()
                .onFailure { exception ->
                    exception.message
                    _errorState.value = true
                }
                .onSuccess { birthdays -> _birthdayList.update { birthdays } }
        }
    }
}