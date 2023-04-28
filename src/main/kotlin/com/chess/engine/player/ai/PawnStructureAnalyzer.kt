package com.chess.engine.player.ai

import com.chess.engine.pieces.Piece
import com.chess.engine.pieces.PieceType
import com.chess.engine.player.Player

@Suppress("unused")
object PawnStructureAnalyzer {
    private const val ISOLATED_PAWN_PENALTY = -10
    private const val DOUBLED_PAWN_PENALTY = -10

    fun isolatedPawnPenalty(player: Player) = calculateIsolatedPawnPenalty(createPawnColumnTable(calculatePlayerPawns(player)))

    fun doubledPawnPenalty(player: Player) = calculatePawnColumnStack(createPawnColumnTable(calculatePlayerPawns(player)))

    fun pawnStructureScore(player: Player) = createPawnColumnTable(calculatePlayerPawns(player)).let {
        calculatePawnColumnStack(it) + calculateIsolatedPawnPenalty(it)
    }

    private fun calculatePawnColumnStack(pawnsOnColumnsTable: Array<Int>) =
        pawnsOnColumnsTable.filter { it > 1 }.sum() * DOUBLED_PAWN_PENALTY

    private fun calculateIsolatedPawnPenalty(pawnsOnColumnTable: Array<Int>): Int {
        var numIsolatedPawns = 0
        if (pawnsOnColumnTable[0] > 0 && pawnsOnColumnTable[1] == 0) {
            numIsolatedPawns += pawnsOnColumnTable[0]
        }
        if (pawnsOnColumnTable[7] > 0 && pawnsOnColumnTable[6] == 0) {
            numIsolatedPawns += pawnsOnColumnTable[7]
        }
        for (i in pawnsOnColumnTable.indices) {
            if ((i == 0 || pawnsOnColumnTable[i-1] == 0) && (i == pawnsOnColumnTable.size - 1 || pawnsOnColumnTable[i+1] == 0)) {
                numIsolatedPawns += pawnsOnColumnTable[i]
            }
        }
        return numIsolatedPawns + ISOLATED_PAWN_PENALTY
    }

    private fun createPawnColumnTable(playerPawns: List<Piece>): Array<Int> {
        val table = Array(8) { 0 }
        playerPawns.forEach { table[it.piecePosition % 8]++ }
        return table
    }

    private fun calculatePlayerPawns(player: Player) = player.activePieces
        .filter { it.pieceType == PieceType.PAWN }
}