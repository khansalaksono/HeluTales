package com.example.helutales

data class Question(
    val description: String,
    val options: List<String>,
    val answer: Int,
    val explanation: String
) {
    fun isAnswerCorrect(userAnswer: Int): Boolean {
        return answer == userAnswer
    }
}
