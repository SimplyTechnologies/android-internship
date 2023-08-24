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

    init {
        fetchBirthdays()
    }

    fun setScrollPosition(scrollPosition: Int) {
        _scrollPosition.update { scrollPosition }
    }

    private fun fetchBirthdays() {
        viewModelScope.launch {
            _birthdayList.update { homeRepository.getBirthdays() }
        }
    }
}