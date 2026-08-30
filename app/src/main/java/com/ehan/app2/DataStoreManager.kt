package com.ehan.app2

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    companion object {
        val ANGKA_1_KEY = intPreferencesKey("angka_1")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
    }

    // --- READ (Flow) ---
    val angka_1: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[ANGKA_1_KEY] ?: 0 }

    val userName: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[USER_NAME_KEY] ?: "" }

    // --- WRITE ---
    suspend fun setAngka_1(enabled: Int) {
        context.dataStore.edit { preferences ->
            preferences[ANGKA_1_KEY] = enabled
        }
    }

    suspend fun setUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }
}