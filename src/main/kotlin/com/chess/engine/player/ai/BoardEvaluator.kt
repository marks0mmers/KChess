package com.chess.engine.player.ai

import com.chess.engine.board.Board

interface BoardEvaluator {
    fun evaluate(board: Board, depth: Int): Int
}