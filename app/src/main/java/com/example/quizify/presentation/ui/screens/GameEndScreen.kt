package com.example.quizify.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GameEndScreen(
    score: Int,
    isPortrait: Boolean,
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit
) {
    if (isPortrait) {
        GameEndScreenPortrait(
            score = score,
            modifier = modifier,
            navigateToHome = navigateToHome
        )
    } else {
        GameEndScreenLandscape(
            score = score,
            modifier = modifier,
            navigateToHome = navigateToHome
        )
    }
}