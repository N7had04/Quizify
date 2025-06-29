package com.example.quizify.presentation.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.quizify.R
import com.example.quizify.presentation.viewmodel.AuthState

@Composable
fun LoginScreenPortrait(
    authState: AuthState,
    login: (String, String) -> Unit,
    navigate: (String) -> Unit,
    email: MutableState<String>,
    password: MutableState<String>,
    passwordVisibility: MutableState<Boolean>,
    context: Context,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                navigate("home")
            }
            is AuthState.Error -> {
                Toast.makeText(context, authState.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Box(modifier = modifier.fillMaxSize().background(Color(0xFF6E58D9)), contentAlignment = Alignment.Center) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.ic_app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(bottom = 24.dp)
            )

            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                maxLines = 1,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                trailingIcon = {
                    val icon = if (passwordVisibility.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                        Icon(icon, "")
                    }
                },
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Button(
                onClick = { login(email.value, password.value) },
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 30.dp),
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Text("Login")
            }

            TextButton(
                onClick = { navigate("signup") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF6E58D9))
            ) {
                Text("Don't have an account? Sign up")
            }
        }
    }
}

