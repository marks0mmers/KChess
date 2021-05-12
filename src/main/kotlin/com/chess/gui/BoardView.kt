package com.chess.gui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chess.engine.board.Board
import com.chess.engine.board.BoardUtils
import com.chess.engine.board.move.Move
import com.chess.engine.board.move.util.MoveFactory

@ExperimentalFoundationApi
@Composable fun BoardView(board: Board, boardDirection: BoardDirection, setBoard: (Board, Move) -> Unit) {
    var selectedTile by remember { mutableStateOf<Int?>(null) }
    val selectedPiece by remember(selectedTile) {
        derivedStateOf {
            selectedTile?.let {
                val piece = board.getPiece(it)
                if (piece?.pieceAlliance == board.currentPlayer.alliance) piece else null
            }
        }
    }
    val legalMoves by remember(selectedPiece, board) {
        derivedStateOf {
            selectedPiece?.calculateLegalMoves(board)
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 100.dp),
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(BoardUtils.NUM_TILES_PER_ROW)
        ) {
            (when (boardDirection) {
                BoardDirection.NORMAL -> BoardUtils.TILES_RANGE.toList()
                BoardDirection.FLIPPED -> BoardUtils.TILES_RANGE.reversed()
            }).forEach { tileId ->
                item {
                    TileView(
                        tileId = tileId,
                        piece = board.getPiece(tileId),
                        isLegalMove = legalMoves?.map { it.destinationCoordinate }?.contains(tileId),
                        isSelected = selectedTile == tileId,
                        onClick = { clickedTile ->
                            when {
                                selectedTile != null -> selectedTile =
                                    when (val move = MoveFactory.createMove(board, selectedTile!!, clickedTile)) {
                                        null -> clickedTile
                                        else -> {
                                            val transition = board.currentPlayer.makeMove(move)
                                            if (transition.moveStatus.isDone) {
                                                setBoard(transition.toBoard, move)
                                            }
                                            null
                                        }
                                    }
                                else -> selectedTile = when (selectedTile == clickedTile) {
                                    true -> null
                                    false -> clickedTile
                                }
                            }
                        }
                    )
                }
            }
        }
    }

}