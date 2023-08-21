package com.simply.birthdayapp.presentation.ui.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.entity.RegisterInputEntity
import com.simply.birthdayapp.data.repositoties.RegisterRepository
import com.simply.birthdayapp.presentation.ui.extenstions.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
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

    private var _registrationSuccessState = MutableStateFlow(false)
    val registrationSuccessState = _registrationSuccessState.asStateFlow()

    private var _registrationErrorState = MutableStateFlow(false)
    val registrationErrorState = _registrationErrorState.asStateFlow()

    private var _registeredEmail = MutableStateFlow("")
    val registeredEmail = _registeredEmail.asStateFlow()

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
                }.onFailure {
                    _registrationErrorState.value = true
                    _registeredEmail.value = _email.value
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
    }

    fun setRepeatPassword(repeatPassword: String) {
        _repeatPassword.value = repeatPassword
    }
}