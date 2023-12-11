package com.example.helutales

// TimerFragment.kt

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.helutales.R

class TimerFragment : Fragment() {

    private lateinit var timerTextView: TextView
    private lateinit var timerProgressBar: ProgressBar
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var taskNameEditText: EditText

    private var workDuration: Long = 25 * 60 * 1000 // Default to 25 minutes
    private var breakDuration: Long = 5 * 60 * 1000 // Default to 5 minutes

    private var timer: CountDownTimer? = null
    private var timerRunning = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_timer, container, false)

        timerTextView = view.findViewById(R.id.timerTextView)
        timerProgressBar = view.findViewById(R.id.timerProgressBar)
        startButton = view.findViewById(R.id.startButton)
        stopButton = view.findViewById(R.id.stopButton)
        taskNameEditText = view.findViewById(R.id.taskNameEditText)

        // Set click listeners for buttons
        startButton.setOnClickListener { startTimer() }
        stopButton.setOnClickListener { stopTimer() }

        // Retrieve settings from arguments
        arguments?.let {
            workDuration = it.getLong("workDuration", 25 * 60 * 1000)
            breakDuration = it.getLong("breakDuration", 5 * 60 * 1000)
        }

        return view
    }

    private fun startTimer() {
        if (!timerRunning) {
            timer = object : CountDownTimer(workDuration, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    updateTimerUI(millisUntilFinished)
                }

                override fun onFinish() {
                    timerRunning = false
                    // Handle timer completion, e.g., switch to break timer
                    startBreakTimer()
                }
            }.start()

            timerRunning = true
            updateButtonState()
        }
    }

    private fun stopTimer() {
        timer?.cancel()
        timerRunning = false
        updateButtonState()
    }

    private fun startBreakTimer() {
        // Code to start break timer based on breakDuration
        // You can use a similar approach as startTimer() method
    }

    private fun updateTimerUI(millisUntilFinished: Long) {
        val minutes = millisUntilFinished / 1000 / 60
        val seconds = (millisUntilFinished / 1000) % 60
        val timeString = String.format("%02d:%02d", minutes, seconds)
        timerTextView.text = timeString

        // Update progress bar
        val progress = (millisUntilFinished / workDuration.toFloat() * 100).toInt()
        timerProgressBar.progress = progress
    }

    private fun updateButtonState() {
        startButton.isEnabled = !timerRunning
        stopButton.isEnabled = timerRunning
    }

    companion object {
        fun newInstance(workDuration: Long, breakDuration: Long): TimerFragment {
            val fragment = TimerFragment()
            val args = Bundle()
            args.putLong("workDuration", workDuration)
            args.putLong("breakDuration", breakDuration)
            fragment.arguments = args
            return fragment
        }
    }
}
