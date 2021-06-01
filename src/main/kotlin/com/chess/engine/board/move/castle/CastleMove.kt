package com.chess.engine.board.move.castle

import com.chess.engine.board.Board
import com.chess.engine.board.move.Move
import com.chess.engine.pieces.Piece
import com.chess.engine.pieces.impl.Rook

abstract class CastleMove(
    board: Board,
    pieceMoved: Piece?,
    destinationCoordinate: Int,
    val castleRook: Rook,
    private val castleRookDestination: Int
) : Move(board, pieceMoved, destinationCoordinate) {
    override val isCastlingMove = true

    override fun execute() = Board {
        board.allPieces.filter { piece -> movedPiece != piece && castleRook != piece }.forEach(::setPiece)
        setPiece(movedPiece?.movePiece(this@CastleMove))
        setPiece(Rook(castleRook.pieceAlliance, castleRookDestination, false))
        nextMoveMaker = board.currentPlayer.opponent.alliance
        transitionMove = this@CastleMove
    }

    override fun equals(other: Any?): Boolean {
        if (!super.equals(other)) return false
        other as CastleMove
        if (castleRook != other.castleRook) return false

        return true
    }

    override fun hashCode() = listOf(
        castleRook.hashCode()
    ).fold(super.hashCode()) { total, num -> 31 * total + num }


}