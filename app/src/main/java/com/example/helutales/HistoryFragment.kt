package com.example.helutales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// HistoryFragment.kt

class HistoryFragment : Fragment(), OnTaskDataChangeListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskDataAdapter: TaskDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize taskDataAdapter before using it
        taskDataAdapter = TaskDataAdapter()
        recyclerView.adapter = taskDataAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTasks()
        TaskDataManager.addOnTaskDataChangeListener(this)
    }

    private fun loadTasks() {
        val tasks = TaskDataManager.getSavedTasks(requireContext())
        taskDataAdapter.submitList(tasks)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        TaskDataManager.removeOnTaskDataChangeListener(this)
    }

    override fun onTaskDataChanged() {
        loadTasks()
    }


}