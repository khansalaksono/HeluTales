package com.example.bhavana

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val quizRepository: QuizRepository = QuizRepository()

    // LiveData to observe changes in the quiz list
    private val _quizzes = MutableLiveData<List<Quiz>>()
    val quizzes: LiveData<List<Quiz>> get() = _quizzes

    init {
        // Initialize the ViewModel by loading quizzes from the repository
        loadQuizzes()
    }

    private fun loadQuizzes() {
        // Use a coroutine scope to call the suspend function
        GlobalScope.launch(Dispatchers.Main) {
            try {
                _quizzes.value = quizRepository.getQuizzes()
            } catch (e: Exception) {
                // Handle the exception appropriately (e.g., log or show an error message)
                e.printStackTrace()
            }
        }
    }
}
