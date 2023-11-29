package com.example.helutales

data class Question(
    val description: String,
    val answer: Int,
    val options: List<String>,
    val explanation: String,
    var userAnswer: Int? = null // Nullable to represent no user answer initially
)
