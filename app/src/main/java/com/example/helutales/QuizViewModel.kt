package com.example.helutales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class QuizViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val quizzes = MutableLiveData<List<Quiz>>()

    fun getQuizzes(): LiveData<List<Quiz>> {
        // Fetch data from Firestore and update the LiveData
        firestore.collection("quizzes")
            .get()
            .addOnSuccessListener { result ->
                val quizList = mutableListOf<Quiz>()
                for (document in result) {
                    val title = document.getString("title") ?: ""
                    val questions = document.data["questions"] as? Map<String, Any> ?: emptyMap()
                    val quiz = Quiz(title, questions)
                    quizList.add(quiz)
                }
                quizzes.value = quizList
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }

        return quizzes
    }
}
