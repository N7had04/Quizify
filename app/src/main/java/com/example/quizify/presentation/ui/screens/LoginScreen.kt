package com.example.quizify.presentation.ui.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.quizify.presentation.viewmodel.AuthState

@Composable
fun LoginScreen(
    authState: AuthState,
    login: (String, String) -> Unit,
    navigate: (String) -> Unit,
    email: MutableState<String>,
    password: MutableState<String>,
    passwordVisibility: MutableState<Boolean>,
    context: Context,
    isPortrait: Boolean,
    modifier: Modifier = Modifier,
) {
    if (isPortrait) {
        LoginScreenPortrait(authState, login, navigate, email, password, passwordVisibility, context, modifier)
    } else {
        LoginScreenLandscape(authState, login, navigate, email, password, passwordVisibility, context, modifier)
    }
}