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
                    val questions = document.data["questions"] as? List<Map<String, Any>> ?: emptyList()
                    val questionList = questions.map { question ->
                        Question(
                            question["description"].toString(),
                            question["answer"].toString(),
                            question["option1"].toString(),
                            question["option2"].toString(),
                            question["option3"].toString(),
                            question["option4"].toString(),
                            question["explanation"].toString()
                        )
                    }
                    val quiz = Quiz(title, questionList)
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