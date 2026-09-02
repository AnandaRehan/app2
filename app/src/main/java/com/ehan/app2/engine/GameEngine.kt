package com.ehan.app2.engine

import com.ehan.app2.model.Move
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
        board: Map<Position, Piece>,
        move: Move
    ): MoveResult {
        val newBoard = board.toMutableMap()
       /** if (!(newBoard[move.from] is MutableList<Piece>>) || newBoard[move.from].isEmpty()) {
            return MoveResult(
                newBoard = board
            )
        }*/
        val piece = newBoard.remove(move.from) ?: return MoveResult(board)
        /**
        var _piece: Piece
        for (i in newBoard[move.from].indices) {
            val piece = newBoard[move.from][i]
            if (move.player == piece.player) {
                _piece = newBoard[move.from].removeAt(i)
            }
        }
        val piece = _piece.copy(position = move.to)*/
        // Remove captured pieces
        
        // Check for promotion (crowned as Dam / King)

        val updatedPiece = piece
        newBoard[move.to] = updatedPiece
        return MoveResult(
           newBoard = newBoard
        )
    }

    fun getPieces(
        board: Map<Position, Piece>
    ): Map<PlayerPiece, Piece> {
        val pieces = mutableMapOf<PlayerPiece, Piece>()
        for ((pos, piece) in board) {
            pieces[piece.player] = piece
        }
    
        return pieces
    }
/**
    fun getMove(
        piece: Piece,
        length: Int
    ): Move {
        val from: Position = piece.position
        val to: Po
    }
    */
    fun getNextMove(
        piece: Piece
    ): Move {
        val from: Position = piece.position
        val to: Position = from.copy(row = from.row + 1)
        return Move(
            from = from,
            to = to,
            player = piece.player
        )
    }

    data class MoveResult(
        val newBoard: Map<Position, Piece>
    )
}
