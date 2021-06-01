package com.chess

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.chess.engine.board.Board
import com.chess.engine.board.move.Move
import com.chess.engine.player.ai.StandardBoardEvaluator
import com.chess.gui.BoardDirection
import com.chess.gui.BoardView
import com.chess.gui.GameSetupView
import com.chess.gui.MoveView
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
fun main() = application {
    System.setProperty("apple.laf.useScreenMenuBar", "true")

    val scaffoldState = rememberScaffoldState()
    val snackbarCoroutineState = rememberCoroutineScope()

    var board by mutableStateOf(Board.STANDARD_BOARD)
    val boardDirection by mutableStateOf(BoardDirection.NORMAL)
    var moves by remember { mutableStateOf(listOf<Move>()) }
    var isConfigDialogOpen by remember { mutableStateOf(false) }

    Window(
        title = "Chess",
        state = rememberWindowState(size = WindowSize(600.dp, 600.dp))
    ) {
        MenuBar {
            Menu("File") {

            }
            Menu("Options") {
                Item("New Game") {
                    board = Board.STANDARD_BOARD
                    snackbarCoroutineState.launch {
                        scaffoldState.snackbarHostState.showSnackbar("New Game Started")
                    }
                }
                Item("Evaluate Board") {
                    println(StandardBoardEvaluator.evaluationDetails(board, 1))
                }
                Item("Undo") {
                    val lastMove = moves.last()
                    moves = moves.dropLast(1)
                    board = board.currentPlayer.unMakeMove(lastMove).toBoard
                }
                Item("Setup Game") {
                    isConfigDialogOpen = true
                }
            }
        }

        MaterialTheme {
            Scaffold(scaffoldState = scaffoldState) {
                Column(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(10.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
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
                if (isConfigDialogOpen) GameSetupView(
                    hideView = {
                        isConfigDialogOpen = false
                    }
                )
            }
        }
    }
}