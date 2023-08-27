package com.simply.birthdayapp.presentation.ui.screens.auth.signIn.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.repositories.ForgotPasswordRepository
import com.simply.birthdayapp.presentation.ui.extenstions.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val forgotPasswordRepository: ForgotPasswordRepository) :
    ViewModel() {

    private var _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private var _code = MutableStateFlow("")
    val code = _code.asStateFlow()

    private var _hasEmailError = MutableStateFlow(false)
    val hasEmailError = _hasEmailError.asStateFlow()

    private var _getCodeErrorState = MutableStateFlow(false)
    val getCodeErrorState = _getCodeErrorState.asStateFlow()


    private var _getCodeErrorMessage = MutableStateFlow("")
    val getCodeErrorMessage = _getCodeErrorMessage.asStateFlow()

    private var _hash = MutableStateFlow("")

    private var _hasGetCodeSuccess = MutableStateFlow(false)
    val hasGetCodeSuccess = _hasGetCodeSuccess.asStateFlow()

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
            }.flowOn(Dispatchers.Main).collect()
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
}