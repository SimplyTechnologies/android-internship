package com.simply.birthdayapp.presentation.ui.screens.main.home.details

import androidx.lifecycle.ViewModel
import com.simply.birthdayapp.presentation.models.Birthday
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BirthdayDetailsViewModel : ViewModel() {
    private var _birthday = MutableStateFlow<Birthday?>(null)
    val birthday = _birthday.asStateFlow()

    fun setBirthday(birthday: Birthday) {
        _birthday.update { birthday }
    }
}