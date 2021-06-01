package com.chess.engine.player

enum class PlayerType(private val pieceName: String) {
    HUMAN("Human"),
    COMPUTER("Computer");

    override fun toString() = pieceName
}