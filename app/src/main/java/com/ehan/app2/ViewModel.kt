package com.ehan.app2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val angka_1: StateFlow<Int> = dataStoreManager.isDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val userName: StateFlow<String> = dataStoreManager.userName
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    fun saveAngka_1(enabled: Int) {
        viewModelScope.launch {
            dataStoreManager.setAngka_1(enabled)
        }
    }

    fun saveUserName(name: String) {
        viewModelScope.launch {
            dataStoreManager.setUserName(name)
        }
    }
}
