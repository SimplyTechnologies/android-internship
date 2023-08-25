package com.simply.birthdayapp.presentation.ui.screens.main.home.birthday

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.entities.CreateBirthdayEntity
import com.simply.birthdayapp.data.repositories.BirthdayRepository
import com.simply.birthdayapp.presentation.extensions.uriToBase64
import com.simply.birthdayapp.presentation.models.RelationshipEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class BirthdayViewModel(
    application: Application,
    private val birthdayRepository: BirthdayRepository,
) : AndroidViewModel(application) {

    private val _name: MutableStateFlow<String> = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _imageUri: MutableStateFlow<Uri?> = MutableStateFlow(null)
    val imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()

    private val _relationship: MutableStateFlow<RelationshipEnum?> = MutableStateFlow(null)
    val relationship: StateFlow<RelationshipEnum?> = _relationship.asStateFlow()

    private val _dateTitle: MutableStateFlow<String> = MutableStateFlow("__.__.____")
    val dateTitle: StateFlow<String> = _dateTitle.asStateFlow()

    private val _date: MutableStateFlow<Date?> = MutableStateFlow(null)

    private val _createBirthdayError = MutableStateFlow(false)
    val createBirthdayError = _createBirthdayError.asStateFlow()

    private val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT)
    val combine = combine(
        _name,
        _relationship,
        _date,
    ) { name, relationship, date ->
        name.isNotBlank() && relationship != null && date != null
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun setName(name: String) {
        _name.update { name }
    }

    fun setImage(image: Uri?) {
        _imageUri.update { image }
    }

    fun setRelationship(relationship: RelationshipEnum?) {
        _relationship.update { relationship }
    }

    fun setDate(date: Long?) {
        _date.update { if (date != null) Date(date) else null }
        _dateTitle.update { formatter.format(Date(date ?: Calendar.getInstance().timeInMillis)) }
    }

    fun setCreateBirthdayErrorFalse() {
        _createBirthdayError.value = false
    }

    fun createBirthday() {
        viewModelScope.launch(Dispatchers.IO) {
            val imageUri = imageUri.value
            birthdayRepository.createBirthday(
                createBirthday = CreateBirthdayEntity(
                    name = _name.value,
                    imageBase64 = imageUri?.uriToBase64(context = getApplication<Application>().applicationContext),
                    relation = getApplication<Application>().applicationContext.getString(_relationship.value?.resId ?: RelationshipEnum.BEST_FRIEND.resId),
                    date = _date.value.toString(),
                ),
            ).onEach {
                it.onFailure { _createBirthdayError.update { true } }
            }.catch { _createBirthdayError.update { true } }.flowOn(Dispatchers.Main).collect()
        }
    }
}