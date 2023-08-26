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
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {

    private var _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private var _surName = MutableStateFlow("")
    val surName = _surName.asStateFlow()

    private var _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private var _repeatPassword = MutableStateFlow("")
    val repeatPassword = _repeatPassword.asStateFlow()

    private var _hasEmailError = MutableStateFlow(false)
    val hasEmailError = _hasEmailError.asStateFlow()

    private var _hasPasswordError = MutableStateFlow(false)
    val hasPasswordError = _hasPasswordError.asStateFlow()

    private var _hasRepeatPasswordError = MutableStateFlow(false)
    val hasRepeatPasswordError = _hasRepeatPasswordError.asStateFlow()

    private var _registrationSuccessState = MutableStateFlow(false)
    val registrationSuccessState = _registrationSuccessState.asStateFlow()

    private var _registeredEmail = MutableStateFlow("")
    val registeredEmail = _registeredEmail.asStateFlow()

    private var _registrationErrorState = MutableStateFlow(false)
    val registrationErrorState = _registrationErrorState.asStateFlow()

    private var _registerErrorMessage = MutableStateFlow("")
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
                    password = _password.value
                )
            ).onEach {
                it.onSuccess { userEntity ->
                    _registrationSuccessState.value = true
                    _registeredEmail.value = userEntity?.email ?: _email.value
                    clearForm()
                }.onFailure { error ->
                    _registerErrorMessage.value = error.message ?: "Error"
                    _registeredEmail.value = _email.value
                    clearForm()
                }
            }.catch {
                _registrationErrorState.value = true
            }.flowOn(Dispatchers.Main)
                .collect()
        }
    }

    fun setName(input: String) {
        _name.value = input
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