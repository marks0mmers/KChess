package com.chess.engine.player.ai

import com.chess.engine.board.Board
import com.chess.engine.board.move.Move

interface MoveStrategy {
    val numBoardsEvaluated: Long
    suspend fun execute(board: Board): Move
}