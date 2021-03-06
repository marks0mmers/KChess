package com.chess.engine.player

import com.chess.engine.Alliance
import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.board.move.castle.KingSideCastleMove
import com.chess.engine.board.move.Move
import com.chess.engine.board.move.castle.QueenSideCastleMove
import com.chess.engine.pieces.PieceType
import com.chess.engine.pieces.impl.Rook

class WhitePlayer(
    board: Board,
    whiteStandardLegals: Collection<Move>,
    blackStandardLegals: Collection<Move>
) : Player(board, board.whitePieces, whiteStandardLegals, blackStandardLegals) {
    override val alliance = Alliance.WHITE
    override val opponent: Player
        get() = board.blackPlayer

    override fun calculateKingCastles(
        playerLegals: Collection<Move>,
        opponentLegals: Collection<Move>
    ): Collection<Move> = mutableListOf<Move>().apply {
        when {
            !hasCastleOpportunities -> return@apply
            playerKing.isFirstMove && playerKing.piecePosition == 60 && !isInCheck -> {
                when {
                    // white king-side castle
                    (61..62).all { board.getPiece(it) == null } -> {
                        val kingSideRook = board.getPiece(63) as? Rook
                        if (kingSideRook != null &&
                            kingSideRook.isFirstMove &&
                            kingSideRook.pieceType == PieceType.ROOK &&
                            (61..62).all { it.calculateAttacksOnTile(opponentLegals).isEmpty() } &&
                            !BoardUtils.isKingPawnTrap(board, playerKing, 52)
                        ) {
                            add(KingSideCastleMove(board, playerKing, 62, kingSideRook, 61))
                        }
                    }
                    // white queen-side castle
                    (57..59).all { board.getPiece(it) == null } -> {
                        val queenSideRook = board.getPiece(56) as? Rook
                        if (queenSideRook != null &&
                            queenSideRook.isFirstMove &&
                            queenSideRook.pieceType == PieceType.ROOK &&
                            (58..59).all { it.calculateAttacksOnTile(opponentLegals).isEmpty() } &&
                            !BoardUtils.isKingPawnTrap(board, playerKing, 52)
                        ) {
                            add(QueenSideCastleMove(board, playerKing, 58, queenSideRook, 59))
                        }
                    }
                }
            }
        }
    }

    override fun toString() = Alliance.WHITE.toString()
}