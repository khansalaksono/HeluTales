package com.example.helutales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel : ViewModel() {

    private val _quizList = MutableLiveData<List<Quiz>>()
    val quizList: LiveData<List<Quiz>> get() = _quizList

    init {
        // Load quiz titles when the ViewModel is initialized
        loadQuizTitles()
    }

    private fun loadQuizTitles() {
        val db = FirebaseFirestore.getInstance()
        val quizzesCollection = db.collection("quizzes")

        quizzesCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val quizList = mutableListOf<Quiz>()
                for (document in querySnapshot.documents) {
                    val quizTitle = document.getString("title") ?: ""
                    val quiz = Quiz(quizTitle) // Assuming a Quiz constructor that takes a title
                    quizList.add(quiz)
                }
                _quizList.value = quizList
            }
            .addOnFailureListener { e ->
                // Handle the error
            }
    }
}
