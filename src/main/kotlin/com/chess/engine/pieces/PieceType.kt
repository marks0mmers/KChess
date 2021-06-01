package com.chess.engine.pieces

enum class PieceType(val value: Int, private val pieceName: String) {
    PAWN(value = 0, pieceName = "P"),
    KNIGHT(value = 0, pieceName = "N"),
    BISHOP(value = 0, pieceName = "B"),
    ROOK(value = 0, pieceName = "R"),
    QUEEN(value = 0, pieceName = "Q"),
    KING(value = 0, pieceName = "K");

    override fun toString() = pieceName
}