package com.simply.birthdayapp.presentation.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.calendarPermissionsGranted(): Boolean =
    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) ==
            PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) ==
            PackageManager.PERMISSION_GRANTED

fun Context.shouldShowCalendarPermissionsRationale(): Boolean =
    ActivityCompat.shouldShowRequestPermissionRationale(this as Activity, Manifest.permission.WRITE_CALENDAR) ||
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALENDAR)

fun ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>.requestCalendarPermissions() =
    launch(
        arrayOf(
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.READ_CALENDAR,
        ),
    )