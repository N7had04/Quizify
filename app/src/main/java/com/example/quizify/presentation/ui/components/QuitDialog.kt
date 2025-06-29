package com.example.quizify.presentation.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

@Composable
fun QuitDialog(
    score: MutableState<Int>,
    currentQuestion: MutableState<Int>,
    showQuitDialog: MutableState<Boolean>,
    onQuit: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { showQuitDialog.value = false },
        title = { Text(text = "Quit Game", color = Color.Black) },
        text = { Text(text = "Are you sure you want to quit the game?", color = Color.Black) },
        confirmButton = {
            TextButton(onClick = {
                showQuitDialog.value = false
                score.value = 0
                currentQuestion.value = 0
                onQuit()
            }) {
                Text(text = "Yes", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = { showQuitDialog.value = false }) {
                Text(text = "No", color = Color.Black)
            }
        },
        containerColor = Color(0xFFF0C828)
    )
}