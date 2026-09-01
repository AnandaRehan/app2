package com.ehan.app2

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
// import androidx.compose.foundation.layout.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.systemBarsPadding
// import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
// import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.ehan.app2.ui.theme.App2Theme
import com.ehan.app2.engine.GameEngine
import com.ehan.app2.model.Move
import com.ehan.app2.model.MoveResult
import com.ehan.app2.model.Piece
import com.ehan.app2.model.PlayerPiece
import com.ehan.app2.model.Position

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dataStoreManager = DataStoreManager(this)
        setContent {
            App2Theme {
                greeting(
                    viewModel = SettingsViewModel(dataStoreManager)
                )
            }
        }
    }
}
/**
    Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show()
    
    LaunchedEffect(angka_1) {
        if (angka_1 != _angka_1) {
            // Jika Game Guardian mengubah nilaiTampilan di RAM menjadi 9999, 
            // kondisi ini akan langsung aktif.
            onCheatDetected()
            
            // Paksa nilai tampilan kembali ke nilai asli (Membalas manipulasi RAM)
            angka_1 = _angka_1
        }
    }

*/


/**
data class Move(
    val from: Position,
    val to: Position
) {
    val notation: String
        get() {
            val sep = if (isCapture) "x" else "-"
            val promo = if (isPromotion) " [DAM!]" else ""
            return "${from.notation}$sep${to.notation}$promo"
        }
}*/

@Composable
fun greeting(
    viewModel: SettingsViewModel
) {
    val context: Context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val boardBorderColor: Color = Color(0xFF5D4037)
    val lightSquareColor: Color = Color(0xFFF0D9B5)
    val darkSquareColor: Color = Color(0xFFB58863)
    val highlightColor: Color = Color(0xFFFFD54F)

    var board by rememberSaveable { mutableStateOf(GameEngine.createInitialBoard()) }
    var dadu: Int? by rememberSaveable { mutableStateOf<Int?>(null) }

    fun handleMove(move: Move) {
        if (move.to.isValid() == true) {
            val result = GameEngine.applyMove(board, move)
            board = result.newBoard
        } else {
            ShowMessage(context, "in valid move $move.to.notation")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp)
            .shadow(16.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(boardBorderColor)
            .padding(10.dp)
            .testTag("checkers_board")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            val rowRange = (7 downTo 0)
            val colRange = (7 downTo 0)
            val jalan = (0..3)
            for (r in jalan) {
                val c = 0
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    val pos = Position(r, 0)
                    val isDark = pos.isDarkSquare()
                    val squareColor = if (isDark) darkSquareColor else lightSquareColor
                    
                    val pieces = board[pos]
                    Box(
                        modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(squareColor)
                                .testTag("square_${pos.row}_${pos.col}"),
                            contentAlignment = Alignment.Center
                    ) {
                        if (r == 7) {
                                Text(
                                    text = ('A' + c).toString(),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = lightSquareColor.copy(alpha = 0.5f),
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(2.dp)
                                )
                            }
                            if (c == 7 || c == 0) {
                                Text(
                                    text = (8 - r).toString(),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = lightSquareColor.copy(alpha = 0.5f),
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(2.dp)
                                )
                            }
                            
                        if (pieces is MutableList<Piece>) {
                            for (i in pieces.indices) {
                                PieceToken(
                                    piece = pieces[i],
                                    modifier = Modifier.padding(4.dp)
                                )
                        }}
                    }
                }
            }
        }
    }
    Button(
        onClick = {
            var currentPlayer: PlayerPiece = PlayerPiece.PLAYER_1
            var _piece: Piece? = null
            dadu = 1
            for (k in board) {
                if (board[k] is MutableList<Piece>) {
                    for (i in board[k]) {
                        var __piece = board[k][i]
                        if (__piece is Piece) {
                            if (__piece.player == currentPlayer) {
                                _piece = __piece
                            }
                        }
                    }
                }
            }
            val piece = _piece
            if (piece != null && dadu != null) {
                val from: Position = piece.position
                val to: Position = from.copy(row = from.row + dadu)
                handleMove(Move(from = from, to = to, player = currentPlayer))
            } else {
                ShowMessage(context, "null")
            }
        }
    ) {
        Text(text = dadu.toString())
    }
    }
}

@Composable
fun PieceToken(
    piece: Piece,
   // pieceTheme: PieceTheme,
   // isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val isP1: Boolean = piece.player == PlayerPiece.PLAYER_1
  //  val baseColor = if (isP1) Color(pieceTheme.p1Color) else Color(pieceTheme.p2Color)
   // val accentColor = if (isP1) Color(pieceTheme.p1Accent) else Color(pieceTheme.p2Accent)
    val baseColor: Color = Color(0xFFDC2626)
    val accentColor: Color = Color(0xFFFEF2F2)

    val elevation = 4.dp
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(3.dp)
            .shadow(elevation, CircleShape)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        baseColor.copy(alpha = 0.95f),
                        baseColor,
                        baseColor.copy(red = baseColor.red * 0.7f, green = baseColor.green * 0.7f, blue = baseColor.blue * 0.7f)
                    ),
                    center = Offset(0.3f, 0.3f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Inner concentric tactile ring
        Box(
            modifier = Modifier
                .fillMaxSize(0.72f)
                .border(1.5.dp, accentColor.copy(alpha = 0.35f), CircleShape)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            
                // Small inner dot for clean checker token look
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(accentColor.copy(alpha = 0.45f))
                )
            
        }
    }
}
