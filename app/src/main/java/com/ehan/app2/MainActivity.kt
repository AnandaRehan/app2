package com.ehan.app2

import android.os.Bundle
import android.content.Context
import android.widget.Toast

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.first
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

        val dataStoreManager = DataStoreManager(this)
        setContent {
            App2Theme {
                greeting(
                    viewModel = SettingsViewModel(dataStoreManager),
                    onCheatDetected = {
                            Toast.makeText(this, "⚠️ Deteksi Manipulasi Memori!", Toast.LENGTH_SHORT).show()
                    },
                    refresh1 = {
                        Toast.makeText(this, "refresh 1", Toast.LENGTH_SHORT).show()
                    },
                    refresh2 = {
                        Toast.makeText(this, "refresh 2", Toast.LENGTH_SHORT).show()
                    },
                    refresh3 = {
                        Toast.makeText(this, "refresh 3", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}
/**
    Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show()
    var _angka_1: Int by rememberSaveable { mutableStateOf(10) }

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
            text = "$angka_1", 
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol Sah untuk mengubah nilai dari dalam sistem game
        Button(
            onClick = {
                // Jalur resmi: Ubah nilai asli dulu, baru update tampilan
                _angka_1 += 7
                angka_1 = _angka_1
                angka_1 = _angka_1
            }
        ) {
            Text("Tambah Koin (+7)")
        }
    }
*/

@Composable
fun greeting(
    //modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    onCheatDetected: () -> Unit,
    refresh1: () -> Unit,
    refresh2: () -> Unit,
    refresh3: () -> Unit
) {
    refresh1()
    val angka_1: Int by viewModel.angka_1.collectAsState()
    val userName: String by viewModel.userName.collectAsState()

  //  var angka_1: Int by rememberSaveable { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        refresh2()
        Text(text = "Angka Saat Ini $angka_1")
        Button(
            onClick = {
                viewModel.saveAngka_1(angka_1 + 1)
            }
        ) {
            refresh3()
            Text(
                text = "Tambah 1"
            )
        }
        Button(
            onClick = {
                viewModel.saveAngka_1(angka_1 - 1)
            }
        ) {
            Text(
                text = "Kurang 1"
            )
        }
        OutlinedTextField(
            value = userName,
            onValueChange = { viewModel.saveUserName(it) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Halo, ${userName.ifEmpty { "Guest" }}!",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
