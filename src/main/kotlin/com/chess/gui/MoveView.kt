package com.chess.gui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chess.engine.Alliance.WHITE
import com.chess.engine.board.move.Move

@Composable fun MoveView(moves: List<Move>) {
    val (whiteMoves, blackMoves) = remember(moves) {
        moves.partition { it.movedPiece?.pieceAlliance == WHITE }
    }

    Card {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .defaultMinSize(400.dp, 100.dp),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Text("White: ", modifier = Modifier.defaultMinSize(minWidth = 60.dp))
                LazyRow {
                    items(whiteMoves.size) { i ->
                        if (i > 0) Text(", ")
                        Text(whiteMoves[i].toString())
                    }
                }
            }
            Row(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Text("Black: ", modifier = Modifier.defaultMinSize(minWidth = 60.dp))
                LazyRow {
                    items(blackMoves.size) { i ->
                        if (i > 0) Text(", ")
                        Text(blackMoves[i].toString())
                    }
                }
            }
        }
    }
}
