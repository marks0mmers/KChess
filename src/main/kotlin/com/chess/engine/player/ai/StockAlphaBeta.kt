package com.chess.engine.player.ai

import com.chess.engine.Alliance.BLACK
import com.chess.engine.Alliance.WHITE
import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.board.BoardUtils.kingThreat
import com.chess.engine.board.BoardUtils.mvvlva
import com.chess.engine.board.move.Move
import com.chess.engine.player.Player

class StockAlphaBeta(private val searchDepth: Int) : MoveStrategy {
    companion object {
        private const val MAX_QUIESCENCE = 5000 * 5
        private fun Player.score(highestSeenValue: Int, lowestSeenValue: Int) =
            when (alliance) {
                WHITE -> "[score: $highestSeenValue]"
                BLACK -> "[score: $lowestSeenValue]"
            }

        private fun calculateTimeTaken(start: Long, end: Long) = "${(end - start) / 1000000} ms"
    }

    private val evaluator = StandardBoardEvaluator
    private var quiescenceCount = 0
    override var numBoardsEvaluated = 0L

    override fun execute(board: Board): Move {
        val startTime = System.currentTimeMillis()
        val currentPlayer = board.currentPlayer
        var bestMove: Move? = null
        var highestSeenValue = Int.MIN_VALUE
        var lowestSeenValue = Int.MAX_VALUE
        var currentValue: Int
        println("${board.currentPlayer} THINKING with depth = $searchDepth")
        var moveCounter = 1
        val numMoves = board.currentPlayer.legalMoves.size
        MoveSorter.EXPENSIVE.sort(board.currentPlayer.legalMoves).forEach { move ->
            val moveTransition = board.currentPlayer.makeMove(move)
            quiescenceCount = 0
            val s = when (moveTransition.moveStatus.isDone) {
                true -> {
                    val candidateMoveStartTime = System.nanoTime()
                    currentValue = when (currentPlayer.alliance) {
                        WHITE -> min(moveTransition.toBoard, searchDepth - 1, highestSeenValue, lowestSeenValue)
                        BLACK -> max(moveTransition.toBoard, searchDepth - 1, highestSeenValue, lowestSeenValue)
                    }
                    when {
                        currentPlayer.alliance.isWhite && currentValue > highestSeenValue -> {
                            highestSeenValue = currentValue
                            bestMove = move
                            if (moveTransition.toBoard.blackPlayer.isInCheckMate) {
                                return@forEach
                            }
                        }
                        currentPlayer.alliance.isBlack && currentValue < lowestSeenValue -> {
                            lowestSeenValue = currentValue
                            bestMove = move
                            if (moveTransition.toBoard.whitePlayer.isInCheckMate) {
                                return@forEach
                            }
                        }
                    }
                    val quiescenceInfo =
                        " ${currentPlayer.score(highestSeenValue, lowestSeenValue)} q: $quiescenceCount"
                    " ${toString()}($searchDepth), m: ($moveCounter/$numMoves) $move, best: $bestMove$quiescenceInfo, t: ${
                        calculateTimeTaken(
                            candidateMoveStartTime,
                            System.nanoTime()
                        )
                    }"
                }
                false -> "\t${toString()}($searchDepth), m: ($moveCounter/$numMoves) $move is illegal! best: $bestMove"
            }
            println(s)
            moveCounter++
        }
        val executionTime = System.currentTimeMillis() - startTime
        println(
            String.format(
                "%s SELECTS %s [#boards evaluated = %d, time taken = %d ms, rate = %.1f]",
                board.currentPlayer,
                bestMove,
                numBoardsEvaluated,
                executionTime,
                (1000 * (numBoardsEvaluated.toDouble() / executionTime))
            )
        )
        return bestMove ?: error("Failed to find a move")
    }

    override fun toString() = "StockAB"

    private fun max(board: Board, depth: Int, highest: Int, lowest: Int): Int {
        if (depth == 0 || BoardUtils.isEndGame(board)) {
            numBoardsEvaluated++
            return evaluator.evaluate(board, depth)
        }
        var currentHighest = highest
        MoveSorter.STANDARD.sort(board.currentPlayer.legalMoves)
            .map { board.currentPlayer.makeMove(it) }
            .filter { it.moveStatus.isDone }
            .map { it.toBoard }
            .forEach { toBoard ->
                currentHighest = currentHighest.coerceAtLeast(
                    min(
                        toBoard,
                        calculateQuiescenceDepth(toBoard, depth),
                        currentHighest,
                        lowest
                    )
                )
                if (currentHighest >= lowest) {
                    return lowest
                }
            }
        return currentHighest
    }

    private fun min(board: Board, depth: Int, highest: Int, lowest: Int): Int {
        if (depth == 0 || BoardUtils.isEndGame(board)) {
            numBoardsEvaluated++
            return evaluator.evaluate(board, depth)
        }
        var currentLowest = lowest
        MoveSorter.STANDARD.sort(board.currentPlayer.legalMoves)
            .map { board.currentPlayer.makeMove(it) }
            .filter { it.moveStatus.isDone }
            .map { it.toBoard }
            .forEach { toBoard ->
                currentLowest = currentLowest.coerceAtMost(
                    max(
                        toBoard,
                        calculateQuiescenceDepth(toBoard, depth),
                        highest,
                        currentLowest
                    )
                )
                if (currentLowest <= highest) {
                    return highest
                }
            }
        return currentLowest
    }

    private fun calculateQuiescenceDepth(toBoard: Board, depth: Int): Int {
        if (depth == 1 && quiescenceCount < MAX_QUIESCENCE) {
            var activityMeasure = 0
            if (toBoard.currentPlayer.isInCheck) {
                activityMeasure += 1
            }
            activityMeasure += BoardUtils.lastNMoves(toBoard, 2)
                .filter { it.isAttack }
                .size
            if (activityMeasure >= 2) {
                quiescenceCount++
                return 2
            }
        }
        return depth - 1
    }

    private enum class MoveSorter {
        STANDARD {
            override fun sort(collection: Collection<Move>) = collection
                .sortedBy(Move::isCastlingMove)
                .sortedBy(::mvvlva)
        },
        EXPENSIVE {
            override fun sort(collection: Collection<Move>) = collection
                .sortedBy(::kingThreat)
                .sortedBy(Move::isCastlingMove)
                .sortedBy(::mvvlva)
        };

        abstract fun sort(collection: Collection<Move>): Collection<Move>
    }
}