package com.simply.birthdayapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.simply.birthdayapp.presentation.navigation.TopLevelNavigation
import com.simply.birthdayapp.presentation.ui.theme.BirthdayAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BirthdayAppTheme {
                TopLevelNavigation()
            }
        }
    }
}