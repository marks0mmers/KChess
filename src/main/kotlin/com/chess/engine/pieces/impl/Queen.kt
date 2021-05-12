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

class Queen(
    pieceAlliance: Alliance,
    piecePosition: Int,
    isFirstMove: Boolean
) : Piece(PieceType.QUEEN, pieceAlliance, piecePosition, isFirstMove) {
    companion object {
        private val CANDIDATE_MOVE_COORDINATES = arrayOf(-9, -8, -7, -1, 1, 7, 8, 9)
        private fun isFirstColumnExclusion(currentPosition: Int, candidatePosition: Int) =
            BoardUtils.FIRST_COLUMN[candidatePosition] && (currentPosition == -9
                    || currentPosition == -1 || currentPosition == 7)

        private fun isEightColumnExclusion(currentPosition: Int, candidatePosition: Int) =
            BoardUtils.EIGHTH_COLUMN[candidatePosition] && (currentPosition == -7
                    || currentPosition == 1 || currentPosition == 9)
    }

    constructor(pieceAlliance: Alliance, piecePosition: Int) : this(pieceAlliance, piecePosition, true)

    override val locationBonus = pieceAlliance.queenBonus(piecePosition)

    override fun movePiece(move: Move) =
        PieceUtils.getMovedQueen(move.movedPiece?.pieceAlliance, move.destinationCoordinate)

    override fun calculateLegalMoves(board: Board): Collection<Move> = mutableListOf<Move>().apply {
        CANDIDATE_MOVE_COORDINATES.forEach { currentCandidateOffset ->
            var candidateDestinationOffset = piecePosition
            loop@ while(true) {
                if (!isFirstColumnExclusion(currentCandidateOffset, candidateDestinationOffset) &&
                    !isEightColumnExclusion(currentCandidateOffset, candidateDestinationOffset)
                ) {
                    candidateDestinationOffset += currentCandidateOffset
                    when {
                        BoardUtils.isValidTileCoordinate(candidateDestinationOffset) -> {
                            when (val pieceAtDestination = board.getPiece(candidateDestinationOffset)) {
                                null -> add(MajorMove(board, this@Queen, candidateDestinationOffset))
                                else -> {
                                    if (pieceAlliance != pieceAtDestination.pieceAlliance) {
                                        add(MajorAttackMove(board, this@Queen, candidateDestinationOffset, pieceAtDestination))
                                    }
                                    break@loop
                                }
                            }
                        }
                        else -> break@loop
                    }
                } else break@loop
            }
        }
    }

    override fun toString() = pieceType.toString()
}