package com.chess.gui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chess.engine.Alliance.WHITE
import com.chess.engine.board.move.Move

@Composable fun MoveView(moves: List<Move>) {
    val (whiteMoves, blackMoves) = remember(moves) {
        moves.partition { it.movedPiece?.pieceAlliance == WHITE }
    }

    Card {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .defaultMinSize(100.dp, 400.dp),
        ) {
            Column(
                modifier = Modifier
                    .width(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "White",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFBFFD4))
                        .border(1.dp, Color.Black),
                )
                LazyColumn {
                    items(whiteMoves.size) { i ->
                        Text(whiteMoves[i].toString())
                    }
                }
            }
            Column(
                modifier = Modifier
                    .width(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Black",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFBFFD4))
                        .border(1.dp, Color.Black),
                )
                LazyColumn {
                    items(blackMoves.size) { i ->
                        Text(blackMoves[i].toString())
                    }
                }
            }
        }
    }
}
