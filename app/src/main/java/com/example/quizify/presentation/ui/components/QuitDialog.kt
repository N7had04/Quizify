package com.example.quizify.presentation.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

@Composable
fun QuitDialog(
    showQuitDialog: MutableState<Boolean>,
    onQuit: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { showQuitDialog.value = false },
        title = { Text("Quit Game") },
        text = { Text("Are you sure you want to quit the game?") },
        confirmButton = {
            TextButton(onClick = {
                showQuitDialog.value = false
                onQuit()
            }) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = { showQuitDialog.value = false }) {
                Text("No")
            }
        },
        containerColor = Color(0xFFF0C828)
    )
}