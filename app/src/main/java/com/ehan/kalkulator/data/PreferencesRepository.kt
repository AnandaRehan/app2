package com.ehan.kalkulator.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ehan.kalkulator.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 1. Definisikan data class di sini
data class AppPreferences(
    val themeMode: ThemeMode
    // Anda bisa menambah pengaturan lain di sini nanti, misal: val isNotificationEnabled: Boolean
)

class PreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private companion object {
        val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    }

    // 2. Flow sekarang memancarkan objek AppPreferences secara utuh
    val appPreferences: Flow<AppPreferences> = dataStore.data.map { preferences ->
        val modeString = preferences[THEME_MODE_KEY] ?: ThemeMode.SYSTEM.name
        val themeMode = try {
            ThemeMode.valueOf(modeString)
        } catch (e: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }

        // Bungkus nilai ke dalam data class
        AppPreferences(themeMode = themeMode)
    }

    // 3. Fungsi untuk mengupdate tema saja
    suspend fun saveThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode.name
        }
    }
}
