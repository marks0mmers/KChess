package com.chess.engine.player.ai

import com.chess.engine.board.Board
import com.chess.engine.pieces.PieceType
import com.chess.engine.player.Player

object StandardBoardEvaluator : BoardEvaluator {
    private const val CHECK_MATE_BONUS = 10000
    private const val CHECK_BONUS = 45
    private const val CASTLE_BONUS = 25
    private const val MOBILITY_MULTIPLIER = 5
    private const val ATTACK_MULTIPLIER = 1
    private const val TWO_BISHOPS_BONUS = 25

    override fun evaluate(board: Board, depth: Int) = board.whitePlayer.score(depth) - board.blackPlayer.score(depth)

    fun evaluationDetails(board: Board, depth: Int) =
        """
        White Mobility : ${board.whitePlayer.mobility}
        White kingThreats : ${board.whitePlayer.kingThreats(depth)}
        White kingSafety : ${board.whitePlayer.kingSafety}
        White attacks : ${board.whitePlayer.attacks}
        White castle : ${board.whitePlayer.castle}
        White pieceEval : ${board.whitePlayer.pieceEvaluations}
        White pawnStructure : ${board.whitePlayer.pawnStructure}
        ---------------------
        Black Mobility : ${board.blackPlayer.mobility}
        Black kingThreats : ${board.blackPlayer.kingThreats(depth)}
        Black kingSafety : ${board.blackPlayer.kingSafety}
        Black attacks : ${board.blackPlayer.attacks}
        Black castle : ${board.blackPlayer.castle}
        Black pieceEval : ${board.blackPlayer.pieceEvaluations}
        Black pawnStructure : ${board.blackPlayer.pawnStructure}
        
        Final Score = ${evaluate(board, depth)}
        """.trimIndent()

    private fun Player.score(depth: Int) = mobility +
            kingThreats(depth) +
            attacks +
            castle +
            pieceEvaluations +
            pawnStructure

    private val Player.attacks
        get() = ATTACK_MULTIPLIER * legalMoves.count { move ->
            move.isAttack && (move.movedPiece?.pieceValue ?: 0) <= (move.attackedPiece?.pieceValue ?: 0)
        }

    private val Player.pieceEvaluations
        get() = activePieces
            .fold(0 to 0) { (pieceValuationScore, numBishops), piece ->
                pieceValuationScore + piece.pieceValue + piece.locationBonus to if (piece.pieceType == PieceType.BISHOP) numBishops + 1 else numBishops
            }.let { (pieceValuationScore, numBishops) ->
                pieceValuationScore + if (numBishops == 2) TWO_BISHOPS_BONUS else 0
            }

    private val Player.mobility get() = MOBILITY_MULTIPLIER * mobilityRatio

    private val Player.mobilityRatio get() = (legalMoves.size * 10.0 / opponent.legalMoves.size).toInt()

    private fun Player.kingThreats(depth: Int) = when {
        opponent.isInCheckMate -> CHECK_MATE_BONUS * depthBonus(depth)
        else -> check
    }

    private val Player.check get() = when {
        opponent.isInCheck -> CHECK_BONUS
        else -> 0
    }

    private val Player.castle get() = when {
        isCastled -> CASTLE_BONUS
        else -> 0
    }

    private val Player.pawnStructure get() = PawnStructureAnalyzer.pawnStructureScore(this)

    private fun depthBonus(depth: Int) = when (depth) {
        0 -> 1
        else -> 100 * depth
    }

    private val Player.kingSafety
        get() = KingSafetyAnalyzer.calculateKingTropism(this).let { kingDistance ->
            (kingDistance.enemyPiece?.pieceValue ?: 0) / 100 * kingDistance.distance
        }
}