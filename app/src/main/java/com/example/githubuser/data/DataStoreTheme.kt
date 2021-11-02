package com.example.githubuser.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreTheme(val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "THEME")

    suspend fun saveToDataStore(isNightMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[UI_MODE_KEY] = isNightMode
        }
    }

    val uiMode: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[UI_MODE_KEY] ?: false
        }

    companion object {
        private val UI_MODE_KEY = booleanPreferencesKey("ui_mode")

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: DataStoreTheme? = null

        fun getInstance(context: Context): DataStoreTheme {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStoreTheme(context)
                INSTANCE = instance
                instance
            }
        }
    }
}
