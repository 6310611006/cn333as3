package com.example.multi_game

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.multi_game.ui.theme.MultiGameTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.multi_game.ui.*
import com.example.multi_game.ui.mathproblegame.MathGameApp
import com.example.multi_game.ui.numberguessinggame.NumberGuessingGame
import com.example.multi_game.ui.quizgame.GameStatus
import com.example.multi_game.ui.quizgame.GameViewModel
import com.example.multi_game.ui.quizgame.Question
import com.example.multi_game.ui.quizgame.QuizScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameViewModel: GameViewModel by viewModels()
        setContent {
            val gameUiState by gameViewModel.uiState.collectAsState()
            val question: Question? by gameViewModel.question.observeAsState(null)
            val score: Int? by gameViewModel.score.observeAsState(null)
            val navController = rememberNavController()
            MultiGameTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                //cutoutShape = CircleShape,
                                content = {
                                    FloatingActionButton(

                                        onClick = { navController.navigate("main_screen") },
                                        backgroundColor = MaterialTheme.colors.primary
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Back",

                                        )
                                    }
                                }
                            )
                        }
                    )
                    {

                        if (!gameUiState.isFinished) {

                            NavHost(navController, startDestination = "main_screen") {

                                composable("quizScreen") {
                                    GameStatus(
                                        questionCount = gameUiState.clickTime,
                                        score = score ?: 0
                                    )
                                    QuizScreen(
                                        navController = navController, question = question,
                                        score = score ?: 0,
                                        onPlayAgain = {
                                            gameViewModel.resetGame()
                                        }
                                    ) { answerIndex ->
                                        gameViewModel.submitAnswer(answerIndex)
                                    }
                                }
                                composable("gameOver") {
                                    com.example.multi_game.ui.quizgame.GameOverScreen(
                                        navController = navController,
                                        score = score ?: 0,
                                        onPlayAgain = {
                                            gameViewModel.resetGame()
                                        })
                                }

                                composable("main_screen") {
                                    MainScreen(
                                        navController = navController,
                                        onPlayAgain = {
                                            gameViewModel.resetGame()
                                        })
                                }

                                composable("MathProblemGameScreen") {
                                    MathGameApp(navController)
                                }
                                composable("NumberGuessingGameScreen") {

                                    NumberGuessingGame(navController)
                                }
                            }
                        } else {
                            com.example.multi_game.ui.quizgame.GameOverScreen(navController = navController,
                                score = score ?: 0,
                                onPlayAgain = {
                                    gameViewModel.resetGame()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
