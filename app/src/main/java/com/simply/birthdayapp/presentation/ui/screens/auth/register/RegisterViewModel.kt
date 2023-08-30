package com.simply.birthdayapp.presentation.ui.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.entities.RegisterInputEntity
import com.simply.birthdayapp.data.repositories.RegisterRepository
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

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _surName = MutableStateFlow("")
    val surName = _surName.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword = _repeatPassword.asStateFlow()

    private val _hasEmailError = MutableStateFlow(false)
    val hasEmailError = _hasEmailError.asStateFlow()

    private val _hasPasswordError = MutableStateFlow(false)
    val hasPasswordError = _hasPasswordError.asStateFlow()

    private val _hasRepeatPasswordError = MutableStateFlow(false)
    val hasRepeatPasswordError = _hasRepeatPasswordError.asStateFlow()

    private val _registrationSuccessState = MutableStateFlow(false)
    val registrationSuccessState = _registrationSuccessState.asStateFlow()

    private val _isOnLoadingState = MutableStateFlow(false)
    val isOnLoadingState = _isOnLoadingState.asStateFlow()

    private val _registrationErrorState = MutableStateFlow(false)
    val registrationErrorState = _registrationErrorState.asStateFlow()

    private val _registerErrorMessage = MutableStateFlow("")
    val registerErrorMessage = _registerErrorMessage.asStateFlow()

    val enableRegisterButton = combine(
        _hasEmailError,
        _hasPasswordError,
        _hasRepeatPasswordError
    ) { emailError, passwordError, repeatPasswordError ->
        _name.value.isNotEmpty()
                && _surName.value.isNotEmpty()
                && _email.value.isNotEmpty()
                && _password.value.isNotEmpty()
                && _repeatPassword.value.isNotEmpty()
                && !emailError && !passwordError && !repeatPasswordError
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), false
    )

    fun registerAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createAccount(
                RegisterInputEntity(
                    email = _email.value,
                    firstName = _name.value,
                    lastName = _surName.value,
                    password = _password.value,
                )
            ).onEach {
                it.onSuccess { _ ->
                    _registrationSuccessState.value = true
                }.onFailure { error ->
                    _registerErrorMessage.value = error.message ?: "Error"
                }
                clearForm()
            }.catch {
                _registrationErrorState.value = true
            }.onStart {
                _isOnLoadingState.value = true
            }.onCompletion {
                _isOnLoadingState.value = false
            }.collect()
        }
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setSurName(surName: String) {
        _surName.value = surName
    }

    fun setEmail(email: String) {
        _email.value = email
        _hasEmailError.value = !email.isValidEmail()
    }

    fun setPassword(password: String) {
        _password.value = password
        _hasPasswordError.value = !password.isPasswordValid()
    }

    fun setRepeatPassword(repeatPassword: String) {
        _repeatPassword.value = repeatPassword
        _hasRepeatPasswordError.value = _repeatPassword.value != _password.value
    }

    fun resetSuccessState() {
        _registrationSuccessState.value = false
    }

    fun registerErrorState() {
        _registrationErrorState.value = false
    }

    fun resetRegisterErrorMessage() {
        _registerErrorMessage.value = ""
    }

    private fun clearForm() {
        _name.value = ""
        _surName.value = ""
        _email.value = ""
        _password.value = ""
        _repeatPassword.value = ""
    }
}