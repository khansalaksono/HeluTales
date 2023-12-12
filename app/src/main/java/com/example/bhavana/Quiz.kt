package com.example.bhavana

data class Quiz(
    val title: String = "",
    val questions: Map<String, Question> = mutableMapOf()
)
