package com.chess.engine.board.move

import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.pieces.Piece

open class PawnAttackMove(
    board: Board,
    movedPiece: Piece?,
    destinationCoordinate: Int,
    attackedPiece: Piece
) : AttackMove(board, movedPiece, destinationCoordinate, attackedPiece) {
    override fun toString() = movedPiece?.piecePosition?.let { BoardUtils.getPositionAtCoordinate(it) } +
            "x" + BoardUtils.getPositionAtCoordinate(destinationCoordinate)
}