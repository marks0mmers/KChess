package com.chess.engine.player.ai

import com.chess.engine.pieces.Piece

data class KingDistance(
    val enemyPiece: Piece?,
    val distance: Int
) {
    val tropismScore: Int
        get() = enemyPiece?.pieceValue ?: 0 / 10 * distance
}