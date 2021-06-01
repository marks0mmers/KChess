package com.chess.engine.board.move

enum class MoveStatus(val isDone: Boolean) {
    DONE(true),
    ILLEGAL_MOVE(false),
    LEAVES_PLAYER_IN_CHECK(false)
}