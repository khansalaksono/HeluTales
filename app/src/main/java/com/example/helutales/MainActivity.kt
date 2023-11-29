package com.example.helutales

import QuizViewModel
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), QuizAdapter.OnItemClickListener {

    private lateinit var quizViewModel: QuizViewModel
    val sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        // Observe changes in the LiveData
        quizViewModel.quizzes.observe(this, Observer { quizzes ->
            // Update UI with the new data
            // You can use quizzes to populate your RecyclerView with CardViews
            // For simplicity, let's assume you have a RecyclerView in your layout
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)

            // Pass the context and quizzes to the adapter
            val quizAdapter = QuizAdapter(this, quizzes, this)
            recyclerView.adapter = quizAdapter

            recyclerView.adapter = QuizAdapter(this, quizzes, object : QuizAdapter.OnItemClickListener {
                override fun onItemClick(quiz: Quiz, position: Int) {
                    // Handle item click, launch QuestionsActivity or other logic
                    val intent = Intent(this@MainActivity, QuestionsActivity::class.java)
                    intent.putExtra("quiz", quiz)
                    startActivity(intent)
                }
            })
//            recyclerView.adapter = QuizAdapter(this, quizzes)
        })
    }

    override fun onItemClick(quiz: Quiz, position: Int) {
        // Handle item click, launch QuestionsActivity
        val intent = Intent(this, QuestionsActivity::class.java)
        intent.putExtra("Title", quiz.title)
        startActivity(intent)
    }

//    override fun onItemClick(quiz: Quiz, position: Int) {
//        // Handle item click, launch QuestionsActivity
//        sharedViewModel.selectedQuiz = quiz
////        val intent = Intent(this, QuestionsActivity::class.java)
////        intent.putExtra("quiz", quiz)
////        startActivity(intent)
//    }
//
////        // Observe changes in the LiveData
////        quizViewModel.getQuizzes().observe(this, Observer { quizzes ->
////            // Update UI with the new data
////            // You can use quizzes to populate your RecyclerView with CardViews
////            // For simplicity, let's assume you have a RecyclerView in your layout
////            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
////            recyclerView.layoutManager = LinearLayoutManager(this)
////
////            // Pass the context and quizzes to the adapter
////            recyclerView.adapter = QuizAdapter(this, quizzes)
////        })
}
