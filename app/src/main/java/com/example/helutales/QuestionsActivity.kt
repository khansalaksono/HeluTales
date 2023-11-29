package com.example.helutales

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class QuestionsActivity : AppCompatActivity() {

    private var currentQuestionIndex: Int = 0
    private val sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
    private lateinit var quiz: Quiz // Use lateinit to initialize it later

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        quiz = sharedViewModel.selectedQuiz!! // Initialize the quiz variable

        showQuestion(currentQuestionIndex)

        val nextButton: Button = findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            if (currentQuestionIndex < quiz.questions.size - 1) {
                currentQuestionIndex++
                showQuestion(currentQuestionIndex)
            } else {
                handleQuizCompletion()
            }
        }
    }

    private fun showQuestion(index: Int) {
        if (quiz.questions.isEmpty() || index >= quiz.questions.size) {
            return
        }

        val questionKeys = quiz.questions.keys.toList()
        val currentQuestionKey = questionKeys[index]
        val currentQuestion = quiz.questions[currentQuestionKey] as? Map<String, Any>

//        val currentQuestion = quiz.questions[currentQuestionKey]

        val textViewQuestion: TextView = findViewById(R.id.textViewQuestion)
        val radioGroupOptions: RadioGroup = findViewById(R.id.radioGroupOptions)
        val radioButtonOption1: RadioButton = findViewById(R.id.radioButtonOption1)
        val radioButtonOption2: RadioButton = findViewById(R.id.radioButtonOption2)
        val radioButtonOption3: RadioButton = findViewById(R.id.radioButtonOption3)
        val radioButtonOption4: RadioButton = findViewById(R.id.radioButtonOption4)

        textViewQuestion.text = currentQuestion?.get("description") as? String ?: ""
        radioButtonOption1.text = currentQuestion?.get("option1") as? String ?: ""
        radioButtonOption2.text = currentQuestion?.get("option2") as? String ?: ""
        radioButtonOption3.text = currentQuestion?.get("option3") as? String ?: ""
        radioButtonOption4.text = currentQuestion?.get("option4") as? String ?: ""


//        textViewQuestion.text = currentQuestion?.description ?: ""
//        radioButtonOption1.text = currentQuestion?.option1 ?: ""
//        radioButtonOption2.text = currentQuestion?.option2 ?: ""
//        radioButtonOption3.text = currentQuestion?.option3 ?: ""
//        radioButtonOption4.text = currentQuestion?.option4 ?: ""

        radioGroupOptions.clearCheck()

        val nextButton: Button = findViewById(R.id.nextButton)
        nextButton.setOnClickListener {
            if (index < quiz.questions.size - 1) {
                showQuestion(index + 1)
            } else {
                showExplanationForLastQuestion()
            }
        }
    }

    private fun showExplanationForLastQuestion() {
//        val textViewExplanation: TextView = findViewById(R.id.textViewExplanation)
//        val lastQuestionExplanation = quiz.questions.values.lastOrNull()?.explanation
//
//        if (lastQuestionExplanation != null) {
//            textViewExplanation.text = lastQuestionExplanation
//        } else {
//            textViewExplanation.text = "Explanation not available"
//        }
//
//        val nextButton: Button = findViewById(R.id.nextButton)
//        nextButton.text = "Submit"
//
//        nextButton.setOnClickListener {
//            handleQuizCompletion()
//        }

        fun showExplanationForLastQuestion() {
            val textViewExplanation: TextView = findViewById(R.id.textViewExplanation)

            val lastQuestion = quiz.questions.values.lastOrNull() as? Map<String, Any>

            val lastQuestionExplanation = lastQuestion?.get("explanation") as? String

            if (lastQuestionExplanation != null) {
                textViewExplanation.text = lastQuestionExplanation
            } else {
                textViewExplanation.text = "Explanation not available"
            }

            val nextButton: Button = findViewById(R.id.nextButton)
            nextButton.text = "Submit"

            nextButton.setOnClickListener {
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
