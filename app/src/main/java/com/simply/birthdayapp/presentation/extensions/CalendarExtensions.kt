package com.simply.birthdayapp.presentation.extensions

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import com.simply.birthdayapp.R
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

fun Context.addEventToCalendar(date: Long, name: String): Uri? {
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = Date(date)

    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    val startTime = calendar.timeInMillis

    calendar[Calendar.HOUR_OF_DAY] = 23
    calendar[Calendar.MINUTE] = 59
    calendar[Calendar.SECOND] = 59
    val endTime = calendar.timeInMillis

    val values = ContentValues().apply {
        put(CalendarContract.Events.DTSTART, startTime)
        put(CalendarContract.Events.DTEND, endTime)
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