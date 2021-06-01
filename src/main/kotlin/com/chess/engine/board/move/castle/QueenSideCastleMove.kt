package com.chess.engine.board.move.castle

import com.chess.engine.board.Board
import com.chess.engine.pieces.Piece
import com.chess.engine.pieces.impl.Rook

class QueenSideCastleMove(
    board: Board,
    pieceMoved: Piece?,
    destinationCoordinate: Int,
    castleRook: Rook,
    castleRookDestination: Int
) : CastleMove(board, pieceMoved, destinationCoordinate, castleRook, castleRookDestination) {
    override fun toString() = "O-O-O"

    override fun equals(other: Any?) = super.equals(other) && castleRook == (other as? QueenSideCastleMove)?.castleRook

    override fun hashCode() = listOf(
        castleRook.hashCode()
    ).fold(super.hashCode()) { total, num -> 31 * total + num }
}