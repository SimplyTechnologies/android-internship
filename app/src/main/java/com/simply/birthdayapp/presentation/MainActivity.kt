package com.simply.birthdayapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.simply.birthdayapp.data.NetworkMonitor
import com.simply.birthdayapp.presentation.navigation.RootNavigation
import com.simply.birthdayapp.presentation.ui.components.NoNetworkConnectionDialog
import com.simply.birthdayapp.presentation.ui.theme.BirthdayAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val networkMonitor: NetworkMonitor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BirthdayAppTheme {
                val connectedToNetwork by networkMonitor.connected.collectAsStateWithLifecycle(
                    initialValue = networkMonitor.isCurrentlyConnected(),
                    lifecycleOwner = this,
                )
                var keepShowingNetworkDialog by rememberSaveable { mutableStateOf(true) }
                var showNetworkDialog by rememberSaveable { mutableStateOf(true) }
                val networkDialogRepeatDelayMillis = 10_000L

                LaunchedEffect(showNetworkDialog) {
                    if (keepShowingNetworkDialog && showNetworkDialog.not()) {
                        lifecycleScope.launch {
                            delay(networkDialogRepeatDelayMillis)
                            showNetworkDialog = true
                        }
                    }
                }

                if (keepShowingNetworkDialog && showNetworkDialog && connectedToNetwork.not()) {
                    NoNetworkConnectionDialog(
                        onDoNotShow = { keepShowingNetworkDialog = false },
                        onDismiss = { showNetworkDialog = false },
                    )
                }

                RootNavigation()
            }
        }
    }
}