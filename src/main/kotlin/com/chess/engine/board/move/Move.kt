package com.chess.engine.board.move

import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.pieces.Piece

abstract class Move(
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

    constructor(board: Board, destinationCoordinate: Int) : this(
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

    open fun execute() = Board {
        board.currentPlayer.activePieces.filter { it != movedPiece }.forEach(::setPiece)
        board.currentPlayer.opponent.activePieces.forEach(::setPiece)
        setPiece(movedPiece?.movePiece(this@Move))
        nextMoveMaker = board.currentPlayer.opponent.alliance
        transitionMove = this@Move
    }

    open fun undo() = Board {
        board.allPieces.forEach(::setPiece)
        nextMoveMaker = board.currentPlayer.alliance
    }

    fun disambiguationFile() = when (board.currentPlayer.legalMoves.find {
        it.destinationCoordinate == destinationCoordinate && it != this && movedPiece?.pieceType == it.movedPiece?.pieceType
    }) {
        null -> ""
        else ->  if (movedPiece != null) BoardUtils.getPositionAtCoordinate(movedPiece.piecePosition).subSequence(0, 1) else ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Move

        if (movedPiece != other.movedPiece) return false
        if (destinationCoordinate != other.destinationCoordinate) return false
        if (currentCoordinate != other.currentCoordinate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = movedPiece?.hashCode() ?: 0
        result = 31 * result + destinationCoordinate
        result = 31 * result + (movedPiece?.piecePosition ?: 0)
        return result
    }

}