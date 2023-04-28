package com.chess.engine.player

enum class PlayerType(private val pieceName: String) {
    HUMAN("Human") {
        override val isComputer = false
    },
    COMPUTER("Computer") {
        override val isComputer = true
    };

    abstract val isComputer: Boolean

    override fun toString() = pieceName
}