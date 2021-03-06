package com.chess.engine.board.move.util

import com.chess.engine.board.move.Move

object MoveUtils {
    fun exchangeScore(move: Move?): Int = when (move) {
        null -> 1
        else -> if (move.isAttack) 5 * exchangeScore(move.board.transitionMove) else exchangeScore(move.board.transitionMove)
    }
}