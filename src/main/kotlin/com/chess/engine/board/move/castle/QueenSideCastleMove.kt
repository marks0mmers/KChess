package com.chess.engine.board.move.castle

import com.chess.engine.board.Board
import com.chess.engine.pieces.Piece
import com.chess.engine.pieces.impl.Rook

class QueenSideCastleMove(
    board: Board,
    pieceMoved: Piece?,
    destinationCoordinate: Int,
    castleRook: Rook,
    castleRookStart: Int,
    castleRookDestination: Int
) : CastleMove(board, pieceMoved, destinationCoordinate, castleRook, castleRookStart, castleRookDestination) {
    override fun toString() = "O-O-O"
}