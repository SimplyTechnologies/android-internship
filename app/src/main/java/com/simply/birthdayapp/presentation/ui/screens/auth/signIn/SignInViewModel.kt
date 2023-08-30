package com.simply.birthdayapp.presentation.ui.screens.auth.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.entities.LoginInputEntity
import com.simply.birthdayapp.data.repositories.LoginRepository
import com.simply.birthdayapp.presentation.ui.extenstions.isPasswordValid
import com.simply.birthdayapp.presentation.ui.extenstions.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SignInViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _loginErrorState = MutableStateFlow(false)
    val loginErrorState = _loginErrorState.asStateFlow()

    private val _loginSuccessState = MutableStateFlow(false)
    val loginSuccessState = _loginSuccessState.asStateFlow()

    private val _loginErrorMessage = MutableStateFlow("")
    val loginErrorMessage = _loginErrorMessage.asStateFlow()

    private val _hasPasswordError = MutableStateFlow(false)
    val hasPasswordError = _hasPasswordError.asStateFlow()

    private val _hasEmailError = MutableStateFlow(false)
    val hasEmailError = _hasEmailError.asStateFlow()

    private val _checkedState = MutableStateFlow(false)
    val checkedState = _checkedState.asStateFlow()

    private val _isOnLoadingState = MutableStateFlow(false)
    val isOnLoadingState = _isOnLoadingState.asStateFlow()

    init {
        viewModelScope.launch {
            _email.value = loginRepository.getEmail()
        }
    }

    fun loginAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.signInAccount(
                LoginInputEntity(
                    email = _email.value,
                    password = _password.value,
                )
            ).onEach {
                it.onSuccess {
                    _loginSuccessState.value = true
                    clearForm()
                }.onFailure { error ->
                    _loginErrorMessage.value = error.message ?: "Error"
                }
            }.catch {
                _loginErrorState.value = true
            }.onStart {
                _isOnLoadingState.value = true
            }.onCompletion {
                _isOnLoadingState.value = false
            }.collect()
        }
    }

    val enableLoginButton = combine(
        _hasEmailError,
        _hasPasswordError,
    ) { emailError, passwordError ->
        _email.value.isNotEmpty()
                && _password.value.isNotEmpty()
                && !emailError && !passwordError
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), false
    )

    fun changeRememberPasswordState(hasRememberPassword: Boolean) {
        _checkedState.value = hasRememberPassword
        viewModelScope.launch {
            loginRepository.setRememberPassword(hasRememberPassword)
        }
    }

    fun loginSuccessState() {
        _loginSuccessState.value = false
    }

    fun loginErrorState() {
        _loginErrorState.value = false
    }

    fun resetLoginErrorMessage() {
        _loginErrorMessage.value = ""
    }

    fun setPassword(password: String) {
        _password.value = password
        _hasPasswordError.value = !password.isPasswordValid()
    }

    fun setEmail(email: String) {
        _email.value = email
        _hasEmailError.value = !email.isValidEmail()
    }

    private fun clearForm() {
        _email.value = ""
        _password.value = ""
    }
}