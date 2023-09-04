package com.simply.birthdayapp.presentation.extensions

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import com.simply.birthdayapp.R
import com.simply.birthdayapp.presentation.models.CalendarContractItem
import java.util.Date
import java.util.TimeZone

@SuppressLint("Range")
fun Context.addEventToCalendar(email: String, date: Long, name: String): Uri? {
    val contractList = mutableListOf<CalendarContractItem>()

    contentResolver.query(
        CalendarContract.Calendars.CONTENT_URI,
        arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.OWNER_ACCOUNT,
        ),
        null,
        null,
    )?.use {
        while (it.moveToNext()) {
            contractList.add(
                CalendarContractItem(
                    calendarId = it.getString(it.getColumnIndex(CalendarContract.Calendars._ID)),
                    accountName = it.getString(it.getColumnIndex(CalendarContract.Calendars.ACCOUNT_NAME)),
                    displayName = it.getString(it.getColumnIndex(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)),
                    ownerAccount = it.getString(it.getColumnIndex(CalendarContract.Calendars.OWNER_ACCOUNT))
                )
            )
        }
    }

    val userEmailContract = contractList.find { it.ownerAccount == email }
    val id = userEmailContract?.calendarId
        ?: if (contractList.isNotEmpty()) contractList[0].calendarId else return null

    val d = Date(date)
    val values = ContentValues().apply {
        put(CalendarContract.Events.DTSTART, d.time)
        put(CalendarContract.Events.DTEND, d.time)
        put(CalendarContract.Events.ALL_DAY, true)
        put(CalendarContract.Events.RRULE, "FREQ=YEARLY")
        put(
            CalendarContract.Events.TITLE,
            getString(
                R.string.calendar_birthday_event_title,
                name.replaceFirstChar(Char::uppercaseChar),
            ),
        )
        put(CalendarContract.Events.CALENDAR_ID, id)
        put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
    }
    return contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
}