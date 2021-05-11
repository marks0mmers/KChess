package com.chess.engine.pieces

import com.chess.engine.Alliance
import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.board.move.*

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
        CANDIDATE_MOVE_COORDINATES.forEach { currentCandidateOffset ->
            val candidateDestinationCoordinate = piecePosition + pieceAlliance.direction * currentCandidateOffset
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                when {
                    currentCandidateOffset == 8 && board.getPiece(candidateDestinationCoordinate) == null -> {
                        val pawnMove = PawnMove(board, this@Pawn, candidateDestinationCoordinate)
                        when {
                            pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate) -> {
                                add(PawnPromotion(pawnMove, PieceUtils.getMovedQueen(pieceAlliance, candidateDestinationCoordinate)))
                                add(PawnPromotion(pawnMove, PieceUtils.getMovedRook(pieceAlliance, candidateDestinationCoordinate)))
                                add(PawnPromotion(pawnMove, PieceUtils.getMovedKnight(pieceAlliance, candidateDestinationCoordinate)))
                                add(PawnPromotion(pawnMove, PieceUtils.getMovedBishop(pieceAlliance, candidateDestinationCoordinate)))
                            }
                            else -> add(pawnMove)
                        }
                    }
                    currentCandidateOffset == 16 && isFirstMove &&
                            (BoardUtils.SECOND_ROW[piecePosition] && pieceAlliance.isBlack || BoardUtils.SEVENTH_ROW[piecePosition] && pieceAlliance.isWhite) -> {
                        val behindCandidateDestinationCoordinate = piecePosition + pieceAlliance.direction * 8
                        if (board.getPiece(candidateDestinationCoordinate) == null && board.getPiece(behindCandidateDestinationCoordinate) == null) {
                            add(PawnJump(board, this@Pawn, candidateDestinationCoordinate))
                        }
                    }
                    currentCandidateOffset == 7 &&
                            !(BoardUtils.EIGHTH_COLUMN[piecePosition] && pieceAlliance.isWhite || BoardUtils.FIRST_COLUMN[piecePosition] && pieceAlliance.isBlack) -> {
                        when {
                            board.getPiece(candidateDestinationCoordinate) != null -> {
                                val pieceOnCandidate = board.getPiece(candidateDestinationCoordinate)
                                if (pieceOnCandidate != null && pieceAlliance != pieceOnCandidate.pieceAlliance) {
                                    val pawnMove = PawnAttackMove(board, this@Pawn, candidateDestinationCoordinate, pieceOnCandidate)
                                    when {
                                        pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate) -> {
                                            add(PawnPromotion(pawnMove, PieceUtils.getMovedQueen(pieceAlliance, candidateDestinationCoordinate)))
                                            add(PawnPromotion(pawnMove, PieceUtils.getMovedRook(pieceAlliance, candidateDestinationCoordinate)))
                                            add(PawnPromotion(pawnMove, PieceUtils.getMovedKnight(pieceAlliance, candidateDestinationCoordinate)))
                                            add(PawnPromotion(pawnMove, PieceUtils.getMovedBishop(pieceAlliance, candidateDestinationCoordinate)))
                                        }
                                        else -> add(pawnMove)
                                    }
                                }
                            }
                            board.enPassantPawn != null && board.enPassantPawn.piecePosition == (piecePosition + pieceAlliance.oppositeDirection) -> {
                                val pieceOnCandidate = board.enPassantPawn
                                if (pieceAlliance != pieceOnCandidate.pieceAlliance) {
                                    add(PawnEnPassantAttack(board, this@Pawn, candidateDestinationCoordinate, pieceOnCandidate))
                                }
                            }
                        }
                    }
                    currentCandidateOffset == 9 &&
                            !(BoardUtils.FIRST_COLUMN[piecePosition] && pieceAlliance.isWhite || BoardUtils.EIGHTH_COLUMN[piecePosition] && pieceAlliance.isBlack) -> {
                        when {
                            board.getPiece(candidateDestinationCoordinate) != null -> {
                                val pieceOnCandidate = board.getPiece(candidateDestinationCoordinate)
                                if (pieceOnCandidate != null && pieceAlliance != pieceOnCandidate.pieceAlliance) {
                                    val pawnMove = PawnAttackMove(board, this@Pawn, candidateDestinationCoordinate, pieceOnCandidate)
                                    when {
                                        pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate) -> {
                                            add(PawnPromotion(pawnMove, PieceUtils.getMovedQueen(pieceAlliance, candidateDestinationCoordinate)))
                                            add(PawnPromotion(pawnMove, PieceUtils.getMovedRook(pieceAlliance, candidateDestinationCoordinate)))
                                            add(PawnPromotion(pawnMove, PieceUtils.getMovedKnight(pieceAlliance, candidateDestinationCoordinate)))
                                            add(PawnPromotion(pawnMove, PieceUtils.getMovedBishop(pieceAlliance, candidateDestinationCoordinate)))
                                        }
                                        else -> add(pawnMove)
                                    }
                                }
                            }
                            board.enPassantPawn != null && board.enPassantPawn.piecePosition == (piecePosition + pieceAlliance.oppositeDirection) -> {
                                val pieceOnCandidate = board.enPassantPawn
                                if (pieceAlliance != pieceOnCandidate.pieceAlliance) {
                                    add(PawnEnPassantAttack(board, this@Pawn, candidateDestinationCoordinate, pieceOnCandidate))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun toString() = pieceType.toString()
}