package com.chess

import androidx.compose.desktop.Window
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.chess.engine.Alliance
import com.chess.engine.Alliance.*
import com.chess.engine.board.Board
import com.chess.engine.board.move.Move
import com.chess.gui.BoardDirection
import com.chess.gui.BoardView
import com.chess.gui.MoveView

@ExperimentalFoundationApi
fun main() = Window(title = "Chess", size = IntSize(600, 600)) {
    var board by mutableStateOf(Board.STANDARD_BOARD)
    var boardDirection by mutableStateOf(BoardDirection.NORMAL)
    var whiteMoves by remember { mutableStateOf(listOf<Move>()) }
    var blackMoves by remember { mutableStateOf(listOf<Move>()) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BoardView(
                board = board,
                boardDirection = boardDirection,
                setBoard = { newBoard, move ->
                    when(board.currentPlayer.alliance) {
                        WHITE -> whiteMoves = whiteMoves + move
                        BLACK -> blackMoves = blackMoves + move
                    }
                    board = newBoard
                }
            )
            MoveView(
                whiteMoves = whiteMoves,
                blackMoves = blackMoves
            )
        }
    }
}