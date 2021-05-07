package com.chess.engine.board

import com.chess.engine.Alliance
import com.chess.engine.board.move.Move
import com.chess.engine.pieces.Pawn
import com.chess.engine.pieces.Piece
import com.chess.engine.player.BlackPlayer
import com.chess.engine.player.Player
import com.chess.engine.player.WhitePlayer

class Board(init: BoardBuilderDsl.() -> Unit) {
    val boardConfig: Map<Int, Piece>
    val currentPlayer: Player
    val whitePieces: Collection<Piece>
    val blackPieces: Collection<Piece>
    val whitePlayer: WhitePlayer
    val blackPlayer: BlackPlayer
    val enPassantPawn: Pawn?
    val transitionMove: Move?

    init {
        val builder = BoardBuilderDsl()
        builder.init()
        boardConfig = builder.boardConfig
        whitePieces = builder.boardConfig.values.filter { it.pieceAlliance == Alliance.WHITE }
        blackPieces = builder.boardConfig.values.filter { it.pieceAlliance == Alliance.BLACK }
        enPassantPawn = builder.enPassantPawn
        val whiteStandardMoves = whitePieces.flatMap { it.calculateLegalMoves(this) }
        val blackStandardMoves = blackPieces.flatMap { it.calculateLegalMoves(this) }
        whitePlayer = WhitePlayer(this, whiteStandardMoves, blackStandardMoves)
        blackPlayer = BlackPlayer(this, whiteStandardMoves, blackStandardMoves)
        currentPlayer = builder.nextMoveMaker.choosePlayerByAlliance(whitePlayer, blackPlayer)
        transitionMove = builder.transitionMove
    }

    fun getPiece(coordinate: Int): Piece? {
        TODO()
    }

    class BoardBuilderDsl {
        lateinit var boardConfig: Map<Int, Piece>
        lateinit var nextMoveMaker: Alliance
        var enPassantPawn: Pawn? = null
        var transitionMove: Move? = null
    }
}