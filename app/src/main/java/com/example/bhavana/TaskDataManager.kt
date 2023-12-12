package com.example.bhavana

import android.content.Context
import com.google.gson.Gson

interface OnTaskDataChangeListener {
    fun onTaskDataChanged()
}
object TaskDataManager {
    private val callbacks = mutableListOf<OnTaskDataChangeListener>()

    fun addOnTaskDataChangeListener(callback: OnTaskDataChangeListener) {
        callbacks.add(callback)
    }

    fun removeOnTaskDataChangeListener(callback: OnTaskDataChangeListener) {
        callbacks.remove(callback)
    }

    private fun notifyTaskDataChanged() {
        callbacks.forEach { it.onTaskDataChanged() }
    }

    private const val PREFS_NAME = "task_data"
    private const val KEY_TASKS = "tasks"

    fun saveTask(context: Context, taskData: TaskData) {
        val tasks = getSavedTasks(context).toMutableList()
        tasks.add(taskData)
        saveTasks(context, tasks)
        notifyTaskDataChanged()
    }

    fun getSavedTasks(context: Context): List<TaskData> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val tasksJson = prefs.getString(KEY_TASKS, null)

        return if (tasksJson != null) {
            Gson().fromJson(tasksJson, Array<TaskData>::class.java).toList()
        } else {
            emptyList()
        }
    }

    private fun saveTasks(context: Context, tasks: List<TaskData>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val tasksJson = Gson().toJson(tasks.toTypedArray())
        editor.putString(KEY_TASKS, tasksJson)
        editor.apply()
    }

    fun removeTask(context: Context, taskData: TaskData) {
        val tasks = getSavedTasks(context).toMutableList()
        tasks.remove(taskData)
        saveTasks(context, tasks)
        notifyTaskDataChanged()
    }
}