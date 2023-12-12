package com.example.bhavana

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class TaskDataAdapter : ListAdapter<TaskData, TaskDataAdapter.TaskViewHolder>(TaskDiffCallback()) {
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNameTextView: TextView = itemView.findViewById(R.id.taskNameTextView)
        val workCyclesTextView: TextView = itemView.findViewById(R.id.workCyclesTextView)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = getItem(position)

        holder.taskNameTextView.text = currentTask.taskName
        holder.workCyclesTextView.text = "Work Cycles: ${currentTask.workCycles}"
        holder.deleteButton.setOnClickListener {
            // Panggil metode untuk menghapus tugas di sini
            showDeleteConfirmationDialog(holder.itemView.context, currentTask)
        }
    }

    private fun showDeleteConfirmationDialog(context: Context, taskData: TaskData) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmation")
            .setMessage("Are you sure you want to delete ${taskData.taskName}?")
            .setPositiveButton("Yes") { _, _ ->
                // Panggil metode untuk menghapus tugas di sini
                TaskDataManager.removeTask(context, taskData)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<TaskData>() {
    override fun areItemsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
        return oldItem == newItem
    }
}
