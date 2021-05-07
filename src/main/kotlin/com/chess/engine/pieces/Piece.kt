package com.chess.engine.pieces

import com.chess.engine.Alliance
import com.chess.engine.board.Board
import com.chess.engine.board.move.Move

sealed class Piece(
    val pieceType: PieceType,
    val pieceAlliance: Alliance,
    val piecePosition: Int,
    val isFirstMove: Boolean
) {
    val pieceValue: Int
        get() = pieceType.value

    abstract val locationBonus: Int

    abstract fun movePiece(move: Move): Piece?

    abstract fun calculateLegalMoves(board: Board): Collection<Move>
}
