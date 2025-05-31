package com.mungkive.application.core

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class TokenManager(private val context: Context) {
    companion object {
        private val KEY_ACCESS = stringPreferencesKey("access_token")
        private val KEY_ID = stringPreferencesKey("saved_id")
        private val KEY_PASSWORD = stringPreferencesKey("saved_password")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { it[KEY_ACCESS] = token }
    }

    suspend fun clearToken() {
        context.dataStore.edit { it.remove(KEY_ACCESS) }
    }

    suspend fun getToken(): String? = context.dataStore.data.first()[KEY_ACCESS]

    suspend fun saveCredentials(id: String, password: String) = context.dataStore.edit {
        it[KEY_ID] = id
        it[KEY_PASSWORD] = password
    }

    suspend fun getCredentials(): Pair<String, String>? =
        context.dataStore.data.first().let { prefs ->
            val id = prefs[KEY_ID]
            val password = prefs[KEY_PASSWORD]
            if (id != null && password != null) {
                id to password
            } else {
                null
            }
        }

    suspend fun clearAll() = context.dataStore.edit { it.clear() }
}
