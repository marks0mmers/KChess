package com.chess.engine.board.move.pawn

import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.board.move.Move
import com.chess.engine.pieces.impl.Pawn
import com.chess.engine.pieces.Piece

class PawnPromotion(
    private val decoratedMove: Move,
    private val promotionPiece: Piece
) : PawnMove(decoratedMove.board, decoratedMove.movedPiece, decoratedMove.destinationCoordinate) {
    override val isAttack = decoratedMove.isAttack
    override val attackedPiece = decoratedMove.attackedPiece
    private val promotedPawn = decoratedMove.movedPiece as? Pawn

    override fun execute() = Board {
        val pawnMovedBoard = decoratedMove.board
        pawnMovedBoard.currentPlayer.activePieces.filter { piece -> promotedPawn != piece }.forEach(::setPiece)
        pawnMovedBoard.currentPlayer.opponent.activePieces.forEach(::setPiece)
        setPiece(promotionPiece.movePiece(this@PawnPromotion))
        nextMoveMaker = pawnMovedBoard.currentPlayer.alliance
        transitionMove = this@PawnPromotion
    }

    override fun toString() = movedPiece?.let { BoardUtils.getPositionAtCoordinate(it.piecePosition) } + "-" +
            BoardUtils.getPositionAtCoordinate(destinationCoordinate) + "=" + promotionPiece.pieceType.toString()

    override fun hashCode() = listOf(
        decoratedMove.hashCode(),
        promotedPawn.hashCode()
    ).fold(0) { total, num -> 31 * total + num }

    override fun equals(other: Any?) = when {
        this === other -> true
        javaClass != other?.javaClass -> false
        else -> super.equals(other)
    }
}