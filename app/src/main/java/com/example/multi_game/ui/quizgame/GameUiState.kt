package com.example.multi_game.ui.quizgame


data class GameUiState (
    var numQuestionsAsked: Int = 0,
    var clickTime: Int = 0,
    val currentWordCount: Int = 1,
    val isFinished: Boolean = false,
)
