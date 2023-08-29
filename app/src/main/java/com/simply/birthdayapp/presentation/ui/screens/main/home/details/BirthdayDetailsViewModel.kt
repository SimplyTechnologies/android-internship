package com.simply.birthdayapp.presentation.ui.screens.main.home.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.models.Birthday
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BirthdayDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private var _birthday = MutableStateFlow<Birthday?>(null)
    val birthday = _birthday.asStateFlow()

    private var _birthdayMessage = MutableStateFlow(getApplication<Application>().applicationContext.getString(R.string.birthday_message))
    val birthdayMessage = _birthdayMessage.asStateFlow()

    fun setBirthday(birthday: Birthday) {
        _birthday.update { birthday }
    }

    fun setBirthdayMessage(message: String) {
        _birthdayMessage.update { message }
    }
}