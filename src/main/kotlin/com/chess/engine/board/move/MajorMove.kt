package com.chess.engine.board.move

import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.pieces.Piece

class MajorMove(board: Board, pieceMoved: Piece?, destinationCoordinate: Int) :
    Move(board, pieceMoved, destinationCoordinate) {

    override fun toString() = movedPiece?.pieceType.toString() + 
            disambiguationFile() +
            BoardUtils.getPositionAtCoordinate(destinationCoordinate)
}