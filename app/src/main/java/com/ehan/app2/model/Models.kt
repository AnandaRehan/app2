package com.ehan.app2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class PlayerPiece(val displayName: String, val shortName: String) : Parcelable {
    PLAYER_1("Pemain 1 (Merah)", "Merah"),
    PLAYER_2("Pemain 2 (Hitam)", "Hitam");

    fun opponent(): PlayerPiece = if (this == PLAYER_1) PLAYER_2 else PLAYER_1
}

@Parcelize
data class Position(val row: Int, val col: Int) : Parcelable {
    fun isValid(): Boolean = row in 0..7 && col in 0..7
    fun isDarkSquare(): Boolean = (row + col) % 2 == 1

    val notation: String
        get() {
            val colChar = ('A' + col)
            val rowNum = 8 - row
            return "$colChar$rowNum"
        }
}

@Parcelize
data class Piece(
    val player: PlayerPiece,
    val position: Position
): Parcelable

@Parcelize
data class Move(
    val from: Position,
    val to: Position,
    val player: PlayerPiece
) : Parcelable
