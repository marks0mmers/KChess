package com.chess.engine.board.move.util

import com.chess.engine.board.Board

object MoveFactory {
    fun createMove(board: Board, currentCoordinate: Int, destinationCoordinate: Int) = board.allLegalMoves
        .find { move -> move.currentCoordinate == currentCoordinate && move.destinationCoordinate == destinationCoordinate }
}