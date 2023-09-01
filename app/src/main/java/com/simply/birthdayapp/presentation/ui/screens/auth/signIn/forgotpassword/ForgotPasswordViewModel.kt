package com.simply.birthdayapp.presentation.ui.screens.auth.signIn.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.entities.ForgotPasswordEntity
import com.simply.birthdayapp.data.repositories.ForgotPasswordRepository
import com.simply.birthdayapp.presentation.extensions.isPasswordValid
import com.simply.birthdayapp.presentation.extensions.isValidEmail
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

class ForgotPasswordViewModel(private val forgotPasswordRepository: ForgotPasswordRepository) :
    ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword = _repeatPassword.asStateFlow()

    private val _hasPasswordError = MutableStateFlow(false)
    val hasPasswordError = _hasPasswordError.asStateFlow()

    private val _code = MutableStateFlow("")
    val code = _code.asStateFlow()

    private val _hasEmailError = MutableStateFlow(false)
    val hasEmailError = _hasEmailError.asStateFlow()

    private val _getCodeErrorState = MutableStateFlow(false)
    val getCodeErrorState = _getCodeErrorState.asStateFlow()

    private val _resetPasswordErrorState = MutableStateFlow(false)
    val resetPasswordErrorState = _resetPasswordErrorState.asStateFlow()

    private val _resetPasswordSuccess = MutableStateFlow(false)
    val resetPasswordSuccess = _resetPasswordSuccess.asStateFlow()

    private val _resetPasswordErrorMessage = MutableStateFlow("")
    val resetPasswordErrorMessage = _resetPasswordErrorMessage.asStateFlow()

    private val _hasRepeatPasswordError = MutableStateFlow(false)
    val hasRepeatPasswordError = _hasRepeatPasswordError.asStateFlow()

    private val _isOnLoadingState = MutableStateFlow(false)
    val isOnLoadingState = _isOnLoadingState.asStateFlow()

    private val _getCodeErrorMessage = MutableStateFlow("")
    val getCodeErrorMessage = _getCodeErrorMessage.asStateFlow()

    private val _hasGetCodeSuccess = MutableStateFlow(false)
    val hasGetCodeSuccess = _hasGetCodeSuccess.asStateFlow()

    private val _hash = MutableStateFlow("")
    val enableDoneButton = combine(
        _hasPasswordError,
        _hasRepeatPasswordError
    ) { passwordError, repeatPasswordError ->
        _password.value.isNotEmpty()
                && _repeatPassword.value.isNotEmpty()
                && !passwordError && !repeatPasswordError
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), false
    )
    val enabledGetCodeButton = _email.combine(_hasEmailError) { emailValue, emailErrorValue ->
        emailValue.isNotEmpty() && !emailErrorValue
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), false
    )

    fun getCode() {
        viewModelScope.launch {
            forgotPasswordRepository.resetPasswordEmail(_email.value).onEach {
                it.onSuccess { getCodeResponse ->
                    _hash.value = getCodeResponse?.hash.toString()
                    _hasGetCodeSuccess.value = true
                }
                it.onFailure { error ->
                    _getCodeErrorMessage.value = error.message ?: "Error"
                    _hasGetCodeSuccess.value = false
                }
            }.catch {
                _getCodeErrorState.value = true
            }.onStart {
                _isOnLoadingState.value = true
            }.onCompletion {
                _isOnLoadingState.value = false
            }.collect()
        }
    }

    fun resetPassword() {
        viewModelScope.launch(Dispatchers.IO) {
            forgotPasswordRepository.forgotPassword(
                ForgotPasswordEntity(
                    hash = _code.value,
                    email = _email.value,
                    password = _password.value
                )
            ).onEach {
                it.onSuccess { _ ->
                    _resetPasswordSuccess.value = true
                }.onFailure { error ->
                    _resetPasswordErrorMessage.value = error.message ?: "Error"
                }
                clearForm()
            }.catch {
                _resetPasswordErrorState.value = true
            }.onStart {
                _isOnLoadingState.value = true
            }.onCompletion {
                _isOnLoadingState.value = false
            }.collect()
        }
    }

    fun setEmail(email: String) {
        _email.value = email
        _hasEmailError.value = !email.isValidEmail()
    }

    fun setCode(code: String) {
        _code.value = code
    }

    fun resetGetCodeErrorMessage() {
        _getCodeErrorMessage.value = ""
    }

    fun getCodeErrorState() {
        _getCodeErrorState.value = false
    }

    fun resetPasswordSuccessState() {
        _resetPasswordSuccess.value = false
    }

    fun resetPasswordErrorMessage() {
        _resetPasswordErrorMessage.value = ""
    }

    fun resetPasswordErrorState() {
        _resetPasswordErrorState.value = false
    }

    fun setPassword(password: String) {
        _password.value = password
        _hasPasswordError.value = !password.isPasswordValid()
    }

    private fun clearForm() {
        _email.value = ""
        _password.value = ""
        _repeatPassword.value = ""
        _code.value = ""
    }

    fun setRepeatPassword(repeatPassword: String) {
        _repeatPassword.value = repeatPassword
        _hasRepeatPasswordError.value = _repeatPassword.value != _password.value
    }

    fun clearPasswords() {
        _password.value = ""
        _repeatPassword.value = ""
    }
}