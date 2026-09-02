package com.ehan.app2.engine

import com.ehan.app2.model.Move
import com.ehan.app2.model.Piece
import com.ehan.app2.model.PlayerPiece
import com.ehan.app2.model.Position

object GameEngine {
    fun createInitialBoard(): Map<Position, List<Piece>> {
        val board = mutableMapOf<Position, List<Piece>>()
        val pos = Position(0, 7)
        val pieces = listOf<Piece>(
            Piece(player = PlayerPiece.PLAYER_1, position = pos),
            Piece(player = PlayerPiece.PLAYER_2, position = pos)
        )
        board[pos] = pieces
        return board
    }
    
    fun applyMove(
        board: Map<Position, List<Piece>>,
        move: Move
    ): MoveResult {
        val newBoard = board.toMutableMap()
        val pieces = board[move.from]?.toMutableList() ?: return MoveResult(board)

        var _piece: Piece? = null
        var i: Int = 0
        if (pieces != null && pieces is List<Piece>) {
            cariPiece@ for (index in pieces.indices) {
                val piece = pieces[i]
                if (piece.player == move.player) {
                    i = index
                    _piece = pieces.removeAt(i)
                    break@cariPiece
                }
            }
        }
        if (_piece == null) {
            return MoveResult(board)
        }
        newBoard[move.from] = pieces
        val piece = _piece

        val updatedPiece = piece.copy(position = move.to)
        if (newBoard?[move.to] != null && newBoard?[move.to] is List<Piece>) {
            val pieces = newBoard?[move.to]?.toMutableList()
            if (pieces == null) {
                newBoard[move.to] = listOf<Piece>(updatedPiece)
            } else {
                pieces.add(updatedPiece)
                newBoard[move.to] = pieces
            }
        } else {
            newBoard[move.to] = listOf<Piece>(updatedPiece)
        }

        return MoveResult(
           newBoard = newBoard
        )
    }

    fun getPieces(
        board: Map<Position, List<Piece>>
    ): Map<PlayerPiece, Piece> {
        val pieces = mutableMapOf<PlayerPiece, Piece>()
        for ((pos, _pieces) in board) {
            for (piece in _pieces) {
                pieces[piece.player] = piece
            }
        }
        return pieces
    }

    fun getMove(
        piece: Piece,
        length: Int
    ): Move {
        val from: Position = piece.position
        var _to: Position = piece.position
        for (i in 1..length) {
            var __to = _to.copy(col = if (from.row % 2 == 0) { from.col - 1 } else { from.col + 1 })
            if (__to.isValid() == true) {
                _to = __to
            } else {
                _to = _to.copy(row = _to.row + 1)
            }
        }
        val to: Position = _to
        return Move(from = from, to = to, player = piece.player)
    }

    fun getNextMove(
        piece: Piece
    ): Move {
        return getMove(piece, 1)
    }

    data class MoveResult(
        val newBoard: Map<Position, List<Piece>>
    )
}
