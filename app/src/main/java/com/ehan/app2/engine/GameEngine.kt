package com.ehan.app2.engine

import com.ehan.app2.model.Move
import com.ehan.app2.model.MoveResult
import com.ehan.app2.model.Piece
import com.ehan.app2.model.PlayerPiece
import com.ehan.app2.model.Position

object GameEngine {
    fun createInitialBoard(): Map<Position, Piece> {
        val board = mutableMapOf<Position, Piece>()
        val pos = Position(0, 0)
        board[pos] = Piece(player = PlayerPiece.PLAYER_1, position = pos)
        return board
    }
    
    fun applyMove(
        board: Map<Position, MutableList<Piece>>,
        move: Move
    ): MoveResult {
        val newBoard = board.toMutableMap()
        if (!(newBoard[move.from] is MutableList<Piece>>) || newBoard[move.from].isEmpty()) {
            return MoveResult(
                newBoard = board
            )
        }
        var _piece: Piece
        for (i in newBoard[move.from].indices) {
            val piece = newBoard[move.from][i]
            if (move.player == piece.player) {
                _piece = newBoard[move.from].removeAt(i)
            }
        }
        val piece = _piece.copy(position = move.to)
        // Remove captured pieces
        
        // Check for promotion (crowned as Dam / King)

        val updatedPiece = piece
        if (newBoard[move.to] is MutableList<Piece>>) {
            newBoard[move.to].add(updatedPiece)
        } else {
            newBoard[move.to] = mutableListOf(updatedPiece)
        }
        return MoveResult(
           newBoard = newBoard
        )
    }

    fun getPieces(
        board: Map<Position, Piece>
    ): Map<PlayerPiece, Piece> {
        val pieces = mutableMapOf<PlayerPiece, Piece>()
        for (pos in board) {
            val piece = board[pos]
            pieces[piece.player] = piece
        }
        return pieces
    }
}
