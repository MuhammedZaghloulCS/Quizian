package com.myownportfolio.quizian.MVVM

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SetQuizViewModel : ViewModel() {
    private val _question =
        MutableStateFlow<MutableMap<String, MutableList<String>>>(mutableMapOf("" to mutableListOf()))
    val question = _question.asStateFlow()

    fun addQuestion(question: String, answer: MutableList<String>) {
        val currentQuestion = _question.value
        if (!currentQuestion.containsKey(question)) {
            currentQuestion[question] = answer
        }

    }

}