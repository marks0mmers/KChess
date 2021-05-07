package com.chess.engine.player

import com.chess.engine.Alliance
import com.chess.engine.board.Board
import com.chess.engine.board.move.Move
import com.chess.engine.board.move.MoveStatus
import com.chess.engine.board.move.MoveTransition
import com.chess.engine.pieces.King
import com.chess.engine.pieces.Piece
import com.chess.engine.pieces.PieceType

sealed class Player(
    val board: Board,
    private val playerLegals: Collection<Move>,
    private val opponentLegals: Collection<Move>
) {
    companion object {
        fun calculateAttacksOnTile(tile: Int, moves: Collection<Move>) = moves.filter { it.destinationCoordinate == tile }
    }

    val isInCheck = calculateAttacksOnTile(playerKing.piecePosition, opponentLegals).isNotEmpty()
    val hasEscapeMoves = legalMoves.any { makeMove(it).moveStatus.isDone }
    val hasCastleOpportunities = !isInCheck && !playerKing.isCastled && (playerKing.kingSideCastleCapable || playerKing.queenSideCastleCapable)
    val isInCheckMate = isInCheck && !hasEscapeMoves

    val playerKing: King
        get() = activePieces.find { it.pieceType == PieceType.KING } as King

    val legalMoves: Collection<Move>
        get() = playerLegals + calculateKingCastles(playerLegals, opponentLegals)

    abstract val activePieces: Collection<Piece>
    abstract val alliance: Alliance
    abstract val opponent: Player
    abstract fun calculateKingCastles(playerLegals: Collection<Move>, opponentLegals: Collection<Move>): Collection<Move>

    fun makeMove(move: Move) = when {
        !legalMoves.contains(move) -> MoveTransition(board, board, move, MoveStatus.ILLEGAL_MOVE)
        else -> {
            val transitionalBoard = move.execute()
            when {
                transitionalBoard.currentPlayer.opponent.isInCheck ->
                    MoveTransition(board, board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK)
                else ->
                    MoveTransition(board, transitionalBoard, move, MoveStatus.DONE)
            }
        }
    }

    fun unMakeMove(move: Move) = MoveTransition(board, move.undo(), move, MoveStatus.DONE)
}