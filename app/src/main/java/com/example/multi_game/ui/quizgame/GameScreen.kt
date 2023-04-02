package com.example.multi_game.ui.quizgame

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.multi_game.R

@Composable
fun GameStatus(
    questionCount: Int,
    score: Int,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier.height(100.dp))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(30.dp)
            .size(64.dp),
    ) {
        Text(
            text = stringResource(R.string.question_count, questionCount ),
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = "Score: $score",
            fontSize = 18.sp
        )
    }
}

@Composable
fun QuizScreen(gameViewModel: GameViewModel = viewModel(), onPlayAgain: () -> Unit,
               navController: NavController, question: Question?, score: Int, onAnswerSelected: (Int) -> Unit) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(

                painter = painterResource(id = R.drawable.party),
                contentDescription = stringResource(id = R.string.party_content_description)
            )
            Spacer(Modifier.height(30.dp))

            if (question != null) {
                Text(
                    text = question.question,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                val shuffledOptions = question.options.shuffled() // Shuffle the options
                shuffledOptions.forEachIndexed { _, option ->
                    AnswerOption(
                        option = option,
                        isSelected = false,
                        isEnabled = true,
                        onClick = {
                            onAnswerSelected(question.options.indexOf(option))
                            gameViewModel.clickCount()
                            if (gameViewModel.isGameOver()) {
                                navController.navigate("gameOver")
                            }
                        }

                    )

                }

            }

        }
    }




@Composable
fun GameOverScreen(navController: NavController, score: Int, onPlayAgain: () -> Unit) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Game Over!",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Your score is $score",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    onPlayAgain()
                    navController.navigate("quizScreen")
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Play Again")
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
        }
    }


@Composable
fun AnswerOption(
                 option: String, isSelected: Boolean, isEnabled: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = null,
            modifier = Modifier.clickable(enabled = isEnabled, onClick = onClick)
        )
        Text(
            text = option,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}



