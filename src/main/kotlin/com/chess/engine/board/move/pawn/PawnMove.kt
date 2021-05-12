package com.chess.engine.board.move.pawn

import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.board.move.Move
import com.chess.engine.pieces.Piece

open class PawnMove(
    board: Board,
    movedPiece: Piece?,
    destinationCoordinate: Int
) : Move(board, movedPiece, destinationCoordinate) {
    override fun toString() = BoardUtils.getPositionAtCoordinate(destinationCoordinate)
}