package com.simply.birthdayapp.presentation.ui.screens.auth.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.entity.LoginInputEntity
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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SignInViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private var _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private var _loginErrorState = MutableStateFlow(false)
    val loginErrorState = _loginErrorState.asStateFlow()

    private var _loginSuccessState = MutableStateFlow(false)
    val loginSuccessState = _loginSuccessState.asStateFlow()

    private var _loginErrorMessage = MutableStateFlow("")
    val loginErrorMessage = _loginErrorMessage.asStateFlow()

    private var _hasPasswordError = MutableStateFlow(false)
    val hasPasswordError = _hasPasswordError.asStateFlow()

    private var _hasEmailError = MutableStateFlow(false)
    val hasEmailError = _hasEmailError.asStateFlow()

    private var _checkedState = MutableStateFlow(false)
    val checkedState = _checkedState.asStateFlow()


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
                    password = _password.value
                )
            ).onEach {
                it.onSuccess {
                    _loginSuccessState.value = true
                }.onFailure { error ->
                    _loginErrorMessage.value = error.message ?: "Error"
                }
                clearForm()
            }.catch {
                _loginErrorState.value = true
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