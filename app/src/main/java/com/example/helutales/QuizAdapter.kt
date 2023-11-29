package com.example.helutales

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuizAdapter(
    private val context: Context,
    private val quizzes: List<Quiz>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(quiz: Quiz, position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.quiz_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quiz = quizzes[position]
        holder.titleTextView.text = quiz.title

        // Set OnClickListener to handle CardView click
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(quiz, position)
        }

//        // Set OnClickListener to handle CardView click
//        holder.itemView.setOnClickListener {
//            // Launch a new activity or show a dialog with the questions
//            // For simplicity, I'll just show a toast with the questions
//            val questions = quiz.questions.toString() // Convert map to string for simplicity
//            // You can replace this with your logic to display questions in a new activity or dialog
//            // For example, you can pass the questions to a new activity using Intent
//            val intent = Intent(context, QuestionsActivity::class.java)
//            intent.putExtra("questions", questions)
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }
}
