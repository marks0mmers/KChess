package com.chess.engine.pieces.util

import com.chess.engine.Alliance
import com.chess.engine.board.BoardUtils
import com.chess.engine.pieces.impl.*

object PieceUtils {
    private val ALL_POSSIBLE_PAWNS = Alliance.values()
        .flatMap { alliance -> BoardUtils.TILES_RANGE.map { alliance to it } }
        .associateWith { (alliance, position) -> Pawn(alliance, position, false) }

    private val ALL_POSSIBLE_KNIGHTS = Alliance.values()
        .flatMap { alliance -> BoardUtils.TILES_RANGE.map { alliance to it } }
        .associateWith { (alliance, position) -> Knight(alliance, position, false) }

    private val ALL_POSSIBLE_BISHOPS = Alliance.values()
        .flatMap { alliance -> BoardUtils.TILES_RANGE.map { alliance to it } }
        .associateWith { (alliance, position) -> Bishop(alliance, position, false) }

    private val ALL_POSSIBLE_ROOKS = Alliance.values()
        .flatMap { alliance -> BoardUtils.TILES_RANGE.map { alliance to it } }
        .associateWith { (alliance, position) -> Rook(alliance, position, false) }

    private val ALL_POSSIBLE_QUEENS = Alliance.values()
        .flatMap { alliance -> BoardUtils.TILES_RANGE.map { alliance to it } }
        .associateWith { (alliance, position) -> Queen(alliance, position, false) }

    fun getMovedPawn(alliance: Alliance?, destinationCoordinate: Int) =
        ALL_POSSIBLE_PAWNS[alliance to destinationCoordinate] ?: error("Invalid Position")

    fun getMovedKnight(alliance: Alliance?, destinationCoordinate: Int) =
        ALL_POSSIBLE_KNIGHTS[alliance to destinationCoordinate] ?: error("Invalid Position")

    fun getMovedBishop(alliance: Alliance?, destinationCoordinate: Int) =
        ALL_POSSIBLE_BISHOPS[alliance to destinationCoordinate] ?: error("Invalid Position")

    fun getMovedRook(alliance: Alliance?, destinationCoordinate: Int) =
        ALL_POSSIBLE_ROOKS[alliance to destinationCoordinate] ?: error("Invalid Position")

    fun getMovedQueen(alliance: Alliance?, destinationCoordinate: Int) =
        ALL_POSSIBLE_QUEENS[alliance to destinationCoordinate] ?: error("Invalid Position")
}