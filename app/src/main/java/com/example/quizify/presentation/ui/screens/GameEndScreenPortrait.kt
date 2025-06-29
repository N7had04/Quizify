package com.example.quizify.presentation.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizify.R

@Composable
fun GameEndScreenPortrait(
    score: Int,
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit
) {
    BackHandler {
        navigateToHome()
    }

    Box(
        modifier = modifier.fillMaxSize().background(Color(0xFF5857A4)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(30.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = when (score) {
                        in 13..15 -> "Genius"
                        in 11..12 -> "Quiz Master"
                        in 9..10 -> "Knowledgeable"
                        in 7..8 -> "Sharp Thinker"
                        in 5..6 -> "Learner"
                        in 3..4 -> "Explorer"
                        in 1..2 -> "Beginner"
                        else -> "Try Again"
                    },
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = when (score) {
                        in 13..15 -> "Unstoppable! You mastered the quiz!"
                        in 11..12 -> "So close to perfection!"
                        in 9..10 -> "Well done, you really know your stuff!"
                        in 7..8 -> "Solid performance!"
                        in 5..6 -> "Good effort. Keep going!"
                        in 3..4 -> "You're on the path to greatness!"
                        in 1..2 -> "Every expert was once a beginner!"
                        else -> "Let’s give it another shot!"
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Your Score %.2f / 100.00".format(score.toFloat() / 15 * 100),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Image(
                    painter = painterResource(R.drawable.ic_quizify_cup_icon),
                    contentDescription = "Cup Icon"
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Correct Answers",
                            fontSize = 16.sp
                        )

                        Text(
                            text = "$score",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Wrong Answers",
                            fontSize = 16.sp
                        )

                        Text(
                            text = "${15 - score}",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Quizify",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { navigateToHome() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDBB72C)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Go Home",
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}
