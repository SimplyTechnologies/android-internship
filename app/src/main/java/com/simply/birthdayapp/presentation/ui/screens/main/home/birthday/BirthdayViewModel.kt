package com.simply.birthdayapp.presentation.ui.screens.main.home.birthday

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.simply.birthdayapp.data.entities.CreateBirthdayEntity
import com.simply.birthdayapp.data.entities.UpdateBirthdayEntity
import com.simply.birthdayapp.data.repositories.BirthdayRepository
import com.simply.birthdayapp.presentation.extensions.fromMillisToUtcDate
import com.simply.birthdayapp.presentation.extensions.fromUtcToDayMonthYearDate
import com.simply.birthdayapp.presentation.extensions.uriToBase64
import com.simply.birthdayapp.presentation.models.Birthday
import com.simply.birthdayapp.presentation.models.RelationshipEnum
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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class BirthdayViewModel(
    application: Application,
    private val birthdayRepository: BirthdayRepository,
) : AndroidViewModel(application) {

    private val _name: MutableStateFlow<String> = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _imageByteArray: MutableStateFlow<ByteArray?> = MutableStateFlow(null)
    val imageByteArray: StateFlow<ByteArray?> = _imageByteArray.asStateFlow()

    private val _relationship: MutableStateFlow<RelationshipEnum?> = MutableStateFlow(null)
    val relationship: StateFlow<RelationshipEnum?> = _relationship.asStateFlow()

    private val _createBirthdayError = MutableStateFlow(false)
    val createBirthdayError = _createBirthdayError.asStateFlow()

    private val _updateBirthdayError = MutableStateFlow(false)
    val updateBirthdayError = _updateBirthdayError.asStateFlow()

    private val _deleteBirthdayError = MutableStateFlow(false)
    val deleteBirthdayError = _deleteBirthdayError.asStateFlow()

    private val _createBirthdaySuccess = MutableStateFlow(false)
    val createBirthdaySuccess = _createBirthdaySuccess.asStateFlow()

    private val _updateBirthdaySuccess = MutableStateFlow(false)
    val updateBirthdaySuccess = _updateBirthdaySuccess.asStateFlow()

    private val _deleteBirthdaySuccess = MutableStateFlow(false)
    val deleteBirthdaySuccess = _deleteBirthdaySuccess.asStateFlow()

    private val _editModeBirthday = MutableStateFlow<Birthday?>(null)
    val editModeBirthday = _editModeBirthday.asStateFlow()

    private val _addToCalendarCheck = MutableStateFlow(false)
    val addToCalendarCheck = _addToCalendarCheck.asStateFlow()

    private val _failedToAddBirthdayToCalendar = MutableStateFlow(false)
    val failedToAddBirthdayToCalendar = _failedToAddBirthdayToCalendar.asStateFlow()

    private val _dateDayMonthYear: MutableStateFlow<String> = MutableStateFlow("__.__.____")
    val dateDayMonthYear: StateFlow<String> = _dateDayMonthYear.asStateFlow()

    private val _dateUtc: MutableStateFlow<String> =
        MutableStateFlow(Calendar.getInstance().timeInMillis.fromMillisToUtcDate())
    val dateUtc: StateFlow<String> = _dateUtc.asStateFlow()


    val combine = combine(
        _name,
        _relationship,
        _dateDayMonthYear,
    ) { name, relationship, date ->
        name.isNotBlank() && relationship != null && date != "__.__.____"
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun setName(name: String) {
        _name.update { name }
    }

    fun setImage(image: ByteArray?) {
        _imageByteArray.update { image }
    }

    fun setRelationship(relationship: RelationshipEnum?) {
        _relationship.update { relationship }
    }

    fun setDate(dateUtc: String) {
        _dateUtc.update { dateUtc }
        _dateDayMonthYear.update { dateUtc.fromUtcToDayMonthYearDate() }
    }

    fun setAddToCalendarCheck(addToCalendarCheck: Boolean) {
        _addToCalendarCheck.update { addToCalendarCheck }
    }

    fun setCreateBirthdayErrorFalse() {
        _createBirthdayError.update { false }
    }

    fun setCreateBirthdaySuccessFalse() {
        _createBirthdaySuccess.update { false }
    }

    fun setUpdateBirthdaySuccessFalse() {
        _updateBirthdaySuccess.update { false }
    }

    fun setDeleteBirthdaySuccessFalse() {
        _deleteBirthdaySuccess.update { false }
    }

    fun setUpdateBirthdayErrorFalse() {
        _updateBirthdayError.update { false }
    }

    fun setDeleteBirthdayErrorFalse() {
        _deleteBirthdayError.update { false }
    }

    fun setFailedToAddBirthdayToCalendar(value: Boolean) {
        _failedToAddBirthdayToCalendar.update { value }
    }

    fun setBirthday(birthday: Birthday?) {
        _editModeBirthday.update { birthday }
        if (birthday != null) {
            setName(birthday.name)
            setImage(birthday.image)
            setRelationship(birthday.relation)
            setDate(birthday.dateUtc)
        } else {
            setName("")
            setImage(null)
            setRelationship(null)
            _dateUtc.update { Calendar.getInstance().timeInMillis.fromMillisToUtcDate() }
            _dateDayMonthYear.update { "__.__.____" }
        }
    }

    fun createBirthday(onCompletion: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val imageByteArray = imageByteArray.value
            birthdayRepository.createBirthday(
                createBirthday = CreateBirthdayEntity(
                    name = _name.value,
                    imageBase64 = imageByteArray?.uriToBase64(),
                    relation = getApplication<Application>().applicationContext.getString(
                        _relationship.value?.resId ?: RelationshipEnum.BEST_FRIEND.resId
                    ),
                    dateUtc = _dateUtc.value,
                ),
            ).onEach {
                it.onSuccess {
                    _createBirthdaySuccess.update { true }
                    _addToCalendarCheck.update { false }
                }
                it.onFailure { _createBirthdayError.update { true } }
            }.onCompletion { onCompletion() }
                .catch { _createBirthdayError.update { true } }.collect()
        }
    }

    fun updateBirthday(
        id: Int,
        onCompletion: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val imageByteArray = imageByteArray.value
            birthdayRepository.updateBirthday(
                id = id, updateBirthdayEntity = UpdateBirthdayEntity(
                    name = _name.value,
                    imageBase64 = imageByteArray?.uriToBase64(),
                    relation = getApplication<Application>().applicationContext.getString(
                        _relationship.value?.resId ?: RelationshipEnum.BEST_FRIEND.resId
                    ),
                    date = _dateUtc.value,
                )
            ).onEach {
                it.onSuccess {
                    _updateBirthdaySuccess.update { true }
                    _addToCalendarCheck.update { false }
                }
                it.onFailure { _updateBirthdayError.update { true } }
            }.onCompletion { onCompletion() }
                .catch { _updateBirthdayError.update { true } }.collect()
        }
    }

    fun deleteBirthday(
        id: Int,
        onCompletion: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            birthdayRepository.deleteBirthday(id)
                .onEach {
                    it.onSuccess { _deleteBirthdaySuccess.update { true } }
                    it.onFailure { _deleteBirthdayError.update { true } }
                }
                .onCompletion { onCompletion() }
                .catch { _deleteBirthdayError.update { true } }.collect()
        }
    }
}