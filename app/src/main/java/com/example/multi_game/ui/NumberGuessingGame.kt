package com.example.multi_game.ui

import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.unit.dp

import kotlin.random.Random


@Composable
fun NumberGuessingGame(navController: NavHostController) {
    var guess by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf(Random.nextInt(1, 101)) }
    var message by remember { mutableStateOf("Guess a number between 1 and 100") }
    val focusRequester = remember { FocusRequester() }
    var count by remember { mutableStateOf(0) }


    Column {
        TopAppBar(
            title = { Text(text = "Math Game") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back to home screen")
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = message, modifier = Modifier.padding(bottom = 16.dp))

            OutlinedTextField(
                value = guess,
                onValueChange = { guess = it },
                label = { Text("Guess a number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.focusRequester(focusRequester)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (guess.isNotEmpty()) {
                    val number = guess.toInt()
                    when {
                        number < answer -> {
                            message = "Your guess is too low"
                            count += 1
                            guess = ""
                        }
                        number > answer -> {
                            message = "Your guess is too high"
                            count += 1
                            guess = ""
                        }
                        else -> {
                            message =
                                "You guessed it! The answer was $answer and You guessed the number in $count guesses."
                        }
                    }


                    focusRequester.requestFocus()
                } else {
                    message = "Please enter a valid number."
                }
            }) {
                Text(text = "Guess")
            }
            Button(onClick = {
                guess = ""
                message = "Guess a number between 1 and 100"
                answer = Random.nextInt(1, 101)
                count = 0
                focusRequester.requestFocus()
            }) {
                Text(text = "Reset")
            }

        }
    }
}




