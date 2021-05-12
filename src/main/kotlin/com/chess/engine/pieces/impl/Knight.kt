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

class Knight(
    pieceAlliance: Alliance,
    piecePosition: Int,
    isFirstMove: Boolean
) : Piece(PieceType.KNIGHT, pieceAlliance, piecePosition, isFirstMove) {
    companion object {
        val CANDIDATE_MOVE_COORDINATES = arrayOf(-17, -15, -10, -6, 6, 10, 15, 17)
        private fun isFirstColumnExclusion(
            currentPosition: Int,
            candidateOffset: Int
        ) = BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 ||
                candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15)

        private fun isSecondColumnExclusion(
            currentPosition: Int,
            candidateOffset: Int
        ) = BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6)

        private fun isSeventhColumnExclusion(
            currentPosition: Int,
            candidateOffset: Int
        ) = BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10)

        private fun isEighthColumnExclusion(
            currentPosition: Int,
            candidateOffset: Int
        ) = BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 ||
                candidateOffset == -6 || candidateOffset == 10 || candidateOffset == 17)
    }

    constructor(pieceAlliance: Alliance, piecePosition: Int) : this(pieceAlliance, piecePosition, true)

    override val locationBonus = pieceAlliance.knightBonus(piecePosition)

    override fun movePiece(move: Move) =
        PieceUtils.getMovedKnight(move.movedPiece?.pieceAlliance, move.destinationCoordinate)

    override fun calculateLegalMoves(board: Board): Collection<Move> = mutableListOf<Move>().apply {
        CANDIDATE_MOVE_COORDINATES.forEach { currentCandidateOffset ->
            if (!isFirstColumnExclusion(piecePosition, currentCandidateOffset) &&
                !isSecondColumnExclusion(piecePosition, currentCandidateOffset) &&
                !isSeventhColumnExclusion(piecePosition, currentCandidateOffset) &&
                !isEighthColumnExclusion(piecePosition, currentCandidateOffset)
            ) {
                val candidateDestinationCoordinate = piecePosition + currentCandidateOffset
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    when (val pieceAtDestination = board.getPiece(candidateDestinationCoordinate)) {
                        null -> add(MajorMove(board, this@Knight, candidateDestinationCoordinate))
                        else -> {
                            if (pieceAlliance != pieceAtDestination.pieceAlliance) {
                                add(MajorAttackMove(board, this@Knight, candidateDestinationCoordinate, pieceAtDestination))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun toString() = pieceType.toString()

}