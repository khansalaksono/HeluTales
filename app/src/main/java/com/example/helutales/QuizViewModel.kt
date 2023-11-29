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
                    val questionsMap = document.data["questions"] as? Map<String, Any> ?: emptyMap()
                    val questions = parseQuestions(questionsMap)
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

    private fun parseQuestions(questionsMap: Map<*, *>): MutableMap<String, Question> {
        val result = mutableMapOf<String, Question>()
        for ((key, value) in questionsMap) {
            if (value is Map<*, *>) {
                val question = Question(
                    value["description"] as? String ?: "",
                    value["answer"] as? String ?: "",
                    value["option1"] as? String ?: "",
                    value["option2"] as? String ?: "",
                    value["option3"] as? String ?: "",
                    value["option4"] as? String ?: "",
                    value["explanation"] as? String ?: ""
                )
                result[key.toString()] = question
            }
        }
        return result
    }
}
