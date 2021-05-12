package com.chess.engine.board.move.pawn

import com.chess.engine.board.Board
import com.chess.engine.pieces.impl.Pawn
import com.chess.engine.pieces.Piece

class PawnEnPassantAttack(
    board: Board,
    movedPiece: Piece?,
    destinationCoordinate: Int,
    attackedPiece: Piece
) : PawnAttackMove(board, movedPiece, destinationCoordinate, attackedPiece) {
    override fun execute() = Board {
        board.currentPlayer.activePieces.filter { piece -> movedPiece != piece }.forEach(::setPiece)
        board.currentPlayer.opponent.activePieces.forEach(::setPiece)
        setPiece(movedPiece?.movePiece(this@PawnEnPassantAttack))
        nextMoveMaker = board.currentPlayer.opponent.alliance
        transitionMove = this@PawnEnPassantAttack
    }

    override fun undo() = Board {
        board.allPieces.forEach(::setPiece)
        enPassantPawn = attackedPiece as? Pawn
        nextMoveMaker = board.currentPlayer.alliance
    }
}