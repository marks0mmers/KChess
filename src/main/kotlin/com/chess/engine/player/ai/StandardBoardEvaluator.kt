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

    override fun evaluate(board: Board, depth: Int) = score(board.whitePlayer, depth) - score(board.blackPlayer, depth)

    fun evaluationDetails(board: Board, depth: Int) =
        """
        White Mobility : ${mobility(board.whitePlayer)}
        White kingThreats : ${kingThreats(board.whitePlayer, depth)}
        White attacks : ${attacks(board.whitePlayer)}
        White castle : ${castle(board.whitePlayer)}
        White pieceEval : ${pieceEvaluations(board.whitePlayer)}
        White pawnStructure : ${pawnStructure(board.whitePlayer)}
        ---------------------
        Black Mobility : ${mobility(board.blackPlayer)}
        Black kingThreats : ${kingThreats(board.blackPlayer, depth)}
        Black attacks : ${attacks(board.blackPlayer)}
        Black castle : ${castle(board.blackPlayer)}
        Black pieceEval : ${pieceEvaluations(board.blackPlayer)}
        Black pawnStructure : ${pawnStructure(board.blackPlayer)}
        
        Final Score = ${evaluate(board, depth)}
        """.trimIndent()

    private fun score(player: Player, depth: Int) = mobility(player) +
            kingThreats(player, depth) +
            attacks(player) +
            castle(player) +
            pieceEvaluations(player) +
            pawnStructure(player)

    private fun attacks(player: Player) = player.legalMoves
        .filter { move -> move.isAttack && move.movedPiece?.pieceValue ?: 0 <= move.attackedPiece?.pieceValue ?: 0 }
        .count() * ATTACK_MULTIPLIER

    private fun pieceEvaluations(player: Player) = player.activePieces
        .fold(0 to 0) { (pieceValuationScore, numBishops), piece ->
            pieceValuationScore + piece.pieceValue + piece.locationBonus to if (piece.pieceType == PieceType.BISHOP) numBishops + 1 else numBishops
        }.let { (pieceValuationScore, numBishops) ->
            pieceValuationScore + if (numBishops == 2) TWO_BISHOPS_BONUS else 0
        }

    private fun mobility(player: Player) = MOBILITY_MULTIPLIER * mobilityRatio(player)

    private fun mobilityRatio(player: Player) =
        (player.legalMoves.size * 10.0 / player.opponent.legalMoves.size).toInt()

    private fun kingThreats(player: Player, depth: Int) = when {
        player.opponent.isInCheckMate -> CHECK_MATE_BONUS * depthBonus(depth)
        else -> check(player)
    }

    private fun check(player: Player) = when {
        player.opponent.isInCheck -> CHECK_BONUS
        else -> 0
    }

    private fun depthBonus(depth: Int) = when (depth) {
        0 -> 1
        else -> 100 * depth
    }

    private fun castle(player: Player) = when {
        player.isCastled -> CASTLE_BONUS
        else -> 0
    }

    private fun pawnStructure(player: Player) = PawnStructureAnalyzer.pawnStructureScore(player)

    private fun kingSafety(player: Player) = KingSafetyAnalyzer.calculateKingTropism(player).let { kingDistance ->
        kingDistance.enemyPiece?.pieceValue ?: 0 / 100 * kingDistance.distance
    }
}