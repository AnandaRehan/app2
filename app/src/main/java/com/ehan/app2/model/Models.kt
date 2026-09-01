package com.ehan.app2.model

enum class PlayerPiece(val displayName: String, val shortName: String) {
    PLAYER_1("Pemain 1 (Merah)", "Merah"),
    PLAYER_2("Pemain 2 (Hitam)", "Hitam");

    fun opponent(): PlayerPiece = if (this == PLAYER_1) PLAYER_2 else PLAYER_1
}

data class Position(val row: Int, val col: Int) {
    fun isValid(): Boolean = row in 0..3 && col == 0
    fun isDarkSquare(): Boolean = (row + col) % 2 == 1

    val notation: String
        get() {
            val colChar = ('A' + col)
            val rowNum = 4 - row
            return "$colChar$rowNum"
        }
}

data class Piece(
    val player: PlayerPiece,
    val position: Position
)

data class Move(
    val from: Position,
    val to: Position,
    val player: PlayerPiece
)

data class MoveResult(
    val newBoard: Map<Position, MutableList<Piece>>
)
