package com.chess.engine.pieces.impl

import com.chess.engine.Alliance
import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.board.move.MajorAttackMove
import com.chess.engine.board.move.MajorMove
import com.chess.engine.board.move.Move
import com.chess.engine.pieces.Piece
import com.chess.engine.pieces.PieceType

class King(
    pieceAlliance: Alliance,
    piecePosition: Int,
    isFirstMove: Boolean,
    val kingSideCastleCapable: Boolean,
    val queenSideCastleCapable: Boolean
) : Piece(PieceType.KING, pieceAlliance, piecePosition, isFirstMove) {
    companion object {
        val CANDIDATE_MOVE_COORDINATES = arrayOf(-9, -8, -7, -1, 1, 7, 8, 9)

        private fun isFirstColumnExclusion(
            currentCandidate: Int,
            candidateDestinationCoordinate: Int
        ): Boolean {
            return BoardUtils.FIRST_COLUMN[currentCandidate] && (candidateDestinationCoordinate == -9 ||
                    candidateDestinationCoordinate == -1 || candidateDestinationCoordinate == 7)
        }

        private fun isEighthColumnExclusion(
            currentCandidate: Int,
            candidateDestinationCoordinate: Int
        ): Boolean {
            return BoardUtils.EIGHTH_COLUMN[currentCandidate] && (candidateDestinationCoordinate == -7 ||
                    candidateDestinationCoordinate == 1 || candidateDestinationCoordinate == 9)
        }
    }

    var isCastled: Boolean = false

    constructor(
        pieceAlliance: Alliance,
        piecePosition: Int,
        isFirstMove: Boolean,
        isCastled: Boolean,
        kingSideCastleCapable: Boolean,
        queenSideCastleCapable: Boolean
    ) : this(pieceAlliance, piecePosition, isFirstMove, kingSideCastleCapable, queenSideCastleCapable) {
        this.isCastled = isCastled
    }

    constructor(
        pieceAlliance: Alliance,
        piecePosition: Int,
        kingSideCastleCapable: Boolean,
        queenSideCastleCapable: Boolean
    ) : this(pieceAlliance, piecePosition, true, kingSideCastleCapable, queenSideCastleCapable)

    override val locationBonus = pieceAlliance.kingBonus(piecePosition)

    override fun movePiece(move: Move) = King(
        pieceAlliance,
        move.destinationCoordinate,
        isFirstMove = false,
        move.isCastlingMove,
        kingSideCastleCapable = false,
        queenSideCastleCapable = false
    )

    override fun calculateLegalMoves(board: Board): Collection<Move> = mutableListOf<Move>().apply {
        CANDIDATE_MOVE_COORDINATES.forEach { currentCandidateOffset ->
            if (!isFirstColumnExclusion(piecePosition, currentCandidateOffset) &&
                !isEighthColumnExclusion(piecePosition, currentCandidateOffset)
            ) {
                val candidateDestinationCoordinate = piecePosition + currentCandidateOffset
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    when (val pieceAtDestination = board.getPiece(candidateDestinationCoordinate)) {
                        null -> add(MajorMove(board, this@King, candidateDestinationCoordinate))
                        else -> {
                            if (pieceAlliance != pieceAtDestination.pieceAlliance) {
                                add(MajorAttackMove(board, this@King, candidateDestinationCoordinate, pieceAtDestination))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun toString() = pieceType.toString()
}