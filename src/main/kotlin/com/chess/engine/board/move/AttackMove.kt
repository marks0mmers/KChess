package com.chess.engine.board.move

import com.chess.engine.board.Board
import com.chess.engine.pieces.Piece

abstract class AttackMove(
    board: Board,
    movedPiece: Piece?,
    destinationCoordinate: Int,
    override val attackedPiece: Piece
) : Move(board, movedPiece, destinationCoordinate) {
    override val isAttack = true

    override fun equals(other: Any?): Boolean {
        if (!super.equals(other)) return false
        other as AttackMove
        if (attackedPiece != other.attackedPiece) return false
        return true
    }

    override fun hashCode() = 31 * super.hashCode() + attackedPiece.hashCode()


}