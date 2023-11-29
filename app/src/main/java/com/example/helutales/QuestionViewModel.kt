package com.example.helutales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionViewModel : ViewModel() {

    // LiveData to hold the current question
    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question> get() = _currentQuestion

    // Function to set the current question
    fun setCurrentQuestion(question: Question) {
        _currentQuestion.value = question
    }

    // Additional functions to handle user interactions with questions
    // ...

}
