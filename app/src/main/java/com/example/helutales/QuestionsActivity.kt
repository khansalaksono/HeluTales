package com.example.helutales

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        val questions = intent.getStringExtra("questions")
        // Handle the questions and display them in your QuestionsActivity
        // For simplicity, you can use a TextView or any other UI component
        val questionsTextView: TextView = findViewById(R.id.questionsTextView)
        questionsTextView.text = questions
    }
}
