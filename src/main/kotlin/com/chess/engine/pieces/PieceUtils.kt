package com.chess.engine.pieces

import com.chess.engine.Alliance
import com.chess.engine.board.BoardUtils
import com.chess.utils.Table
import com.chess.utils.get

object PieceUtils {
    private val ALL_POSSIBLE_PAWNS: Table<Alliance, Int, Pawn> = Alliance.values()
        .associate { alliance ->
            alliance to BoardUtils.TILES_RANGE.associateWith { Pawn(alliance, it, false) }
        }
    private val ALL_POSSIBLE_KNIGHTS: Table<Alliance, Int, Knight> = Alliance.values()
        .associate { alliance ->
            alliance to BoardUtils.TILES_RANGE.associateWith { Knight(alliance, it, false) }
        }
    private val ALL_POSSIBLE_BISHOPS: Table<Alliance, Int, Bishop> = Alliance.values()
        .associate { alliance ->
            alliance to BoardUtils.TILES_RANGE.associateWith { Bishop(alliance, it,false) }
        }
    private val ALL_POSSIBLE_ROOKS: Table<Alliance, Int, Rook> = Alliance.values()
        .associate { alliance ->
            alliance to BoardUtils.TILES_RANGE.associateWith { Rook(alliance, it, false) }
        }
    private val ALL_POSSIBLE_QUEENS: Table<Alliance, Int, Queen> = Alliance.values()
        .associate { alliance ->
            alliance to BoardUtils.TILES_RANGE.associateWith { Queen(alliance, it, false) }
        }

    fun getMovedPawn(alliance: Alliance?, destinationCoordinate: Int) = ALL_POSSIBLE_PAWNS[alliance to destinationCoordinate]
    fun getMovedKnight(alliance: Alliance?, destinationCoordinate: Int) = ALL_POSSIBLE_KNIGHTS[alliance to destinationCoordinate]
    fun getMovedBishop(alliance: Alliance?, destinationCoordinate: Int) = ALL_POSSIBLE_BISHOPS[alliance to destinationCoordinate]
    fun getMovedRook(alliance: Alliance?, destinationCoordinate: Int) = ALL_POSSIBLE_ROOKS[alliance to destinationCoordinate]
    fun getMovedQueen(alliance: Alliance?, destinationCoordinate: Int) = ALL_POSSIBLE_QUEENS[alliance to destinationCoordinate]
}