package com.chess.engine.pieces.impl

import com.chess.engine.Alliance
import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.board.move.MajorAttackMove
import com.chess.engine.board.move.MajorMove
import com.chess.engine.board.move.Move
import com.chess.engine.pieces.Piece
import com.chess.engine.pieces.PieceType
import com.chess.engine.pieces.util.PieceUtils

class Bishop(
    pieceAlliance: Alliance,
    piecePosition: Int,
    isFirstMove: Boolean
) : Piece(PieceType.BISHOP, pieceAlliance, piecePosition, isFirstMove) {
    companion object {
        private val CANDIDATE_MOVE_COORDINATES = arrayOf(-9, -7, 7, 9)

        private fun isFirstColumnExclusion(currentCandidate: Int, candidateDestinationCoordinate: Int) =
            BoardUtils.FIRST_COLUMN[candidateDestinationCoordinate] && (currentCandidate == -9 || currentCandidate == 7)

        private fun isEighthColumnExclusion(currentCandidate: Int, candidateDestinationCoordinate: Int) =
            BoardUtils.EIGHTH_COLUMN[candidateDestinationCoordinate] && (currentCandidate == -7 || currentCandidate == 9)
    }

    constructor(pieceAlliance: Alliance, piecePosition: Int) : this(pieceAlliance, piecePosition, true)

    override val locationBonus: Int
        get() = pieceAlliance.bishopBonus(piecePosition)

    override fun movePiece(move: Move): Piece =
        PieceUtils.getMovedBishop(move.movedPiece?.pieceAlliance, move.destinationCoordinate)

    override fun calculateLegalMoves(board: Board): Collection<Move> = mutableListOf<Move>().apply {
        CANDIDATE_MOVE_COORDINATES.forEach { currentCandidateOffset ->
            var candidateDestinationCoordinate = piecePosition
            loop@ while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (!isFirstColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate) &&
                    !isEighthColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)
                ) {
                    candidateDestinationCoordinate += currentCandidateOffset
                    if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                        when (val pieceAtDestination = board.getPiece(candidateDestinationCoordinate)) {
                            null -> add(MajorMove(board, this@Bishop, candidateDestinationCoordinate))
                            else -> {
                                if (pieceAlliance != pieceAtDestination.pieceAlliance) {
                                    add(MajorAttackMove(board, this@Bishop, candidateDestinationCoordinate, pieceAtDestination))
                                }
                                break@loop
                            }
                        }
                    }
                } else break@loop
            }
        }
    }

    override fun toString() = pieceType.toString()
}