package com.simply.birthdayapp.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn

interface NetworkMonitor {
    val connected: Flow<Boolean>
    fun isCurrentlyConnected(): Boolean
}

class NetworkMonitorImpl(context: Context) : NetworkMonitor {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val connected: Flow<Boolean> = callbackFlow {
        val request = Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
        val callback = object : NetworkCallback() {
            private val networks: MutableSet<Network> = mutableSetOf()

            override fun onAvailable(network: Network) {
                if (networks.isEmpty()) channel.trySend(true)
                networks += network
            }

            override fun onLost(network: Network) {
                networks -= network
                if (networks.isEmpty()) channel.trySend(false)
            }
        }
        connectivityManager.registerNetworkCallback(request, callback)
        channel.trySend(isCurrentlyConnected())
        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
    }
        .distinctUntilChanged()
        .conflate()
        .flowOn(Dispatchers.IO)

    override fun isCurrentlyConnected(): Boolean = connectivityManager.activeNetwork
        ?.let { connectivityManager.getNetworkCapabilities(it) }
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        ?: false
}