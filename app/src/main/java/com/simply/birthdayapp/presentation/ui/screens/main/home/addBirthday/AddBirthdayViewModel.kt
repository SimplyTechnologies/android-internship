package com.simply.birthdayapp.presentation.ui.screens.main.home.addBirthday

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.entities.CreateBirthdayEntity
import com.simply.birthdayapp.data.repositories.AddBirthdayRepository
import com.simply.birthdayapp.presentation.extensions.uriToBase64
import com.simply.birthdayapp.presentation.models.RelationshipEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddBirthdayViewModel(
    private val addBirthdayRepository: AddBirthdayRepository,
) : ViewModel() {

    private val _name: MutableStateFlow<String> = MutableStateFlow("")
    private val _imageUri: MutableStateFlow<Uri?> = MutableStateFlow(null)
    private val _relationship: MutableStateFlow<RelationshipEnum?> = MutableStateFlow(null)
    private val _dateTitle: MutableStateFlow<String> = MutableStateFlow("__.__.____")
    private val _date: MutableStateFlow<Date?> = MutableStateFlow(null)

    val name: StateFlow<String> = _name.asStateFlow()
    val imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()
    val relationship: StateFlow<RelationshipEnum?> = _relationship.asStateFlow()
    val dateTitle: StateFlow<String> = _dateTitle.asStateFlow()

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

    fun createBirthday(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val imageUri = imageUri.value
            addBirthdayRepository.createBirthday(
                createBirthday = CreateBirthdayEntity(
                    name = _name.value,
                    imageBase64 = imageUri?.uriToBase64(context),
                    relation = _relationship.value?.value ?: RelationshipEnum.BEST_FRIEND.value,
                    date = _date.value.toString(),
                )
            )
        }
    }
}