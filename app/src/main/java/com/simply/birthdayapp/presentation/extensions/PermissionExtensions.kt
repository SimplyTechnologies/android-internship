package com.simply.birthdayapp.presentation.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.checkCalendarPermission(
    requestPermissionLauncher: ActivityResultLauncher<String>,
    onShowRationale: () -> Unit,
) {
    if (calendarPermissionGranted().not()) {
        if (shouldShowCalendarPermissionRationale()) onShowRationale()
        else requestPermissionLauncher.requestCalendarPermission()
    }
}

fun Context.calendarPermissionGranted(): Boolean =
    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) ==
            PackageManager.PERMISSION_GRANTED

fun Context.shouldShowCalendarPermissionRationale(): Boolean =
    ActivityCompat.shouldShowRequestPermissionRationale(this as Activity, Manifest.permission.WRITE_CALENDAR)

fun ActivityResultLauncher<String>.requestCalendarPermission() = launch(Manifest.permission.WRITE_CALENDAR)