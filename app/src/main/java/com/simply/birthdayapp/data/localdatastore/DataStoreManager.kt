package com.simply.birthdayapp.data.localdatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val BIRTDAYAPP_DATASTORE = "birtdayapp_datastore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = BIRTDAYAPP_DATASTORE)

class DataStoreManager(private val context: Context) {

    companion object {
        val EMAIL = stringPreferencesKey("EMAIL")
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        val REMEMBER_PASSWORD = booleanPreferencesKey("REMEMBER_PASSWORD")
    }

    suspend fun setAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    fun getAccessToken(): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[ACCESS_TOKEN] ?: ""
            }
    }

    suspend fun setUserEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }

    fun getUserEmail(): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[EMAIL] ?: ""
            }
    }

    suspend fun setRememberPassword(hasRememberPassword: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[REMEMBER_PASSWORD] = hasRememberPassword
        }
    }

    fun getRememberPassword(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[REMEMBER_PASSWORD] ?: false
            }
    }

    suspend fun clearDataStore() = context.dataStore.edit {
        it.clear()
    }
}