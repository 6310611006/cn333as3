package com.example.multi_game

import com.example.multi_game.ui.MathProblemScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.multi_game.ui.theme.MultiGameTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.multi_game.ui.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameViewModel: GameViewModel by viewModels()
        setContent {
            val navController = rememberNavController()
            MultiGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "main_screen"
                    ) {
                        composable("main_screen") {
                            MainScreen(navController)
                        }
                        composable("quiz_game_screen") {
                            GameScreen(
                               gameViewModel = gameViewModel,
                            )
                        }
                        composable("math_challenge_game_screen") {
                            MainScreen(navController)
                        }
                        composable("number_guessing_game_screen") {

                            NumberGuessingGame(navController)
                        }
                    }
                }
            }
        }
    }
}