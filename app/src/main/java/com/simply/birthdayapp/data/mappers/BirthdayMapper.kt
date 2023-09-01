package com.simply.birthdayapp.data.mappers

import android.util.Base64
import com.simply.birthdayapp.BirthdayQuery
import com.simply.birthdayapp.data.models.RelationshipDefaultEnum
import com.simply.birthdayapp.presentation.models.Birthday
import com.simply.birthdayapp.presentation.models.RelationshipEnum

fun BirthdayQuery.Birthday.toBirthday(): Birthday {
    var imageData: ByteArray? = null

    if (image != null) {
        try {
            imageData = Base64.decode(image, Base64.DEFAULT)
        } catch (cause: IllegalArgumentException) {
            cause.printStackTrace()
        }
    }

    return Birthday(
        id = id,
        name = name,
        image = imageData,
        relation = getRelationshipEnum(relation),
        dateUtc = date.toString()
    )
}

private fun getRelationshipEnum(relation: String): RelationshipEnum {
    return when (relation) {
        RelationshipDefaultEnum.BEST_FRIEND.value -> RelationshipEnum.BEST_FRIEND
        RelationshipDefaultEnum.MOTHER.value -> RelationshipEnum.MOTHER
        RelationshipDefaultEnum.FATHER.value -> RelationshipEnum.FATHER
        RelationshipDefaultEnum.GRANDMOTHER.value -> RelationshipEnum.GRANDMOTHER
        RelationshipDefaultEnum.GRANDFATHER.value -> RelationshipEnum.GRANDFATHER
        RelationshipDefaultEnum.BROTHER.value -> RelationshipEnum.BROTHER
        RelationshipDefaultEnum.SISTER.value -> RelationshipEnum.SISTER
        RelationshipDefaultEnum.UNCLE.value -> RelationshipEnum.UNCLE
        else -> RelationshipEnum.BEST_FRIEND
    }
}