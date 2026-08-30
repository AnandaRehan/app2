package com.ehan.kalkulator.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ehan.kalkulator.Kalkulator
import com.ehan.kalkulator.data.AppPreferences
import com.ehan.kalkulator.data.ItemRepository
import com.ehan.kalkulator.data.PreferencesRepository
import com.ehan.kalkulator.ui.theme.ThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val itemRepository: ItemRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    // StateFlow sekarang memegang objek AppPreferences lengkap
    val preferencesState = preferencesRepository.appPreferences.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AppPreferences(themeMode = ThemeMode.SYSTEM) // Nilai awal default
    )

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            preferencesRepository.saveThemeMode(mode)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Kalkulator)
                MainViewModel(
                    itemRepository = application.itemRepository,
                    preferencesRepository = application.preferencesRepository
                )
            }
        }
    }
}
