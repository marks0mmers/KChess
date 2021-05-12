package com.chess.gui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.imageResource
import com.chess.engine.board.BoardUtils
import com.chess.engine.pieces.Piece

private val LIGHT_COLOR = Color(0xFFFFFACD)
private val DARK_COLOR = Color(0xFF593E1A)

private fun getTileColor(tileId: Int): Color {
    return when {
        BoardUtils.FIRST_ROW[tileId] ||
        BoardUtils.THIRD_ROW[tileId] ||
        BoardUtils.FIFTH_ROW[tileId] ||
        BoardUtils.SEVENTH_ROW[tileId] -> if (tileId % 2 == 0) LIGHT_COLOR else DARK_COLOR

        BoardUtils.SECOND_ROW[tileId] ||
        BoardUtils.FOURTH_ROW[tileId] ||
        BoardUtils.SIXTH_ROW[tileId] ||
        BoardUtils.EIGHTH_ROW[tileId] -> if (tileId % 2 != 0) LIGHT_COLOR else DARK_COLOR
        else -> error("Invalid TileID $tileId")
    }
}

@Composable fun TileView(
    tileId: Int,
    piece: Piece?,
    isSelected: Boolean,
    onClick: (Int) -> Unit,
    isLegalMove: Boolean? = false,
) {
    val background = remember { getTileColor(tileId) }
    Column(
        modifier = Modifier
            .height(50.dp)
            .background(background)
            .clickable { onClick(tileId) }
            .then(if (isSelected) Modifier.border(2.dp, if (piece != null) Color.Cyan else Color.Gray) else Modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        piece?.let { p ->
            Image(
                imageResource("art/simple/${p.pieceAlliance.toString().substring(0, 1)}$p.gif"),
                p.toString()
            )
        }
        when (isLegalMove) {
            true -> Image(
                imageResource("art/misc/green_dot.png"),
                contentDescription = "legal-move"
            )
        }
    }
}