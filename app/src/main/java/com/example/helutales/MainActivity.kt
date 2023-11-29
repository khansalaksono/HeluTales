package com.example.helutales

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.helutales.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var viewModel: MainViewModel

    // Initialize Firestore
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Retrieve quiz data from Firestore
        retrieveQuizData()

        // Observe the quizzes LiveData and update UI accordingly
        viewModel.quizzes.observe(this, Observer { quizzes ->
            if (quizzes != null) {
                displayQuizzes(quizzes)
            }
        })

        viewModel.fetchQuizzes()
    }

    private fun retrieveQuizData() {

    }

    private fun displayQuizzes(quizzes: List<Quiz>) {
        val quizContainer = findViewById<LinearLayout>(R.id.quizContainer)

        // Iterate through the list of quizzes and dynamically create UI elements
        for (quiz in quizzes) {
            val button = Button(this)
            button.text = quiz.title
            button.setOnClickListener { startQuiz(quiz) }
            quizContainer.addView(button)

            // Optionally, you can add some spacing between buttons
            val layoutParams = button.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(0, 0, 0, 16) // Adjust margin as needed
            button.layoutParams = layoutParams
        }
    }


    private fun startQuiz(quiz: Quiz) {
        QuizHolder.selectedQuiz = quiz
        startActivity(Intent(this, QuizActivity::class.java))
    }

}


//import android.os.Bundle
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.helutales.QuizAdapter
//import com.example.helutales.databinding.ActivityMainBinding
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private val quizViewModel: QuizViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val adapter = QuizAdapter(emptyList()) { quiz ->
//            // Handle item click, e.g., navigate to QuizDetailActivity with the selected quiz
//        }
//
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter
//
//        // Observe changes in the list of quizzes
//        quizViewModel.quizzes.observe(this, { quizzes ->
//            adapter.updateQuizzes(quizzes)
//        })
//    }
//}
