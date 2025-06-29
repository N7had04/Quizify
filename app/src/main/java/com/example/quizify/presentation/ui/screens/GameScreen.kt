package com.example.quizify.presentation.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.example.quizify.data.model.Question
import com.example.quizify.presentation.ui.components.QuitDialog
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameScreen(
    questions: List<Question>,
    timer: MutableIntState,
    description: MutableState<String>,
    selectedAnswer: MutableState<String>,
    currentQuestion: MutableIntState,
    score: MutableIntState,
    showQuitDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onQuit: () -> Unit,
    navigateToEnd: (Int) -> Unit
) {
    var timerJob by remember { mutableStateOf<Job?>(null) }
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    BackHandler {
        showQuitDialog.value = true
    }

    if (showQuitDialog.value) {
        QuitDialog(score, currentQuestion, showQuitDialog, onQuit)
    }

    if (questions.isEmpty()) return

    val currentQ = questions[currentQuestion.intValue]
    val shuffledAnswers = remember(currentQuestion.intValue) {
        (currentQ.incorrectAnswers + currentQ.correctAnswer).shuffled()
    }

    LaunchedEffect(currentQuestion.intValue) {
        timer.intValue = 30
        selectedAnswer.value = ""
        description.value = HtmlCompat.fromHtml(currentQ.question, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

        timerJob = launch {
            while (timer.intValue > 0 && selectedAnswer.value.isEmpty()) {
                delay(1000)
                timer.intValue--
            }
        }

        while (selectedAnswer.value.isEmpty() && timer.intValue > 0) {
            delay(100)
        }

        timerJob?.cancel()

        delay(3000)

        if (currentQuestion.intValue < questions.lastIndex) {
            currentQuestion.intValue++
        } else {
            currentQuestion.intValue = 0
            navigateToEnd(score.intValue)
            score.intValue = 0
        }
    }

    Box(
        modifier = modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(
                Color(0xFF2C1788),
                Color(0xFF1A4A92),
                Color(0xFF1A7792),
                Color(0xFF1A4A92),
                Color(0xFF2C1788)
            ))
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { showQuitDialog.value = true }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Quit",
                        tint = Color.White
                    )
                }

                Text(
                    text = "Question ${currentQuestion.intValue + 1}",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            ) {
                LinearProgressIndicator(
                    progress = { timer.intValue.toFloat() / 30f },
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF0C828),
                    trackColor = Color.Transparent
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = timer.intValue.toString(),
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(Modifier.height(30.dp))

            Text(
                text = description.value,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 40.sp,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(Modifier.height(30.dp))

            shuffledAnswers.forEach { answer ->
                val backgroundColor = when {
                    selectedAnswer.value == answer && answer == currentQ.correctAnswer -> Color.Green
                    selectedAnswer.value.isNotEmpty() && selectedAnswer.value != answer && answer == currentQ.correctAnswer -> Color.Green
                    selectedAnswer.value == answer && answer != currentQ.correctAnswer -> Color.Red
                    answer == currentQ.correctAnswer && timer.intValue == 0 -> Color.Green
                    else -> Color(0xFF97108C)
                }

                Button(
                    onClick = {
                        if (selectedAnswer.value.isEmpty()) {
                            selectedAnswer.value = answer
                            if (answer == currentQ.correctAnswer) {
                                score.intValue++
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
                ) {
                    Text(
                        text = HtmlCompat.fromHtml(answer, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    val activity = context as? Activity

    DisposableEffect(Unit) {
        val originalOrientation = activity?.requestedOrientation
        activity?.requestedOrientation = orientation

        onDispose {
            activity?.requestedOrientation = originalOrientation ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}