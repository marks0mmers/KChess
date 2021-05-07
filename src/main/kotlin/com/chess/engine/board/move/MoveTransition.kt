package com.chess.engine.board.move

import com.chess.engine.board.Board

data class MoveTransition (
    val fromBoard: Board,
    val toBoard: Board,
    val transitionMove: Move,
    val moveStatus: MoveStatus
)
