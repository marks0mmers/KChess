package com.chess.engine

import com.chess.engine.board.BoardUtils
import com.chess.engine.player.BlackPlayer
import com.chess.engine.player.Player
import com.chess.engine.player.WhitePlayer

enum class Alliance {
    WHITE {
        override val isWhite = true
        override val isBlack = false
        override val direction = Alliance.UP_DIRECTION
        override val oppositeDirection = Alliance.DOWN_DIRECTION
        override fun toString() = "White"

        override fun pawnBonus(position: Int) = Alliance.WHITE_PAWN_PREFERRED_COORDINATES[position]

        override fun knightBonus(position: Int) = Alliance.WHITE_KNIGHT_PREFERRED_COORDINATES[position]

        override fun bishopBonus(position: Int) = Alliance.WHITE_BISHOP_PREFERRED_COORDINATES[position]

        override fun rookBonus(position: Int) = Alliance.WHITE_ROOK_PREFERRED_COORDINATES[position]

        override fun queenBonus(position: Int) = Alliance.WHITE_QUEEN_PREFERRED_COORDINATES[position]

        override fun kingBonus(position: Int) = Alliance.WHITE_KING_PREFERRED_COORDINATES[position]

        override fun isPawnPromotionSquare(position: Int) = BoardUtils.FIRST_ROW[position]

        override fun choosePlayerByAlliance(whitePlayer: WhitePlayer, blackPlayer: BlackPlayer) = whitePlayer
    },
    BLACK {
        override val isWhite = false
        override val isBlack = true
        override val direction = Alliance.DOWN_DIRECTION
        override val oppositeDirection = Alliance.UP_DIRECTION
        override fun toString() = "Black"

        override fun pawnBonus(position: Int) = Alliance.BLACK_PAWN_PREFERRED_COORDINATES[position]

        override fun knightBonus(position: Int) = Alliance.BLACK_KNIGHT_PREFERRED_COORDINATES[position]

        override fun bishopBonus(position: Int) = Alliance.BLACK_BISHOP_PREFERRED_COORDINATES[position]

        override fun rookBonus(position: Int) = Alliance.BLACK_ROOK_PREFERRED_COORDINATES[position]

        override fun queenBonus(position: Int) = Alliance.BLACK_QUEEN_PREFERRED_COORDINATES[position]

        override fun kingBonus(position: Int) = Alliance.BLACK_KING_PREFERRED_COORDINATES[position]

        override fun isPawnPromotionSquare(position: Int) = BoardUtils.EIGHTH_ROW[position]

        override fun choosePlayerByAlliance(whitePlayer: WhitePlayer, blackPlayer: BlackPlayer) = blackPlayer
    };

    abstract val isWhite: Boolean
    abstract val isBlack: Boolean
    abstract val direction: Int
    abstract val oppositeDirection: Int
    abstract fun pawnBonus(position: Int): Int
    abstract fun knightBonus(position: Int): Int
    abstract fun bishopBonus(position: Int): Int
    abstract fun rookBonus(position: Int): Int
    abstract fun queenBonus(position: Int): Int
    abstract fun kingBonus(position: Int): Int
    abstract fun isPawnPromotionSquare(position: Int): Boolean
    abstract fun choosePlayerByAlliance(whitePlayer: WhitePlayer, blackPlayer: BlackPlayer): Player
    abstract override fun toString(): String

    companion object {
        private const val UP_DIRECTION = -1
        private const val DOWN_DIRECTION = 1

        private val WHITE_PAWN_PREFERRED_COORDINATES = intArrayOf(
            0, 0, 0, 0, 0, 0, 0, 0,
            75, 75, 75, 75, 75, 75, 75, 75,
            25, 25, 29, 29, 29, 29, 25, 25,
            5, 5, 10, 55, 55, 10, 5, 5,
            0, 0, 0, 20, 20, 0, 0, 0,
            5, -5, -10, 0, 0, -10, -5, 5,
            5, 10, 10, -20, -20, 10, 10, 5,
            0, 0, 0, 0, 0, 0, 0, 0
        )

        private val BLACK_PAWN_PREFERRED_COORDINATES = intArrayOf(
            0, 0, 0, 0, 0, 0, 0, 0,
            5, 10, 10, -20, -20, 10, 10, 5,
            5, -5, -10, 0, 0, -10, -5, 5,
            0, 0, 0, 20, 20, 0, 0, 0,
            5, 5, 10, 55, 55, 10, 5, 5,
            25, 25, 29, 29, 29, 29, 25, 25,
            75, 75, 75, 75, 75, 75, 75, 75,
            0, 0, 0, 0, 0, 0, 0, 0
        )

        private val WHITE_KNIGHT_PREFERRED_COORDINATES = intArrayOf(
            -50, -40, -30, -30, -30, -30, -40, -50,
            -40, -20, 0, 0, 0, 0, -20, -40,
            -30, 0, 10, 15, 15, 10, 0, -30,
            -30, 5, 15, 20, 20, 15, 5, -30,
            -30, 0, 15, 20, 20, 15, 0, -30,
            -30, 5, 10, 15, 15, 10, 5, -30,
            -40, -20, 0, 5, 5, 0, -20, -40,
            -50, -40, -30, -30, -30, -30, -40, -50
        )

        private val BLACK_KNIGHT_PREFERRED_COORDINATES = intArrayOf(
            -50, -40, -30, -30, -30, -30, -40, -50,
            -40, -20, 0, 5, 5, 0, -20, -40,
            -30, 5, 10, 15, 15, 10, 5, -30,
            -30, 0, 15, 20, 20, 15, 0, -30,
            -30, 5, 15, 20, 20, 15, 5, -30,
            -30, 0, 10, 15, 15, 10, 0, -30,
            -40, -20, 0, 0, 0, 0, -20, -40,
            -50, -40, -30, -30, -30, -30, -40, -50
        )

        private val WHITE_BISHOP_PREFERRED_COORDINATES = intArrayOf(
            -20, -10, -10, -10, -10, -10, -10, -20,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -10, 0, 5, 10, 10, 5, 0, -10,
            -10, 5, 5, 10, 10, 5, 5, -10,
            -10, 0, 10, 10, 10, 10, 0, -10,
            -10, 10, 10, 10, 10, 10, 10, -10,
            -10, 5, 0, 0, 0, 0, 5, -10,
            -20, -10, -10, -10, -10, -10, -10, -20
        )

        private val BLACK_BISHOP_PREFERRED_COORDINATES = intArrayOf(
            -20, -10, -10, -10, -10, -10, -10, -20,
            -10, 5, 0, 0, 0, 0, 5, -10,
            -10, 10, 10, 10, 10, 10, 10, -10,
            -10, 0, 10, 10, 10, 10, 0, -10,
            -10, 5, 5, 10, 10, 5, 5, -10,
            -10, 0, 5, 10, 10, 5, 0, -10,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -20, -10, -10, -10, -10, -10, -10, -20
        )

        private val WHITE_ROOK_PREFERRED_COORDINATES = intArrayOf(
            0, 0, 0, 0, 0, 0, 0, 0,
            5, 20, 20, 20, 20, 20, 20, 5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            0, 0, 0, 5, 5, 0, 0, 0
        )

        private val BLACK_ROOK_PREFERRED_COORDINATES = intArrayOf(
            0, 0, 0, 5, 5, 0, 0, 0,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            5, 20, 20, 20, 20, 20, 20, 5,
            0, 0, 0, 0, 0, 0, 0, 0
        )

        private val WHITE_QUEEN_PREFERRED_COORDINATES = intArrayOf(
            -20, -10, -10, -5, -5, -10, -10, -20,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -10, 0, 5, 5, 5, 5, 0, -10,
            -5, 0, 5, 5, 5, 5, 0, -5,
            0, 0, 5, 5, 5, 5, 0, -5,
            -10, 5, 5, 5, 5, 5, 0, -10,
            -10, 0, 5, 0, 0, 0, 0, -10,
            -20, -10, -10, -5, -5, -10, -10, -20
        )

        private val BLACK_QUEEN_PREFERRED_COORDINATES = intArrayOf(
            -20, -10, -10, -5, -5, -10, -10, -20,
            -10, 0, 5, 0, 0, 0, 0, -10,
            -10, 5, 5, 5, 5, 5, 0, -10,
            0, 0, 5, 5, 5, 5, 0, -5,
            0, 0, 5, 5, 5, 5, 0, -5,
            -10, 0, 5, 5, 5, 5, 0, -10,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -20, -10, -10, -5, -5, -10, -10, -20
        )

        private val WHITE_KING_PREFERRED_COORDINATES = intArrayOf(
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -20, -30, -30, -40, -40, -30, -30, -20,
            -10, -20, -20, -20, -20, -20, -20, -10,
            20, 20, 0, 0, 0, 0, 20, 20,
            20, 30, 10, 0, 0, 10, 30, 20
        )

        private val BLACK_KING_PREFERRED_COORDINATES = intArrayOf(
            20, 30, 10, 0, 0, 10, 30, 20,
            20, 20, 0, 0, 0, 0, 20, 20,
            -10, -20, -20, -20, -20, -20, -20, -10,
            -20, -30, -30, -40, -40, -30, -30, -20,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30
        )
    }
}