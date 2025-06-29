package com.example.quizify.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizify.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPortrait(
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
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    sheetState: SheetState
) {
    if (showSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showSheet.value = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(Modifier.padding(24.dp)) {
                Text("Profile", style = MaterialTheme.typography.headlineSmall)

                Spacer(Modifier.height(16.dp))

                Text("Name: ${user.name} ${user.surname}")
                Text("Email: ${user.mail}")

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        showSheet.value = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Logout")
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(
                Color(0xFF6E58D9),
                Color(0xFF9082D4),
                Color(0xFFC089CF),
                Color(0xFFC06CD3),
                Color(0xFFC089CF),
                Color(0xFF9082D4),
                Color(0xFF6E58D9)))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text("Quizify", style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 60.sp),
                fontStyle = FontStyle.Italic
            )

            Spacer(Modifier.height(100.dp))

            Text(
                "Please select the difficulty and category for the game:",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(60.dp))

            ExposedDropdownMenuBox(
                expanded = expanded.value,
                onExpandedChange = { expanded.value = !expanded.value }
            ) {
                OutlinedTextField(
                    value = selectedDifficulty.value,
                    onValueChange = {},
                    textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                    label = { Text(text = "Difficulty", fontWeight = FontWeight.Bold) },
                    readOnly = true,
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    difficultyOptions.keys.forEach { difficulty ->
                        DropdownMenuItem(
                            text = { Text(difficulty) },
                            onClick = {
                                selectedDifficulty.value = difficulty
                                expanded.value = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded2.value,
                onExpandedChange = { expanded2.value = !expanded2.value }
            ) {
                OutlinedTextField(
                    value = selectedCategory.value,
                    onValueChange = {},
                    textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                    label = { Text(text = "Category", fontWeight = FontWeight.Bold) },
                    readOnly = true,
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded2.value)
                    }
                )

                ExposedDropdownMenu(
                    expanded = expanded2.value,
                    onDismissRequest = { expanded2.value = false }
                ) {
                    categoryOptions.keys.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory.value = category
                                expanded2.value = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(60.dp))

            Button(
                onClick = { onStartGame(
                    difficultyOptions.getOrDefault(selectedDifficulty.value, "easy"),
                    categoryOptions.getOrDefault(selectedCategory.value, 9))
                          Log.i("MYTAG", "Difficulty: ${difficultyOptions.getOrDefault(selectedDifficulty.value, "easy")}, Category: ${categoryOptions.getOrDefault(selectedCategory.value, 9)}")},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E6F1B))
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Start Game")
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = {
                showSheet.value = true
                coroutineScope.launch { sheetState.show() }
            }) {
                Icon(Icons.Default.Person, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Profile",
                    fontSize = 16.sp
                )
            }
        }
    }
}