/**package com.ehan.kalkulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ehan.kalkulator.ui.navigation.AppNavigation
import com.ehan.kalkulator.ui.theme.KalkulatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val preferences by KalkulatorApplication.instance.preferencesRepository.preferences.collectAsStateWithLifecycle()

            kalkulatorTheme(
                themeMode = preferences.themeMode,
                palette = preferences.palette,
                dynamicColor = preferences.dynamicColor
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}

*/
package com.ehan.kalkulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
//import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ehan.kalkulator.ui.theme.KalkulatorTheme
import com.ehan.kalkulator.ui.theme.ThemeMode
import com.ehan.kalkulator.ui.MainViewModel
import com.ehan.kalkulator.ui.ThemeSelectionScreen

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val prefs by viewModel.preferencesState.collectAsState()
            KalkulatorTheme(
                themeMode = prefs.themeMode
            ) {
                KalkulatorScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun KalkulatorScreen(viewModel: MainViewModel) {
    var expression by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("") }
   // val preferences by Kalkulator.instance.preferencesRepository.preferences.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding() // Membatasi agar tidak tertutup tombol navigasi HP
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        // 🖥️ Layar Ekspresi & Hasil
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = if (expression.isEmpty()) "0" else expression, 
                fontSize = 28.sp, 
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = result, 
                fontSize = 40.sp, 
                style = MaterialTheme.typography.headlineLarge
            )
        }

        // 🔘 Grid Tombol Kalkulator
        val buttons = listOf(
            listOf("C", "(", ")", "÷"),
            listOf("7", "8", "9", "×"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "⌫", "=")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { symbol ->
                    Button(
                        onClick = {
                            when (symbol) {
                                "C" -> {
                                    expression = ""
                                    result = ""
                                }
                                "⌫" -> {
                                    if (expression.isNotEmpty()) {
                                        expression = expression.dropLast(1)
                                    }
                                }
                                "=" -> {
                                    result = calculateResult(expression)
                                }
                                else -> {
                                    expression += symbol
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                    ) {
                        Text(text = symbol, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

// 🧮 Fungsi Evaluasi Matematika Sederhana
fun calculateResult(expr: String): String {
    return try {
        if (expr.isEmpty()) return ""
        // Membersihkan operator di akhir jika user lupa memasukkan angka (misal: "3+6+")
        val cleanExpr = expr.trimEnd('+', '-', '*', '/', '×', '÷')
        val sanitized = cleanExpr.replace("×", "*").replace("÷", "/")
        
        val tokens = mutableListOf<String>()
        var numberBuffer = ""
        for (char in sanitized) {
            if (char in "+-*/") {
                if (numberBuffer.isNotEmpty()) {
                    tokens.add(numberBuffer)
                    numberBuffer = ""
                }
                tokens.add(char.toString())
            } else {
                numberBuffer += char
            }
        }
        if (numberBuffer.isNotEmpty()) tokens.add(numberBuffer)

        if (tokens.isEmpty()) return ""

        // Prioritas 1: Perkalian & Pembagian
        var i = 0
        while (i < tokens.size) {
            if (tokens[i] == "*" || tokens[i] == "/") {
                val left = tokens[i - 1].toDouble()
                val right = tokens[i + 1].toDouble()
                val res = if (tokens[i] == "*") left * right else left / right
                tokens[i - 1] = res.toString()
                tokens.removeAt(i)
                tokens.removeAt(i)
                i--
            }
            i++
        }

        // Prioritas 2: Penjumlahan & Pengurangan
        var total = tokens[0].toDouble()
        i = 1
        while (i < tokens.size) {
            val op = tokens[i]
            val nextVal = tokens[i + 1].toDouble()
            total = if (op == "+") total + nextVal else total - nextVal
            i += 2
        }

        if (total % 1.0 == 0.0) total.toLong().toString() else total.toString()
    } catch (e: Exception) {
        "Error"
    }
}

@Composable
private fun ThemeModeOption(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("theme_mode_$title"),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KalkulatorScreenPreview() {
    val viewModel: MainViewModel by viewModels { MainViewModel.Factory }
    KalkulatorTheme {
        KalkulatorScreen(viewModel = viewModel)
    }
}
