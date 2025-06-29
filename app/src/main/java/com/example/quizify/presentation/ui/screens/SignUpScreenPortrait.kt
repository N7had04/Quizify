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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun SignUpScreenPortrait(
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
    modifier: Modifier = Modifier
) {
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                navigate("login")
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
                value = name.value,
                onValueChange = {
                    name.value = it
                    nameError.value = false },
                label = { Text("Name") },
                maxLines = 1,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                isError = nameError.value
            )

            if (nameError.value) {
                Text(
                    text = "Name must be between 5 and 16 characters long and contain only letters",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }

            TextField(
                value = surname.value,
                onValueChange = {
                    surname.value = it
                    surnameError.value = false },
                label = { Text("Surname") },
                maxLines = 1,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                isError = surnameError.value
            )

            if (surnameError.value) {
                Text(
                    text = "Surname must be between 5 and 16 characters long and contain only letters",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }

            TextField(
                value = email.value,
                onValueChange = {
                    email.value = it
                    emailError.value = false },
                label = { Text("Email") },
                maxLines = 1,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                isError = emailError.value
            )

            if (emailError.value) {
                Text(
                    text = "Email is not valid",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }

            TextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                    passwordError.value = false},
                label = { Text("Password") },
                trailingIcon = {
                    val icon = if (passwordVisibility.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                        Icon(icon, "")
                    }
                },
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                isError = passwordError.value
            )

            if (passwordError.value) {
                Text(
                    text = "Password must be at least 8 characters long and contain at least one digit and one letter",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }

            TextField(
                value = password2.value,
                onValueChange = {
                    password2.value = it
                    password2Error.value = false},
                label = { Text("Confirm your password") },
                trailingIcon = {
                    val icon = if (passwordVisibility2.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    IconButton(onClick = { passwordVisibility2.value = !passwordVisibility2.value }) {
                        Icon(icon, "")
                    }
                },
                visualTransformation = if (passwordVisibility2.value) VisualTransformation.None else PasswordVisualTransformation(),
                maxLines = 1,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                isError = password2Error.value
            )

            if (password2Error.value) {
                Text(
                    text = "Passwords do not match",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 24.dp)
                )
            }

            Button(
                onClick = {
                    password2Error.value = password.value != password2.value
                    nameError.value = name.value.length > 16 || name.value.length < 5 ||
                            !name.value.matches(Regex("^[A-Za-z]+$"))
                    surnameError.value = surname.value.length > 16 || surname.value.length < 5 ||
                            !surname.value.matches(Regex("^[A-Za-z]+$"))
                    emailError.value = email.value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
                    passwordError.value = password.value.isEmpty() || password.value.length < 8 ||
                            !password.value.any { it.isDigit() } || !password.value.any { it.isLetter() }

                    if (!password2Error.value && !nameError.value && !surnameError.value && !emailError.value && !passwordError.value) {
                        signUp(name.value, surname.value, email.value, password.value)
                    } },
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 30.dp),
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Text("Sign Up")
            }
        }
    }
}