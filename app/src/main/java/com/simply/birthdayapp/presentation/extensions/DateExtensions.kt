package com.simply.birthdayapp.presentation.extensions

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun Long.fromMillisToUtcDate(): String = utcDateFormatter.format(Instant.ofEpochMilli(this))

fun String.fromUtcToMillisDate(): Long = Instant.from(utcDateFormatter.parse(this)).toEpochMilli()

fun String.fromUtcToDayMonthYearDate(): String = dayMonthYearDateFormatter.format(utcDateFormatter.parse(this))

private const val PATTERN_DATE_UTC: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val PATTERN_DATE_DAY_MONTH_YEAR: String = "dd.MM.yyyy"

private val utcDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE_UTC).withZone(ZoneOffset.UTC)
private val dayMonthYearDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE_DAY_MONTH_YEAR)
