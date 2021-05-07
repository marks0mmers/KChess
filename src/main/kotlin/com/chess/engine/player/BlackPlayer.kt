package com.chess.engine.player

import com.chess.engine.Alliance
import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.board.move.KingSideCastleMove
import com.chess.engine.board.move.Move
import com.chess.engine.board.move.QueenSideCastleMove
import com.chess.engine.pieces.PieceType
import com.chess.engine.pieces.Rook

class BlackPlayer(
    board: Board,
    whiteStandardLegals: Collection<Move>,
    blackStandardLegals: Collection<Move>
) : Player(board, blackStandardLegals, whiteStandardLegals) {
    override val activePieces = board.whitePieces
    override val alliance = Alliance.BLACK
    override val opponent = board.whitePlayer

    override fun calculateKingCastles(
        playerLegals: Collection<Move>,
        opponentLegals: Collection<Move>
    ): Collection<Move> = mutableListOf<Move>().apply {
        when {
            !hasCastleOpportunities -> return@apply
            playerKing.isFirstMove && playerKing.piecePosition == 4 && !isInCheck -> {
                when {
                    // black king-side castle
                    (5..6).all { board.getPiece(it) == null } -> {
                        val kingSideRook = board.getPiece(7) as? Rook
                        if (kingSideRook != null &&
                            kingSideRook.isFirstMove &&
                            kingSideRook.pieceType == PieceType.ROOK &&
                            (5..6).all { calculateAttacksOnTile(it, opponentLegals).isEmpty() } &&
                            !BoardUtils.isKingPawnTrap(board, playerKing, 12)
                        ) {
                            add(KingSideCastleMove(board, playerKing, 6, kingSideRook, kingSideRook.piecePosition, 5))
                        }
                    }
                    // black queen-side castle
                    (1..3).all { board.getPiece(it) == null } -> {
                        val queenSideRook = board.getPiece(0) as? Rook
                        if (queenSideRook != null &&
                            queenSideRook.isFirstMove &&
                            queenSideRook.pieceType == PieceType.ROOK &&
                            (2..3).all { calculateAttacksOnTile(it, opponentLegals).isEmpty() } &&
                            !BoardUtils.isKingPawnTrap(board, playerKing, 12)
                        ) {
                            add(QueenSideCastleMove(board, playerKing, 2, queenSideRook, queenSideRook.piecePosition, 3))
                        }
                    }
                }
            }
        }
    }

    override fun toString() = Alliance.BLACK.toString()
}