package com.example.quizify.presentation.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.quizify.data.model.User
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    user: User,
    onStartGame: (String, Int) -> Unit,
    onLogout: () -> Unit,
    difficultyOptions: Map<String, String>,
    selectedDifficulty: MutableState<String>,
    categoryOptions: Map<String, Int>,
    selectedCategory: MutableState<String>,
    expanded: MutableState<Boolean>,
    expanded2: MutableState<Boolean>,
    showSheet: MutableState<Boolean>,
    isPortrait: Boolean,
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    sheetState: SheetState
) {
    if (isPortrait) {
        HomeScreenPortrait(user, onStartGame, onLogout, difficultyOptions, selectedDifficulty, categoryOptions,
            selectedCategory, expanded, expanded2, showSheet, modifier, coroutineScope, sheetState)
    } else {
        HomeScreenLandscape(user, onStartGame, onLogout, difficultyOptions, selectedDifficulty, categoryOptions,
            selectedCategory, expanded, expanded2, showSheet, modifier, coroutineScope, sheetState)
    }
}