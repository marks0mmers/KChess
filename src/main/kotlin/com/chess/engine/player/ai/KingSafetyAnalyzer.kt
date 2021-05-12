package com.chess.engine.player.ai

import com.chess.engine.board.BoardUtils
import com.chess.engine.pieces.Piece
import com.chess.engine.player.Player
import kotlin.math.abs
import kotlin.math.max

object KingSafetyAnalyzer {
    private val COLUMNS = listOf(
        BoardUtils.FIRST_COLUMN,
        BoardUtils.SECOND_COLUMN,
        BoardUtils.THIRD_COLUMN,
        BoardUtils.FOURTH_COLUMN,
        BoardUtils.FIFTH_COLUMN,
        BoardUtils.SIXTH_COLUMN,
        BoardUtils.SEVENTH_COLUMN,
        BoardUtils.EIGHTH_COLUMN
    )

    fun calculateKingTropism(player: Player): KingDistance {
        val playerKingSquare = player.playerKing.piecePosition
        val enemyMoves = player.opponent.legalMoves
        val (closestPiece, closestDistance) = enemyMoves.fold(null.to<Piece?, Int>(Int.MAX_VALUE)) { (closestPiece, closestDistance), move ->
            val currentDistance = calculateChebyshevDistance(playerKingSquare, move.destinationCoordinate)
            if (currentDistance < closestDistance) move.movedPiece to currentDistance else closestPiece to closestDistance
        }
        return KingDistance(closestPiece, closestDistance)
    }
    
    private fun calculateChebyshevDistance(kingTileId: Int, enemyAttackTileId: Int): Int {
        val squareOneRank = getRank(kingTileId)
        val squareTwoRank = getRank(enemyAttackTileId)

        val squareOneFile = getFile(kingTileId)
        val squareTwoFile = getFile(enemyAttackTileId)

        val rankDistance = abs(squareTwoRank - squareOneRank)
        val fileDistance = abs(squareTwoFile - squareOneFile)

        return max(rankDistance, fileDistance)
    }
    
    private fun getFile(coordinate: Int) = when {
        BoardUtils.FIRST_COLUMN[coordinate] -> 1
        BoardUtils.SECOND_COLUMN[coordinate] -> 2
        BoardUtils.THIRD_COLUMN[coordinate] -> 3
        BoardUtils.FOURTH_COLUMN[coordinate] -> 4
        BoardUtils.FIFTH_COLUMN[coordinate] -> 5
        BoardUtils.SIXTH_COLUMN[coordinate] -> 6
        BoardUtils.SEVENTH_COLUMN[coordinate] -> 7
        BoardUtils.EIGHTH_COLUMN[coordinate] -> 8
        else -> error("Should not reach here")
    }
    
    private fun getRank(coordinate: Int) = when {
        BoardUtils.FIRST_ROW[coordinate] -> 1
        BoardUtils.SECOND_ROW[coordinate] -> 2
        BoardUtils.THIRD_ROW[coordinate] -> 3
        BoardUtils.FOURTH_ROW[coordinate] -> 4
        BoardUtils.FIFTH_ROW[coordinate] -> 5
        BoardUtils.SIXTH_ROW[coordinate] -> 6
        BoardUtils.SEVENTH_ROW[coordinate] -> 7
        BoardUtils.EIGHTH_ROW[coordinate] -> 8
        else -> error("Should not reach here")
    }
}