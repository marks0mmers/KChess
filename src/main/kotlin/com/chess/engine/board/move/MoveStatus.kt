package com.chess.engine.board.move

enum class MoveStatus {
    DONE {
        override val isDone = true
    },
    ILLEGAL_MOVE {
        override val isDone = false
    },
    LEAVES_PLAYER_IN_CHECK {
        override val isDone = false
    };

    abstract val isDone: Boolean
}