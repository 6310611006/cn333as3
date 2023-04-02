package com.example.multi_game.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.multi_game.R


@Composable
fun MainScreen(onPlayAgain: () -> Unit,navController: NavHostController) {
    Column(

        modifier = Modifier.padding(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.CenterVertically,
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Image(

            painter = painterResource(id = R.drawable.multiprofile),
            contentDescription = stringResource(id = R.string.cat_content_description)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(

            onClick = { navController.navigate("number_guessing_game_screen") },
            content = { Text("Number Guessing Game") }
        )

        Button(

            onClick = { navController.navigate("math_problem_game_screen") },
            content = { Text("Math Challenge Game") }
        )

        Button(
            onClick = { onPlayAgain()
                navController.navigate("quizScreen") },
            content = { Text("Quiz Game") }
        )
    }
}

