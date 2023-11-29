package com.example.helutales

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuestionsActivity : AppCompatActivity() {

    private lateinit var quiz: Quiz
    private var currentQuestionIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        quiz = intent.getSerializableExtra("quiz") as Quiz
        showQuestion(currentQuestionIndex)

        // Set OnClickListener for the "Next" button
        val nextButton: Button = findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            if (currentQuestionIndex < quiz.questions.size - 1) {
                currentQuestionIndex++
                showQuestion(currentQuestionIndex)
            } else {
                // Last question, handle submission or completion
                handleQuizCompletion()
            }
        }

//        val questions = intent.getStringExtra("questions")
//        // Handle the questions and display them in your QuestionsActivity
//        // For simplicity, you can use a TextView or any other UI component
//        val questionsTextView: TextView = findViewById(R.id.questionsTextView)
//        questionsTextView.text = questions
    }
    private fun showQuestion(index: Int) {
        if (quiz.questions.isEmpty()) {
            // Handle the case where there are no questions
            return
        }

        val question = quiz.questions[index]

        val textViewQuestion: TextView = findViewById(R.id.textViewQuestion)
        val radioGroupOptions: RadioGroup = findViewById(R.id.radioGroupOptions)
        val radioButtonOption1: RadioButton = findViewById(R.id.radioButtonOption1)
        val radioButtonOption2: RadioButton = findViewById(R.id.radioButtonOption2)
        val radioButtonOption3: RadioButton = findViewById(R.id.radioButtonOption3)
        val radioButtonOption4: RadioButton = findViewById(R.id.radioButtonOption4)
        val nextButton: Button = findViewById(R.id.nextButton)

        // Update UI to display the question and options
        textViewQuestion.text = question.description
        radioButtonOption1.text = question.option1
        radioButtonOption2.text = question.option2
        radioButtonOption3.text = question.option3
        radioButtonOption4.text = question.option4

        // Clear any previous selection in RadioGroup
        radioGroupOptions.clearCheck()

        // Set OnClickListener for the "Next" button
        nextButton.setOnClickListener {
            if (currentQuestionIndex < quiz.questions.size - 1) {
                currentQuestionIndex++
                showQuestion(currentQuestionIndex)
            } else {
                handleQuizCompletion()
            }
        }
    }


    private fun handleQuizCompletion() {
        // Handle the submission or completion logic
        // Calculate the score based on user responses
        // You may want to show a summary or navigate to a result screen
    }
}
