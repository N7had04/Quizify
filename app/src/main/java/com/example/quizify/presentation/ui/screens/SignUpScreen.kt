package com.example.quizify.presentation.ui.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.quizify.presentation.viewmodel.AuthState

@Composable
fun SignUpScreen(
    authState: AuthState,
    signUp: (String, String, String, String) -> Unit,
    navigate: (String) -> Unit,
    name: MutableState<String>,
    nameError: MutableState<Boolean>,
    surname: MutableState<String>,
    surnameError: MutableState<Boolean>,
    email: MutableState<String>,
    emailError: MutableState<Boolean>,
    password: MutableState<String>,
    passwordError: MutableState<Boolean>,
    password2: MutableState<String>,
    password2Error: MutableState<Boolean>,
    passwordVisibility: MutableState<Boolean>,
    passwordVisibility2: MutableState<Boolean>,
    context: Context,
    isPortrait: Boolean,
    modifier: Modifier = Modifier
) {
    if (isPortrait) {
        SignUpScreenPortrait(authState, signUp, navigate, name, nameError, surname, surnameError, email,
            emailError, password, passwordError, password2, password2Error, passwordVisibility,
            passwordVisibility2, context, modifier)
    } else {
        SignUpScreenLandscape(authState, signUp, navigate, name, nameError, surname, surnameError, email,
            emailError, password, passwordError, password2, password2Error, passwordVisibility,
            passwordVisibility2, context, modifier)
    }
}