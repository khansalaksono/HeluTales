package com.example.bhavana

import com.example.bhavana.R
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), QuizAdapter.OnItemClickListener {

    private lateinit var quizViewModel: QuizViewModel

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
        })

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            // Handle the click event, e.g., redirecting to the Pomodoro activity
            val intent = Intent(this@MainActivity, PomodoroActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(quiz: Quiz, position: Int) {
        // Handle item click, launch QuestionsActivity
        val intent = Intent(this, QuestionsActivity::class.java)
        intent.putExtra("Title", quiz.title)
        startActivity(intent)
    }

//        // Observe changes in the LiveData
//        quizViewModel.getQuizzes().observe(this, Observer { quizzes ->
//            // Update UI with the new data
//            // You can use quizzes to populate your RecyclerView with CardViews
//            // For simplicity, let's assume you have a RecyclerView in your layout
//            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
//            recyclerView.layoutManager = LinearLayoutManager(this)
//
//            // Pass the context and quizzes to the adapter
//            recyclerView.adapter = QuizAdapter(this, quizzes)
//        })
    }