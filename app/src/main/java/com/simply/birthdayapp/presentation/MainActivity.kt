package com.simply.birthdayapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.simply.birthdayapp.presentation.navigation.RootNavigation
import com.simply.birthdayapp.presentation.ui.theme.BirthdayAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            BirthdayAppTheme {
                RootNavigation()
            }
        }
    }
}