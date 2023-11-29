package com.example.helutales

data class Quiz(
    val title: String,
    val questions: Map<String, Map<String, Any>> = mutableMapOf()
)
