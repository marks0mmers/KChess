package com.chess.engine.pieces

import com.chess.engine.Alliance
import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.board.move.MajorAttackMove
import com.chess.engine.board.move.MajorMove
import com.chess.engine.board.move.Move

class Rook (
    pieceAlliance: Alliance,
    piecePosition: Int,
    isFirstMove: Boolean
) : Piece(PieceType.ROOK, pieceAlliance, piecePosition, isFirstMove) {
    companion object {
        private val CANDIDATE_MOVE_COORDINATES = arrayOf(-8, -1, 1, 8)

        private fun isColumnExclusive(currentCandidate: Int, candidateDestinationCoordinate: Int) =
            (BoardUtils.FIRST_COLUMN[candidateDestinationCoordinate] && currentCandidate == -1) ||
                    (BoardUtils.EIGHTH_COLUMN[candidateDestinationCoordinate] && currentCandidate == 1)
    }

    constructor(pieceAlliance: Alliance, piecePosition: Int) : this(pieceAlliance, piecePosition, true)

    override val locationBonus = pieceAlliance.rookBonus(piecePosition)

    override fun movePiece(move: Move) = PieceUtils.getMovedRook(move.movedPiece?.pieceAlliance, move.destinationCoordinate)

    override fun calculateLegalMoves(board: Board): Collection<Move> = mutableListOf<Move>().apply {
        CANDIDATE_MOVE_COORDINATES.forEach { currentCandidateOffset ->
            var candidateDestinationCoordinate = piecePosition
            loop@ while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (!isColumnExclusive(currentCandidateOffset, candidateDestinationCoordinate)) {
                    candidateDestinationCoordinate += currentCandidateOffset
                    if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                        when (val pieceAtDestination = board.getPiece(candidateDestinationCoordinate)) {
                            null -> add(MajorMove(board, this@Rook, candidateDestinationCoordinate))
                            else -> {
                                if (pieceAlliance != pieceAtDestination.pieceAlliance) {
                                    add(MajorAttackMove(board, this@Rook, candidateDestinationCoordinate, pieceAtDestination))
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