package com.ehan.kalkulator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ehan.kalkulator.ui.theme.ThemeMode

@Composable
fun ThemeSelectionScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    // Ambil objek AppPreferences terkini
    val prefs by viewModel.preferencesState.collectAsState()

    Column(
        modifier = modifier.fillMaxWidth()
                   .padding(16.dp)
    ) {
        Text(
            text = "Pilih Tema Aplikasi",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )

        ThemeMode.entries.forEach { mode ->
            Row(
                modifier = Modifier
                           .fillMaxWidth()
                           .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    // Bandingkan dengan prefs.themeMode
                    selected = (prefs.themeMode == mode),
                    onClick = { viewModel.setThemeMode(mode) }
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                Text(
                    text = mode.label,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}