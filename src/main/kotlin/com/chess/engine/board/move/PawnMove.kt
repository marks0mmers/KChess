package com.chess.engine.board.move

import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.pieces.Piece

class PawnMove(board: Board, movedPiece: Piece?, destinationCoordinate: Int) :
    Move(board, movedPiece, destinationCoordinate) {
    override fun toString() = BoardUtils.getPositionAtCoordinate(destinationCoordinate)
}