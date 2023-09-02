package com.simply.birthdayapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simply.birthdayapp.data.NetworkMonitor
import com.simply.birthdayapp.presentation.navigation.RootNavigation
import com.simply.birthdayapp.presentation.ui.components.NoNetworkConnectionDialog
import com.simply.birthdayapp.presentation.ui.theme.BirthdayAppTheme
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val networkMonitor: NetworkMonitor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            BirthdayAppTheme {
                val connectedToNetwork by networkMonitor.connected.collectAsStateWithLifecycle(
                    initialValue = networkMonitor.isCurrentlyConnected(),
                    lifecycleOwner = this,
                )
                var doNotShowNetworkDialogAnymore by rememberSaveable { mutableStateOf(false) }
                var dismissedNetworkDialogRecently by rememberSaveable { mutableStateOf(false) }
                val networkDialogDismissTimeoutMillis = 10_000L

                LaunchedEffect(dismissedNetworkDialogRecently) {
                    if (doNotShowNetworkDialogAnymore.not() && dismissedNetworkDialogRecently) {
                        delay(networkDialogDismissTimeoutMillis)
                        dismissedNetworkDialogRecently = false
                    }
                }

                if (doNotShowNetworkDialogAnymore.not() && dismissedNetworkDialogRecently.not() && connectedToNetwork.not()) {
                    NoNetworkConnectionDialog(
                        onDoNotShow = { doNotShowNetworkDialogAnymore = true },
                        onDismiss = { dismissedNetworkDialogRecently = true },
                    )
                }
                RootNavigation()
            }
        }
    }
}