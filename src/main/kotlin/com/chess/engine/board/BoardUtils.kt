package com.chess.engine.board

import com.chess.engine.pieces.impl.King
import com.chess.engine.pieces.PieceType

object BoardUtils {
    private const val NUM_TILES = 64
    const val NUM_TILES_PER_ROW = 8
    val TILES_RANGE = 0 until NUM_TILES

    val FIRST_COLUMN = initColumn(0)
    val SECOND_COLUMN = initColumn(1)
    val THIRD_COLUMN = initColumn(2)
    val FOURTH_COLUMN = initColumn(3)
    val FIFTH_COLUMN = initColumn(4)
    val SIXTH_COLUMN = initColumn(5)
    val SEVENTH_COLUMN = initColumn(6)
    val EIGHTH_COLUMN = initColumn(7)
    val FIRST_ROW = initRow(0)
    val SECOND_ROW = initRow(1)
    val THIRD_ROW = initRow(2)
    val FOURTH_ROW = initRow(3)
    val FIFTH_ROW = initRow(4)
    val SIXTH_ROW = initRow(5)
    val SEVENTH_ROW = initRow(6)
    val EIGHTH_ROW = initRow(7)
    val ALGEBRAIC_NOTATION = listOf(
        "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
        "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
        "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
        "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
        "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
        "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
        "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
        "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
    )
    val POSITION_TO_COORDINATE = TILES_RANGE.associateBy { ALGEBRAIC_NOTATION[it] }

    private fun initColumn(columnNumber: Int) = TILES_RANGE.map {
        (it - columnNumber) % NUM_TILES_PER_ROW == 0
    }
    private fun initRow(rowNumber: Int) = TILES_RANGE.map {
        it in (NUM_TILES_PER_ROW * rowNumber) until NUM_TILES_PER_ROW * (rowNumber + 1)
    }

    fun isValidTileCoordinate(coordinate: Int) = coordinate in TILES_RANGE

    fun getCoordinateAtPosition(position: String) = POSITION_TO_COORDINATE[position]

    fun getPositionAtCoordinate(coordinate: Int) = ALGEBRAIC_NOTATION[coordinate]

    fun isKingPawnTrap(board: Board, playerKing: King, frontTile: Int) = board.getPiece(frontTile).run {
        this != null && pieceType == PieceType.PAWN && pieceAlliance == playerKing.pieceAlliance
    }
}