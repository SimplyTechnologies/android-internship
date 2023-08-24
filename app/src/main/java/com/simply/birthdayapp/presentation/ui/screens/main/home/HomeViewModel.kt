package com.simply.birthdayapp.presentation.ui.screens.main.home

import androidx.lifecycle.ViewModel
import com.simply.birthdayapp.presentation.models.Birthday
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _birthdayList: MutableStateFlow<List<Birthday>> = MutableStateFlow(emptyList())
    val birthdayList: StateFlow<List<Birthday>> = _birthdayList.asStateFlow()

    private val _scrollPosition: MutableStateFlow<Int> = MutableStateFlow(0)
    val scrollPosition: StateFlow<Int> = _scrollPosition.asStateFlow()

    fun setScrollPosition(scrollPosition: Int) {
        _scrollPosition.update { scrollPosition }
    }
}