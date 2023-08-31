package com.simply.birthdayapp.presentation.ui.screens.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    private val _bottomNavBarVisible: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val bottomNavBarVisible: StateFlow<Boolean> = _bottomNavBarVisible.asStateFlow()

    fun showBottomNavBar() {
        _bottomNavBarVisible.update { true }
    }

    fun hideBottomNavBar() {
        _bottomNavBarVisible.update { false }
    }
}