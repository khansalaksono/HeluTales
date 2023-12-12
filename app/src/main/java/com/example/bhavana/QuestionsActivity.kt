package com.example.bhavana

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionsActivity : AppCompatActivity() {

    //var quizzes: MutableList<Quiz>? = null
    private var quiz: Quiz? = null
    var questions: MutableMap<String, Question>? = null
    var index = 1
    var beforeIndex = 1
    private lateinit var quizTitle: String
    private var showingQuestion = true
    private var selectedOption: String? = null
    private var selectedOptionBefore: String? = null
    private lateinit var optionAdapter: OptionAdapter
    private lateinit var description: TextView
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        quizTitle = intent.getStringExtra("Title") ?: ""

        setUpFirestore()

        optionAdapter = OptionAdapter(this, Question())
        description = findViewById<TextView>(R.id.description)
        btnNext = findViewById<Button>(R.id.btnNext)

        setUpEventListener()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        showExitConfirmationDialog()
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit Quiz")
            .setMessage("Are you sure you want to exit the quiz? Your progress will be lost.")
            .setPositiveButton("Yes") { _, _ ->
                // User clicked "Yes," exit the quiz
                super.onBackPressed()
            }
            .setNegativeButton("No") { dialog, _ ->
                // User clicked "No," dismiss the dialog
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun setUpEventListener() {
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

//        btnPrevious.setOnClickListener {
//            index--
//            bindViews()
//        }

        btnNext.setOnClickListener {
            if (showingQuestion) {
                // Question -> Materi
                selectedOption = optionAdapter.getSelectedOption()
                if (selectedOption == null) {
                    Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                // You can do something with the selected option, for example:
                Log.d("USER_ANSWER", "User selected option: $selectedOption")
                showingQuestion = false
            } else {
                // Materi -> Question
                index++
                if (index > questions?.size ?: 0) {
                    index = 1 // Loop kembali ke data 1 kalau sudah sampai akhir
                }
                showingQuestion = true
            }
            bindViews()
        }


        btnSubmit.setOnClickListener {
            if (quiz != null) {
                Log.d("FINALQUIZ", questions.toString())

                val intent = Intent(this, ResultActivity::class.java)
                val json = Gson().toJson(quiz)
                intent.putExtra("QUIZ", json)
                startActivity(intent)
            } else {
                // Handle the case where quiz is null
                Toast.makeText(this, "No quiz available", Toast.LENGTH_SHORT).show()
            }
        }


//        btnSubmit.setOnClickListener {
//            if (quizzes != null && quizzes!!.isNotEmpty()) {
//                Log.d("FINALQUIZ", questions.toString())
//
//                val intent = Intent(this, ResultActivity::class.java)
//                val json = Gson().toJson(quizzes!![0])
//                intent.putExtra("QUIZ", json)
//                startActivity(intent)
//            } else {
//                // Handle the case where quizzes is null or empty
//                Toast.makeText(this, "No quizzes available", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private fun setUpFirestore() {
        val firestore = FirebaseFirestore.getInstance()

        if (quizTitle.isNotEmpty()) {
            firestore.collection("quizzes").whereEqualTo("title", quizTitle)
                .get()
                .addOnSuccessListener { result ->
                    if (result != null && !result.isEmpty) {
                        val document = result.documents[0]
                        quiz = document.toObject(Quiz::class.java)

                        if (quiz != null) {
                            questions = quiz?.questions?.toMutableMap()
                            bindViews()
                        } else {
                            // Handle the case where the retrieved quiz is null
                            Toast.makeText(this, "No quiz available", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle the case where no quiz is found
                        Toast.makeText(this, "No quiz found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle the failure to retrieve data from Firestore
                    Log.e("QuestionsActivity", "Error getting quiz questions", exception)
                    Toast.makeText(this, "Error retrieving quiz", Toast.LENGTH_SHORT).show()
                }
        }
    }



//    private fun setUpFirestore() {
//        val firestore = FirebaseFirestore.getInstance()
//        val quizTitle = intent.getStringExtra("Title")
//        if (quizTitle != null) {
//            firestore.collection("quizzes").whereEqualTo("title", quizTitle)
//                .get()
//                .addOnSuccessListener { result ->
//                    if (result != null && !result.isEmpty) {
//                        val document = result.documents[0]
//                        val quiz = document.toObject(Quiz::class.java)
//                        questions = quiz?.questions?.toMutableMap()
//                        bindViews()
//                    }
//                }
//        }
//    }

    private fun bindViews() {
//        val btnNext = findViewById<Button>(R.id.btnNext)
//        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
//        val optionList = findViewById<RecyclerView>(R.id.optionList)
//        val materiText = findViewById<TextView>(R.id.materiText)
//
//        btnSubmit.visibility = View.GONE
//        optionList.visibility = View.GONE
//        materiText.visibility = View.GONE
//
//        if (index == 1) { //first question
//            btnNext.visibility = View.VISIBLE
//        } else if (index == questions!!.size) { // last question
//            if (showingQuestion) {
//                btnNext.visibility = View.VISIBLE
//            } else {
//                btnNext.visibility = View.GONE
//                btnSubmit.visibility = View.VISIBLE
//            }
//        } else { // Middle
//            btnNext.visibility = View.VISIBLE
//        }
//
//        val question = questions!!["question$index"]
//
//        question?.let {
//            if (showingQuestion) {
//                description.text = it.description
//
//                // Use the same instance of optionAdapter
//                optionAdapter.updateQuestion(it)
//
//                optionList.layoutManager = LinearLayoutManager(this)
//                optionList.adapter = optionAdapter
//                optionList.setHasFixedSize(true)
//                optionList.visibility = View.VISIBLE
//                selectedOption = null
//            } else {
//                materiText.text = it.explanation
//                description.visibility = View.VISIBLE
//
//                selectedOption = null
//            }
//        }
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val optionList = findViewById<RecyclerView>(R.id.optionList)
//        val btnPrevious = findViewById<Button>(R.id.btnPrevious)
        val materiText = findViewById<TextView>(R.id.materiText)

        btnSubmit.visibility = View.GONE
        optionList.visibility = View.GONE
        materiText.visibility = View.GONE

        if (index == 1) { //first question
            btnNext.visibility = View.VISIBLE
        } else if (index == questions!!.size) { // last question
            if (showingQuestion) {
                btnNext.visibility = View.VISIBLE
            } else {
                btnNext.visibility = View.GONE
                btnSubmit.visibility = View.VISIBLE
            }
        } else { // Middle
            btnNext.visibility = View.VISIBLE
        }

//        if (index == 1) { //first question
//            btnNext.visibility = View.VISIBLE
//        } else if (index == questions!!.size) { // last question
//            btnSubmit.visibility = View.VISIBLE
//            btnPrevious.visibility = View.VISIBLE
//        } else { // Middle
//            btnPrevious.visibility = View.VISIBLE
//            btnNext.visibility = View.VISIBLE
//        }

        val question = questions!!["question$index"]

        question?.let {
            if (showingQuestion){
                description.text = it.description

                optionAdapter.updateQuestion(it)

                optionList.layoutManager = LinearLayoutManager(this)
                optionList.adapter = optionAdapter
                optionList.setHasFixedSize(true)
                optionList.visibility = View.VISIBLE
                selectedOption = null
            } else {
                description.text = it.explanation

                materiText.visibility = View.VISIBLE
                selectedOption = null
            }
        }
//        question?.let {
//            description.text = it.description
//            val optionAdapter = OptionAdapter(this, it)
//            optionList.layoutManager = LinearLayoutManager(this)
//            optionList.adapter = optionAdapter
//            optionList.setHasFixedSize(true)
//        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        // Pass the quiz title back to MainActivity
//        val intent = Intent(this, MainActivity::class.java)
//        intent.putExtra("Title", quizTitle)
//        startActivity(intent)
//    }
}