package com.chess.engine.board.move

import com.chess.engine.board.Board
import com.chess.engine.pieces.Piece

sealed class Move(
    val board: Board,
    val movedPiece: Piece?,
    val destinationCoordinate: Int,
    val isFirstMove: Boolean
) {
    constructor(board: Board, pieceMoved: Piece?, destinationCoordinate: Int) : this(
        board,
        pieceMoved,
        destinationCoordinate,
        pieceMoved?.isFirstMove ?: false
    )

    private constructor(board: Board, destinationCoordinate: Int) : this(
        board,
        null,
        destinationCoordinate,
        false
    )

    open val isAttack = false
    open val isCastlingMove = false
    open val attackedPiece: Piece? = null
    val currentCoordinate: Int?
        get() = movedPiece?.piecePosition

    open fun execute(): Board {
        TODO()
    }

    fun undo(): Board {
        TODO()
    }

    fun disambiguationFile(): String = TODO()
}