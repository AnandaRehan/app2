package com.ehan.kalkulator

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStore
import com.ehan.kalkulator.data.AppDatabase
import com.ehan.kalkulator.data.ItemRepository
import com.ehan.kalkulator.data.PreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
/**
class Kalkulator : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val itemRepository: AppItemRepository by lazy { AppItemRepositoryImpl(database.appItemDao()) }
    val preferencesRepository: AppPreferencesRepository by lazy { AppPreferencesRepository() }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: Kalkulator
            private set
    }
}
*/

// Ekstensi DataStore untuk Context
val Context.dataStore by preferencesDataStore(name = "user_preferences")

class Kalkulator : Application() {
    
    // Inisialisasi database secara lazy
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    
    // Inisialisasi repositories
    val itemRepository: ItemRepository by lazy { ItemRepository(database.itemDao()) }
    val preferencesRepository: PreferencesRepository by lazy { PreferencesRepository(dataStore) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: Kalkulator
            private set
    }
}