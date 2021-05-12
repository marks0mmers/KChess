package com.chess.gui

enum class BoardDirection {
    NORMAL {
        override val opposite: BoardDirection
            get() = FLIPPED
    },
    FLIPPED {
        override val opposite: BoardDirection
            get() = NORMAL
    };

    abstract val opposite: BoardDirection
}