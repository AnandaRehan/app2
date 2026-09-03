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
        if (pieces != null && pieces is List<Piece>) {
            cariPiece@ for (i in pieces.indices) {
                val piece = pieces[i]
                if (piece.player == move.player) {
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
        if (newBoard[move.to] != null && newBoard[move.to] is List<Piece>) {
            val pieces = newBoard[move.to]?.toMutableList()
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
        board: Map<Position, List<Piece>>,
        piece: Piece,
        length: Int
    ): Move {
        val from: Position = piece.position
        var _to: Position = piece.position
        var _withPiece = mutableMapOf<Int, List<Piece>>()
        for (i in 1..length) {
            var __to = _to.copy(col = if (from.row % 2 == 0) { from.col - 1 } else { from.col + 1 })
            if (__to.isValid() == true) {
                _to = __to
            } else {
                _to = _to.copy(row = _to.row + 1)
            }
            if (_to in board) {
                val pieces = board[_to]
                if (pieces != null && pieces is List<Piece>) {
                    for (dex in pieces.indices) {
                        val _piece = pieces[dex]
                        if (piece != _piece) {
                            if (i in _withPiece) {
                                val _pieces = _withPiece[i]?.toMutableList()
                                if (_pieces == null) {
                                    _withPiece[i] = listOf<Piece>(_piece)
                                } else {
                                    _pieces.add(_piece)
                                    _withPiece[i] = _pieces
                                }
                            } else {
                                _withPiece[i] = listOf<Piece>(_piece)
                            }
                        }
                    }
                }
            }
        }
        val to: Position = _to
        val withPiece: Map<Int, List<Piece>>? = if (_withPiece.isNullOrEmpty()) { null } else { _withPiece }
        return Move(from = from, to = to, player = piece.player, withPiece = withPiece)
    }

    fun getNextMove(
        board: Map<Position, List<Piece>>,
        piece: Piece
    ): Move {
        return getMove(board, piece, 1)
    }

    data class MoveResult(
        val newBoard: Map<Position, List<Piece>>
    )
}
