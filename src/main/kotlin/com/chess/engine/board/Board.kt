package com.chess.engine.board

import com.chess.engine.Alliance
import com.chess.engine.board.move.Move
import com.chess.engine.pieces.*
import com.chess.engine.pieces.impl.*
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
    val allPieces: Collection<Piece>
        get() = whitePieces + blackPieces
    val allLegalMoves: Collection<Move>
        get() = whitePlayer.legalMoves + blackPlayer.legalMoves

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

    companion object {
        private fun prettyPrint(piece: Piece?) = when (piece) {
            null -> "-"
            else -> when {
                piece.pieceAlliance.isBlack -> piece.toString().toLowerCase()
                else -> piece.toString()
            }
        }

        val STANDARD_BOARD by lazy {
            Board {
                setPiece(Rook(Alliance.BLACK, 0))
                setPiece(Knight(Alliance.BLACK, 1))
                setPiece(Bishop(Alliance.BLACK, 2))
                setPiece(Queen(Alliance.BLACK, 3))
                setPiece(King(Alliance.BLACK, 4, kingSideCastleCapable = true, queenSideCastleCapable = true))
                setPiece(Bishop(Alliance.BLACK, 5))
                setPiece(Knight(Alliance.BLACK, 6))
                setPiece(Rook(Alliance.BLACK, 7))
                (8 until 16).forEach { i ->
                    setPiece(Pawn(Alliance.BLACK, i))
                }

                (48 until 56).forEach { i ->
                    setPiece(Pawn(Alliance.WHITE, i))
                }
                setPiece(Rook(Alliance.WHITE, 56))
                setPiece(Knight(Alliance.WHITE, 57))
                setPiece(Bishop(Alliance.WHITE, 58))
                setPiece(Queen(Alliance.WHITE, 59))
                setPiece(King(Alliance.WHITE, 60, kingSideCastleCapable = true, queenSideCastleCapable = true))
                setPiece(Bishop(Alliance.WHITE, 61))
                setPiece(Knight(Alliance.WHITE, 62))
                setPiece(Rook(Alliance.WHITE, 63))

                nextMoveMaker = Alliance.WHITE
            }
        }
    }

    fun getPiece(coordinate: Int) = boardConfig[coordinate]

    override fun toString(): String {
        return BoardUtils.TILES_RANGE.fold("") { s, i ->
            val baseRet = s + String.format("%3s", prettyPrint(boardConfig[i]))
            if ((i + 1) % 8 == 0) baseRet + "\n" else baseRet
        }
    }

    class BoardBuilderDsl {
        var boardConfig: MutableMap<Int, Piece> = mutableMapOf()
        lateinit var nextMoveMaker: Alliance
        var enPassantPawn: Pawn? = null
        var transitionMove: Move? = null

        fun setPiece(piece: Piece?) {
            if (piece != null) {
                boardConfig[piece.piecePosition] = piece
            }
        }
    }
}