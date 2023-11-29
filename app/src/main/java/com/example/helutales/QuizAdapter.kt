package com.example.helutales

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helutales.databinding.QuizItemBinding

class QuizAdapter(private val quizzes: List<Quiz>, private val onItemClick: (Quiz) -> Unit) :
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = QuizItemBinding.inflate(inflater, parent, false)
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizzes[position]
        holder.bind(quiz)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    inner class QuizViewHolder(private val binding: QuizItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding.titleTextView.text = quiz.title
            binding.root.setOnClickListener { onItemClick(quiz) }
        }
    }
}
