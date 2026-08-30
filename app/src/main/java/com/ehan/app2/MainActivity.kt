package com.ehan.app2

import android.os.Bundle
import android.content.Context

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
/**
import androidx.lifecycle.compose.collectAsStateWithLifecycle
*/
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
/**
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme*/
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import kotlinx.coroutines.launch
import com.ehan.app2.ui.theme.App2Theme
/**
import com.ehan.app2.ui.MainViewModel
*/
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            App2Theme {
                greeting(
                    onCheatDetected = {
                            Toast.makeText(this, "⚠️ Deteksi Manipulasi Memori!", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
fun greeting(
    onCheatDetected: () Unit,
    modifier: Modifier = Modifier
) {
    var _angka_1: Int by remember { mutableStateOf(10) }

    var angka_1: Int by remember { mutableStateOf(10) }

    LaunchedEffect(angka_1) {
        if (angka_1 != _angka_1) {
            // Jika Game Guardian mengubah nilaiTampilan di RAM menjadi 9999, 
            // kondisi ini akan langsung aktif.
            onCheatDetected()
            
            // Paksa nilai tampilan kembali ke nilai asli (Membalas manipulasi RAM)
            angka_1 = _angka_1
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Koin Game Anda:",
            style = MaterialTheme.typography.titleMedium
        )
        
        // Angka ini yang dicari cheater lewat Game Guardian (10 -> 17 -> 5)
        Text(
            text = "$nilaiTampilan", 
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol Sah untuk mengubah nilai dari dalam sistem game
        Button(
            onClick = {
                // Jalur resmi: Ubah nilai asli dulu, baru update tampilan
                nilaiAsli += 7 
                nilaiTampilan = nilaiAsli
            }
        ) {
            Text("Tambah Koin (+7)")
        }
    }

    /**
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        Text(text = "Angka Saat Ini " + angka_1.toString())
        Button(
            onClick = {
                angka_1++
            }
        ) {
            Text(
                text = "Tambah 1"
            )
        }
        Button(
            onClick = {
                angka_1--
            }
        ) {
            Text(
                text = "Kurang 1"
            )
        }
        Text(
            text = ""
        )
    }*/
}
/**
class UserPreferences(private val dataStore: DataStore<Preferences>) {

    companion object {
        // 2. Tentukan Key unik beserta tipe datanya (stringPreferencesKey, intPreferencesKey, dll.)
        private val ANGKA_1_KEY = intPreferencesKey("data1")
    }

    // 3. Membaca Data (Mengembalikan data dalam bentuk Flow secara reactive)
    val ANGKA_1: Flow<Int> = dataStore.data
        .map { preferences ->
            // Mengembalikan nilai tersimpan, atau string kosong "" jika null
            preferences[ANGKA_1_KEY] ?: 0
        }

    // 4. Menulis/Menyimpan Data (Wajib menggunakan fungsi suspend / di dalam Coroutine)
    suspend fun saveAngka_2(angka: Int) {
        dataStore.edit { preferences ->
            preferences[ANGKA_1_KEY] = angka
        }
    }
}*/