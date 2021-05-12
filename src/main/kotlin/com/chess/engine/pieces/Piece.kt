package com.chess.engine.pieces

import com.chess.engine.Alliance
import com.chess.engine.board.Board
import com.chess.engine.board.move.Move

abstract class Piece(
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Piece

        if (pieceType != other.pieceType) return false
        if (pieceAlliance != other.pieceAlliance) return false
        if (piecePosition != other.piecePosition) return false
        if (isFirstMove != other.isFirstMove) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pieceType.hashCode()
        result = 31 * result + pieceAlliance.hashCode()
        result = 31 * result + piecePosition
        result = 31 * result + isFirstMove.hashCode()
        return result
    }


}
