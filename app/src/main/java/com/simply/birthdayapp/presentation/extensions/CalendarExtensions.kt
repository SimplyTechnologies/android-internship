package com.simply.birthdayapp.presentation.extensions

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import com.simply.birthdayapp.R
import java.util.TimeZone

fun Context.addEventToCalendar(date: Long, name: String): Uri? {
    val values = ContentValues().apply {
        put(CalendarContract.Events.DTSTART, date)
        put(CalendarContract.Events.DTEND, date)
        put(
            CalendarContract.Events.TITLE,
            getString(
                R.string.calendar_birthday_event_title,
                name.replaceFirstChar(Char::uppercaseChar),
            ),
        )
        put(CalendarContract.Events.CALENDAR_ID, 1)
        put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
    }
    return contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
}