package com.chess.engine.pieces

import com.chess.engine.Alliance
import com.chess.engine.board.Board
import com.chess.engine.board.move.Move

class Pawn(
    pieceAlliance: Alliance,
    piecePosition: Int,
    isFirstMove: Boolean
) : Piece(PieceType.PAWN, pieceAlliance, piecePosition, isFirstMove) {
    companion object {
        val CANDIDATE_MOVE_COORDINATES = arrayOf(8, 16 ,7, 9)
    }

    constructor(pieceAlliance: Alliance, piecePosition: Int) : this(pieceAlliance, piecePosition, true)

    override val locationBonus = pieceAlliance.pawnBonus(piecePosition)

    override fun movePiece(move: Move) = PieceUtils.getMovedPawn(move.movedPiece?.pieceAlliance, move.destinationCoordinate)

    override fun calculateLegalMoves(board: Board): Collection<Move> = mutableListOf<Move>().apply {

    }

    override fun toString() = pieceType.toString()
}