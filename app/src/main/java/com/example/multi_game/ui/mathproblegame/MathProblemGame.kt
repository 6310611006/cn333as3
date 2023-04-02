package com.example.multi_game.ui.mathproblegame

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlin.random.Random


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MathGameApp(navController: NavHostController) {
    var score by remember { mutableStateOf(0) }
    var currentProblem by remember { mutableStateOf(generateMathProblem()) }
    var currentAnswer by remember { mutableStateOf("") }
    var remainingTime by remember { mutableStateOf(5000L) }
    var isGameOver by remember { mutableStateOf(false) }

    LaunchedEffect(remainingTime) {
        while (remainingTime > 0 && !isGameOver) {
            delay(500L)
            remainingTime -= 500L
        }
        if (!isGameOver) {
            isGameOver = true
        }
    }


        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            if (isGameOver) {
                GameOverScreen(score = score, onRestart = {
                    score = 0
                    currentProblem = generateMathProblem()
                    currentAnswer = ""
                    remainingTime = 5000L
                    isGameOver = false
                })
            } else {
                MathProblemScreen(
                    navController = navController,
                    problem = currentProblem.first,
                    answer = currentAnswer,
                    onAnswerChanged = { currentAnswer = it },
                    onSubmit = {
                        if (currentAnswer.isNotEmpty()) {
                            val answer = currentAnswer.toInt()
                            if (answer == currentProblem.second) {
                                score++
                                currentProblem = generateMathProblem()
                                currentAnswer = ""
                                remainingTime = 5000L
                            } else {
                                isGameOver = true
                            }
                        }
                    },
                    remainingTime = remainingTime,
                    onTimeExpired = { isGameOver = true }
                )
            }

        }
    }


@Composable
fun MathProblemScreen(navController: NavHostController,
    problem: String,
    answer: String,
    onAnswerChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    remainingTime: Long,
    onTimeExpired: () -> Unit
)
{

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(120.dp))
        Text(text = problem, style = MaterialTheme.typography.h4)
        OutlinedTextField(
            value = answer,
            onValueChange = onAnswerChanged,
            label = { Text("Answer") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(top = 16.dp)
        )

            Button(
                onClick = onSubmit,
                enabled = answer.isNotEmpty(),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Submit")
            }
            Spacer(Modifier.width(10.dp))
            Button(
                onClick = { navController.navigate("main_screen") },
            )
            {
                Text(
                    text = "Cancel",
                )
            }

            Text(
                text = "Time remaining: ${remainingTime / 1000} seconds",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 16.dp)
            )
            if (remainingTime <= 0) {
                onTimeExpired()
            }
            Spacer(Modifier.width(10.dp))

        }
    }

@Composable
fun GameOverScreen(score: Int, onRestart: () -> Unit) {
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "Game Over", style = MaterialTheme.typography.h4)
        Text(text = "Final Score: $score", style = MaterialTheme.typography.h5, modifier = Modifier.padding(top = 16.dp))
        Button(onClick = onRestart, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Restart")
        }

    }
}

fun generateMathProblem(): Pair<String, Int> {
    val a = Random.nextInt(1, 11)
    val b = Random.nextInt(1, 11)
    val problem = when (Random.nextInt(4)) {
        0 -> Pair("$a + $b", a + b)
        1 -> Pair("$a - $b", a - b)
        2 -> Pair("$a * $b", a * b)
        else -> Pair("$a / $b", a / b)
    }
    return problem
}





