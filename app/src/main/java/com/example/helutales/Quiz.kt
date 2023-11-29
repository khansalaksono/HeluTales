package com.example.helutales

import java.io.Serializable

data class Quiz(
    val title: String,
    val questions: List<Question>) : Serializable
