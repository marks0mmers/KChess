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

    private val ROWS = listOf(
        BoardUtils.FIRST_ROW,
        BoardUtils.SECOND_ROW,
        BoardUtils.THIRD_ROW,
        BoardUtils.FOURTH_ROW,
        BoardUtils.FIFTH_ROW,
        BoardUtils.SIXTH_ROW,
        BoardUtils.SEVENTH_ROW,
        BoardUtils.EIGHTH_ROW
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
    
    private fun getFile(coordinate: Int) = when (val file = COLUMNS.indexOfFirst { it[coordinate] }) {
        -1 -> error("Should not reach here")
        else -> file
    }
    
    private fun getRank(coordinate: Int) = when (val rank = ROWS.indexOfFirst { it[coordinate] }) {
        -1 -> error("Should not reach here")
        else -> rank
    }
}