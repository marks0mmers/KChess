package com.chess

import androidx.compose.desktop.AppManager
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.KeyStroke
import androidx.compose.ui.window.Menu
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuItem
import com.chess.engine.Alliance
import com.chess.engine.Alliance.*
import com.chess.engine.board.Board
import com.chess.engine.board.move.Move
import com.chess.gui.BoardDirection
import com.chess.gui.BoardView
import com.chess.gui.MoveView

@ExperimentalFoundationApi
fun main() = Window(title = "Chess", size = IntSize(600, 600)) {
    System.setProperty("apple.laf.useScreenMenuBar", "true")

    var board by mutableStateOf(Board.STANDARD_BOARD)
    val boardDirection by mutableStateOf(BoardDirection.NORMAL)
    var moves by remember { mutableStateOf(listOf<Move>()) }

    // hack to set menu bar within the current window
    AppManager.windows.first().setMenuBar(
        MenuBar(
            Menu(
                name = "File",
            ),
            Menu(
                name = "Options",
                MenuItem(
                    name = "New Game",
                    onClick = { board = Board.STANDARD_BOARD },
                    shortcut = KeyStroke(Key.P)
                ),
                MenuItem(
                    name = "Undo",
                    onClick = {
                        val lastMove = moves.last()
                        moves = moves.dropLast(1)
                        board = board.currentPlayer.unMakeMove(lastMove).toBoard
                    },
                    shortcut = KeyStroke(Key.M)
                )
            )
        )
    )

    MaterialTheme {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BoardView(
                board = board,
                boardDirection = boardDirection,
                setBoard = { newBoard, move ->
                    moves = moves + move
                    board = newBoard
                }
            )
            MoveView(
                moves = moves
            )
        }
    }
}