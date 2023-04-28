package com.chess.gui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chess.engine.player.PlayerType

@Composable fun GameSetupView(
    whiteType: PlayerType,
    blackType: PlayerType,
    initialDepth: Int,
    hideView: (PlayerType, PlayerType, Int) -> Unit
) {
    var whitePlayerType by remember { mutableStateOf(whiteType) }
    var blackPlayerType by remember { mutableStateOf(blackType) }
    var searchDepth by remember { mutableStateOf(initialDepth) }

    Dialog(
        title = "Game Config",
        onCloseRequest = { hideView(whitePlayerType, blackPlayerType, searchDepth) },
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Column {
                Text("White", style = TextStyle(fontWeight = FontWeight.Bold))
                Row {
                    RadioButton(
                        selected = whitePlayerType == PlayerType.HUMAN,
                        onClick = { whitePlayerType = PlayerType.HUMAN }
                    )
                    Text(PlayerType.HUMAN.toString())
                }
                Row {
                    RadioButton(
                        selected = whitePlayerType == PlayerType.COMPUTER,
                        onClick = { whitePlayerType = PlayerType.COMPUTER }
                    )
                    Text(PlayerType.COMPUTER.toString())
                }

                Text("Black", style = TextStyle(fontWeight = FontWeight.Bold))
                Row {
                    RadioButton(
                        selected = blackPlayerType == PlayerType.HUMAN,
                        onClick = { blackPlayerType = PlayerType.HUMAN }
                    )
                    Text(PlayerType.HUMAN.toString())
                }
                Row {
                    RadioButton(
                        selected = blackPlayerType == PlayerType.COMPUTER,
                        onClick = { blackPlayerType = PlayerType.COMPUTER }
                    )
                    Text(PlayerType.COMPUTER.toString())
                }
            }
            Column {
                OutlinedTextField(
                    label = { Text("Search") },
                    value = searchDepth.toString(),
                    onValueChange = {
                        if (it.toIntOrNull() != null) searchDepth = it.toInt()
                    },
                )
            }
        }
    }
}