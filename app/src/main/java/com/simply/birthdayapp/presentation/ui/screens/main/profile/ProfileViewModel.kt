package com.simply.birthdayapp.presentation.ui.screens.main.profile

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.entities.UpdateProfileEntity
import com.simply.birthdayapp.data.repositories.ProfileRepository
import com.simply.birthdayapp.presentation.extensions.uriToBase64
import com.simply.birthdayapp.presentation.models.Profile
import com.simply.birthdayapp.presentation.ui.extenstions.isPasswordValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository,
    application: Application,
) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile = _profile.asStateFlow()

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword = _repeatPassword.asStateFlow()

    private val _hasPasswordError = MutableStateFlow(false)
    val hasPasswordError = _hasPasswordError.asStateFlow()

    private val _hasOldPasswordError = MutableStateFlow(false)
    val hasOldPasswordError = _hasOldPasswordError.asStateFlow()

    private val _hasRepeatPasswordError = MutableStateFlow(false)
    val hasRepeatPasswordError = _hasRepeatPasswordError.asStateFlow()

    private val _changePasswordSuccess = MutableStateFlow(false)
    val changePasswordSuccess = _changePasswordSuccess.asStateFlow()

    private val _changePasswordErrorMessage = MutableStateFlow("")
    val changePasswordErrorMessage = _changePasswordErrorMessage.asStateFlow()

    private val _changePasswordErrorState = MutableStateFlow(false)
    val changePasswordErrorState = _changePasswordErrorState.asStateFlow()

    private val _updateProfileSuccess = MutableStateFlow(false)
    val updateProfileSuccess = _updateProfileSuccess.asStateFlow()

    private val _updateProfileErrorMessage = MutableStateFlow("")
    val updateProfileErrorMessage = _updateProfileErrorMessage.asStateFlow()

    private val _updateProfileErrorState = MutableStateFlow(false)
    val updateProfileErrorState = _updateProfileErrorState.asStateFlow()

    private val _getUserErrorMessage = MutableStateFlow("")
    val getUserErrorMessage = _getUserErrorMessage.asStateFlow()

    private val _getUserErrorState = MutableStateFlow(false)
    val getUserErrorState = _getUserErrorState.asStateFlow()

    private val _imageUri: MutableStateFlow<Uri?> = MutableStateFlow(null)
    val imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _oldPassword = MutableStateFlow("")
    val oldPassword = _oldPassword.asStateFlow()

    private val _isOnLoadingState = MutableStateFlow(false)
    val isOnLoadingState = _isOnLoadingState.asStateFlow()

    private val _name = MutableStateFlow(_profile.value?.firstName ?: "")
    val name = _name.asStateFlow()

    private val _surName = MutableStateFlow(_profile.value?.lastName ?: "")
    val surName = _surName.asStateFlow()

    val enableEditAccountDoneButton = combine(
        _name,
        _surName,
    ) { name, surname ->
        name.isNotEmpty()
                && surname.isNotEmpty()
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), false
    )

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.getUser()
                .onEach {
                    it.onSuccess { profile ->
                        _profile.value = profile
                        _name.update { profile?.firstName.toString() }
                        _surName.update { profile?.lastName.toString() }
                    }.onFailure { error ->
                        _getUserErrorMessage.value = error.message ?: "Error"
                    }
                }.onStart {
                    _isOnLoadingState.value = true
                }.onCompletion {
                    _isOnLoadingState.value = false
                }.catch {
                    _getUserErrorState.value = true
                }.collect()
        }
    }

    fun changePassword() {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.changePassword(
                oldPassword = _oldPassword.value,
                newPassword = _password.value
            ).onEach {
                it.onSuccess { _ ->
                    signOut()
                    clearForm()
                    _changePasswordSuccess.value = true
                }.onFailure { error ->
                    _changePasswordErrorMessage.value = error.message ?: "Error"
                }
            }.catch {
                _changePasswordErrorState.value = true
            }.onStart {
                _isOnLoadingState.value = true
            }.onCompletion {
                _isOnLoadingState.value = false
            }.collect()
        }
    }

    fun updateProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val imageUri = imageUri.value
            profileRepository.updateProfile(
                UpdateProfileEntity(
                    firstName = _name.value,
                    lastName = _surName.value,
                    image = imageUri?.uriToBase64(context = context),
                )
            ).onEach {
                it.onSuccess { _ ->
                    _updateProfileSuccess.value = true
                }.onFailure { error ->
                    _updateProfileErrorMessage.value = error.message ?: "Error"
                }
            }.catch {
                _updateProfileErrorState.value = true
            }.onStart {
                _isOnLoadingState.value = true
            }.onCompletion {
                _isOnLoadingState.value = false
                getUser()
            }.collect()
        }
    }

    val enableDoneButton = combine(
        _hasPasswordError,
        _hasRepeatPasswordError,
    ) { passwordError, repeatPasswordError ->
        _password.value.isNotEmpty()
                && _repeatPassword.value.isNotEmpty()
                && !passwordError && !repeatPasswordError
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), false
    )

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.signOut()
        }
    }

    fun changePasswordErrorState() {
        _changePasswordErrorState.value = false
    }

    fun editAccountErrorStare() {
        _updateProfileErrorState.value = false
    }

    fun getAccountErrorStare() {
        _getUserErrorState.value = false
    }

    fun setPassword(password: String) {
        _password.value = password
        _hasPasswordError.value = !password.isPasswordValid()
    }

    fun setOldPassword(oldPassword: String) {
        _oldPassword.value = oldPassword
        _hasOldPasswordError.value = !oldPassword.isPasswordValid()
        _hasRepeatPasswordError.value = _repeatPassword.value != _password.value
    }

    fun setRepeatPassword(repeatPassword: String) {
        _repeatPassword.value = repeatPassword
        _hasRepeatPasswordError.value = _repeatPassword.value != _password.value
    }

    fun changePasswordSuccessState() {
        _changePasswordSuccess.value = false
    }

    fun updateProfileSuccessState() {
        _updateProfileSuccess.value = false
    }

    fun changePasswordErrorMessage() {
        _changePasswordErrorMessage.value = ""
    }

    fun updateProfileErrorMessage() {
        _updateProfileErrorMessage.value = ""
    }

    fun getUserErrorMessage() {
        _getUserErrorMessage.value = ""
    }

    fun setImage(image: Uri?) {
        _imageUri.update { image }
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setSurName(surName: String) {
        _surName.value = surName
    }

    fun clearForm() {
        _oldPassword.value = ""
        _password.value = ""
        _repeatPassword.value = ""
        _hasOldPasswordError.value = false
        _hasPasswordError.value = false
        _hasRepeatPasswordError.value = false
    }

    fun resetFormEdit() {
        _name.update { profile.value?.firstName ?: "" }
        _surName.update { profile.value?.lastName ?: "" }
        _imageUri.value = null
    }
}