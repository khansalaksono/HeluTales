package com.example.helutales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var quizViewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        // Observe changes in the LiveData
        quizViewModel.getQuizzes().observe(this, Observer { quizzes ->
            // Update UI with the new data
            // You can use quizzes to populate your RecyclerView with CardViews
            // For simplicity, let's assume you have a RecyclerView in your layout
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = QuizAdapter(quizzes)
        })
    }
}
