package com.example.helutales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel : ViewModel() {

    // LiveData to hold the list of quizzes
    private val _quizzes = MutableLiveData<List<Quiz>>()
    val quizzes: LiveData<List<Quiz>> get() = _quizzes

    // Initialize Firestore
    val db = FirebaseFirestore.getInstance()


    // Function to fetch the list of quizzes
    // Inside your ViewModel or Repository
    fun fetchQuizzes() {
        db.collection("quizzes")
            .get()
            .addOnSuccessListener { result ->
                // Convert the result to a list of Quiz objects
                val quizzes = result.toObjects(Quiz::class.java)
                // Update LiveData or use a callback to notify the UI
                _quizzes.postValue(quizzes)
            }
            .addOnFailureListener { exception ->
                // Handle errors
                // Log.e(TAG, "Error getting documents: ", exception)
            }
    }

}