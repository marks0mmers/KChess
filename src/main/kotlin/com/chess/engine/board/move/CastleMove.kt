package com.chess.engine.board.move

import com.chess.engine.board.Board
import com.chess.engine.pieces.Piece
import com.chess.engine.pieces.Rook

sealed class CastleMove(
    board: Board,
    pieceMoved: Piece?,
    destinationCoordinate: Int,
    val castleRook: Rook,
    val castleRookStart: Int,
    val castleRookDestination: Int
) : Move(board, pieceMoved, destinationCoordinate) {
    override val isCastlingMove = true

    override fun execute() = Board {
        board.allPieces.filter { piece -> movedPiece != piece && castleRook != piece }.forEach(::setPiece)
        setPiece(movedPiece?.movePiece(this@CastleMove))
        setPiece(Rook(castleRook.pieceAlliance, castleRookDestination, false))
        nextMoveMaker = board.currentPlayer.opponent.alliance
        transitionMove = this@CastleMove
    }
}