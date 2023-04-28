package com.chess

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.chess.engine.board.Board
import com.chess.engine.board.move.Move
import com.chess.engine.player.PlayerType
import com.chess.engine.player.ai.MoveStrategy
import com.chess.engine.player.ai.StandardBoardEvaluator
import com.chess.engine.player.ai.StockAlphaBeta
import com.chess.gui.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun main() = application {
    System.setProperty("apple.laf.useScreenMenuBar", "true")

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var board by mutableStateOf(Board.STANDARD_BOARD)
    val boardDirection by mutableStateOf(BoardDirection.NORMAL)
    var moves by remember { mutableStateOf(listOf<Move>()) }
    var isConfigDialogOpen by remember { mutableStateOf(false) }

    var whitePlayerType by remember { mutableStateOf(PlayerType.HUMAN) }
    var blackPlayerType by remember { mutableStateOf(PlayerType.COMPUTER) }
    var searchDepth by remember { mutableStateOf(5) }
    val computer: MoveStrategy by derivedStateOf { StockAlphaBeta(searchDepth) }

    fun setBoard(newBoard: Board, move: Move) {
        moves += move
        board = newBoard

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                if ((board.currentPlayer.alliance.isWhite && whitePlayerType.isComputer) || (board.currentPlayer.alliance.isBlack && blackPlayerType.isComputer)) {
                    val computerMove = computer.execute(board)
                    moves += computerMove
                    board = computerMove.execute()
                }
            }
        }
    }

    Window(
        title = "Chess",
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 600.dp, height = 600.dp)
    ) {
        MenuBar {
            Menu("File") {

            }
            Menu("Options") {
                Item("New Game") {
                    board = Board.STANDARD_BOARD
                    moves = listOf()
                    coroutineScope.launch {
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
                            setBoard = ::setBoard
                        )
                        MoveView(
                            moves = moves
                        )
                    }
                }
                if (isConfigDialogOpen) GameSetupView(
                    whitePlayerType,
                    blackPlayerType,
                    searchDepth,
                    hideView = { whiteType, blackType, depth ->
                        isConfigDialogOpen = false
                        whitePlayerType = whiteType
                        blackPlayerType = blackType
                        searchDepth = depth
                    }
                )
            }
        }
    }
}