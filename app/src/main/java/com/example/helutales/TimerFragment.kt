package com.example.helutales

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.helutales.R

class TimerFragment : Fragment() {

    private lateinit var taskNameEditText: EditText
    private lateinit var saveTaskButton: Button
    private lateinit var workCyclesLabel: TextView
//    private lateinit var timerProgressBar: ProgressBar
    private lateinit var startButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private lateinit var stopButton: ImageButton
    private lateinit var timerTextView: TextView
    private lateinit var endTaskButton: Button

    private var isWorking = false
    private var workCycleCount = 0
    private lateinit var countDownTimer: CountDownTimer
    private var isPaused = false
    private var isStopped = false
    private var remainingMillis: Long = 0
//    private var savedProgress = 100 // Declare savedProgress here
    private var initialDuration: Long = 0 // tambahkan properti untuk menyimpan durasi semula

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_timer, container, false)

        taskNameEditText = view.findViewById(R.id.taskNameEditText)
        saveTaskButton = view.findViewById(R.id.saveTask)
        workCyclesLabel = view.findViewById(R.id.workCyclesLabel)
//        timerProgressBar = view.findViewById(R.id.timerProgressBar)
        startButton = view.findViewById(R.id.startButton)
        pauseButton = view.findViewById(R.id.pauseButton)
        stopButton = view.findViewById(R.id.stopButton)
        timerTextView = view.findViewById(R.id.timerTextView)
        endTaskButton = view.findViewById(R.id.endTask)

        pauseButton.visibility = View.GONE
        startButton.visibility = View.GONE
        stopButton.visibility = View.GONE

        saveTaskButton.setOnClickListener {
            handleSaveTask()
        }

        startButton.setOnClickListener {
            if (isWorking) {
                if (isPaused) {
                    resumeTimer()
                    pauseButton.visibility = View.GONE
                    startButton.visibility = View.VISIBLE
                } else {
                    pauseTimer()
                    pauseButton.visibility = View.VISIBLE
                    startButton.visibility = View.GONE
                }
            } else {
                startWorkCycle()
            }
        }

        pauseButton.setOnClickListener {
            resumeTimer()
            pauseButton.visibility = View.GONE
            startButton.visibility = View.VISIBLE
        }

        stopButton.setOnClickListener {
            stopTimer()
        }

        endTaskButton.setOnClickListener {
            endTask()
        }

        return view
    }

    private fun handleSaveTask() {
        val taskName = taskNameEditText.text.toString()
        if (taskName.isNotEmpty()) {
            saveTaskButton.visibility = View.GONE
            workCyclesLabel.visibility = View.VISIBLE
            startWorkCycle()
        }
    }

    private fun startWorkCycle() {
        if (workCycleCount % 8 == 0) {
            // Sudah melewati 4 tahap kerja dan 4 tahap istirahat, reset workCycleCount
            workCycleCount = 0
        }

        isWorking = true
        isPaused = false
        workCycleCount++
        updateWorkCycleLabel()

        // Set the timer based on work or break duration
        val duration = if (workCycleCount % 2 == 1) {
            if (workCycleCount <= 7) 1 * 60 * 1000 else 15 * 60 * 1000
        } else {
            5 * 60 * 1000
        }

        // Setel initialDuration saat memulai siklus baru
        initialDuration = duration.toLong()

        // Reset savedProgress to default when starting a new work cycle
//        savedProgress = 100

        countDownTimer = object : CountDownTimer(duration.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
//                updateProgressBar(millisUntilFinished, duration.toLong())
                remainingMillis = millisUntilFinished
            }

            override fun onFinish() {
                if (workCycleCount < 8) {
                    startWorkCycle()
                } else {
                    // Work cycles completed, reset everything
                    resetTimer()
                }
            }
        }

        pauseButton.visibility = View.GONE
        startButton.visibility = View.VISIBLE
        stopButton.visibility = View.VISIBLE

        countDownTimer.start()
    }

    private fun startTimer(duration: Long) {
        countDownTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
//                updateProgressBar(millisUntilFinished, duration)
                remainingMillis = millisUntilFinished
            }

            override fun onFinish() {
                if (workCycleCount < 8) {
                    startWorkCycle()
                } else {
                    // Work cycles completed, reset everything
                    resetTimer()
                }
            }
        }

//        // Simpan initialDuration saat memulai timer
//        initialDuration = duration

        countDownTimer.start()
    }

    private fun pauseTimer() {
        isPaused = true
//        savedProgress = timerProgressBar.progress
        countDownTimer.cancel()
        pauseButton.visibility = View.GONE
        startButton.visibility = View.VISIBLE
        isStopped = false
    }

    private fun resumeTimer() {
        isPaused = false

        if (isStopped) {
            startTimer(initialDuration)
        } else {
            startTimer(remainingMillis)
        }

        startButton.visibility = View.VISIBLE
        pauseButton.visibility = View.GONE
        //        timerProgressBar.progress = savedProgress
        isStopped = false
    }
    private fun updateWorkCycleLabel() {
        workCyclesLabel.text = "Work Cycle: $workCycleCount"
    }

    private fun updateTimerText(millisUntilFinished: Long) {
        val minutes = millisUntilFinished / 1000 / 60
        val seconds = (millisUntilFinished / 1000) % 60
        timerTextView.text = String.format("%02d:%02d", minutes, seconds)
    }

//    private fun updateProgressBar(millisUntilFinished: Long, duration: Long) {
//        val progress = ((millisUntilFinished.toFloat() / duration) * 100).toInt()
//
//        // Pastikan nilai progress tidak melebihi nilai maksimum ProgressBar
//        val cappedProgress = if (progress > 100) 100 else progress
//
//        timerProgressBar.progress = cappedProgress
//    }

    private fun resetTimer() {
        isWorking = false
        isPaused = false
        workCycleCount = 0
        saveTaskButton.visibility = View.VISIBLE
        workCyclesLabel.visibility = View.GONE
        timerTextView.text = "00:00"
//        timerProgressBar.progress = 100
    }
//    private fun resetTimer() {
//        isWorking = false
//        isPaused = false
//        workCycleCount = 0
//        saveTaskButton.visibility = View.VISIBLE
//        workCyclesLabel.visibility = View.GONE
//        timerTextView.text = "00:00"
//        timerProgressBar.progress = 100
//        startButton.visibility = View.VISIBLE
//        startButton.setImageResource(R.drawable.ic_play)
//        pauseButton.visibility = View.GONE
//    }

    private fun stopTimer() {
        isPaused = true
        countDownTimer.cancel()
        // Optionally, you can update the progress bar to show the paused state
//        updateProgressBar(remainingMillis, remainingMillis)

        // Update tampilan timer dengan initialDuration
        updateTimerText(initialDuration)
        isStopped = true
    }

    private fun endTask() {
        // Dapatkan nama tugas dan jumlah work cycle
        val taskName = taskNameEditText.text.toString()
        val cycles = workCycleCount.toLong()

        // Simpan data ke dalam TaskDataManager
        TaskDataManager.saveTask(requireContext(), TaskData(0, taskName, cycles))

        // Reset tampilan atau lakukan apa pun yang diperlukan setelah mengakhiri tugas
        resetTimer()
    }
}
