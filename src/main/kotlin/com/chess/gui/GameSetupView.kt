package com.chess.gui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chess.engine.player.PlayerType

@ExperimentalComposeUiApi
@Composable fun GameSetupView(hideView: () -> Unit) {
    var whitePlayerType by remember { mutableStateOf(PlayerType.HUMAN) }
    var blackPlayerType by remember { mutableStateOf(PlayerType.HUMAN) }
    var searchDepth by remember { mutableStateOf(6) }

    Dialog(
        title = "Game Config",
        onCloseRequest = { hideView() },
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