package com.simply.birthdayapp.presentation.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthMainViewModel(private val repository: AuthRepository) : ViewModel() {

    private var _rememberPassword = MutableStateFlow(false)
    val rememberPassword = _rememberPassword.asStateFlow()
    init {
        viewModelScope.launch {
            _rememberPassword.value = repository.getRememberPassword()
        }
    }
}