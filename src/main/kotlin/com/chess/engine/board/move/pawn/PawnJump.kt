package com.chess.engine.board.move.pawn

import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.board.move.Move
import com.chess.engine.pieces.impl.Pawn
import com.chess.engine.pieces.Piece

class PawnJump(
    board: Board,
    pieceMoved: Piece?,
    destinationCoordinate: Int
) : Move(board, pieceMoved, destinationCoordinate) {
    override fun execute() = Board {
        board.currentPlayer.activePieces.filter { piece -> movedPiece != piece }.forEach(::setPiece)
        board.currentPlayer.opponent.activePieces.forEach(::setPiece)
        val movedPawn = movedPiece?.movePiece(this@PawnJump) as? Pawn
        setPiece(movedPawn)
        enPassantPawn = movedPawn
        nextMoveMaker = board.currentPlayer.opponent.alliance
        transitionMove = this@PawnJump
    }

    override fun toString() = BoardUtils.getPositionAtCoordinate(destinationCoordinate)
}