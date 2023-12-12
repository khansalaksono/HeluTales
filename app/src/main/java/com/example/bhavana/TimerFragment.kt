package com.example.bhavana

import android.app.AlertDialog
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
import com.example.bhavana.R

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

    private lateinit var workPhaseLabel: TextView
    private lateinit var breakPhaseLabel: TextView
    private lateinit var longBreakLabel: TextView

    private var isWorking = false

    private lateinit var countDownTimer: CountDownTimer
    private var isPaused = false
    private var isStopped = false

//    private var savedProgress = 100 // Declare savedProgress here
    private var workCycleCount = 0
    private var remainingMillis: Long = 0
    private var initialDuration: Long = 0 // tambahkan properti untuk menyimpan durasi semula
    private var phaseCount = 0
    private var workPhaseCount = 0
    private var breakPhaseCount = 0
    private var longBreakCount = 0

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

        workPhaseLabel = view.findViewById(R.id.workPhaseLabel)
        breakPhaseLabel = view.findViewById(R.id.breakPhaseLabel)
        longBreakLabel = view.findViewById(R.id.longBreakLabel)

        pauseButton.visibility = View.GONE
        startButton.visibility = View.GONE
        stopButton.visibility = View.GONE
        endTaskButton.visibility = View.GONE

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

//        stopButton.setOnClickListener {
//            stopTimer()
//        }

        endTaskButton.setOnClickListener {
            showConfirmationDialog()
        }

        return view
    }

    private fun handleSaveTask() {
        val taskName = taskNameEditText.text.toString()
        if (taskName.isNotEmpty()) {
            saveTaskButton.visibility = View.GONE
            workCyclesLabel.visibility = View.VISIBLE
            workPhaseLabel.visibility = View.VISIBLE
            breakPhaseLabel.visibility = View.VISIBLE
            longBreakLabel.visibility = View.VISIBLE
            startWorkCycle()
        }
    }

    private fun startWorkCycle() {
        isWorking = true
        isPaused = false
        phaseCount++
        updateWorkCycleLabel()

        val duration = when {
            phaseCount % 8 == 0 -> {
                // Long Break
                longBreakLabel.text = "Long Break"
                workPhaseLabel.text = ""
                breakPhaseLabel.text = ""
                15 * 60 * 1000 // Long Break: 15 menit
            }
            phaseCount % 2 == 1 -> {
                // Work Phase
                workPhaseLabel.text = "Work Phase"
                breakPhaseLabel.text = ""
                longBreakLabel.text = ""
                25 * 60 * 1000 // Work Phase: 25 menit
            }
            else -> {
                // Break Phase
                breakPhaseLabel.text = "Break Phase"
                workPhaseLabel.text = ""
                longBreakLabel.text = ""
                5 * 60 * 1000 // Break Phase: 5 menit
            }
        }

        initialDuration = duration.toLong()

        countDownTimer = object : CountDownTimer(duration.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
                remainingMillis = millisUntilFinished
            }

            override fun onFinish() {
                when {
                    phaseCount % 8 == 0 -> {
                        // Long Break
                        longBreakCount++
                        longBreakLabel.text = "Long Break"
                    }
                    phaseCount % 2 == 1 -> {
                        // Work Phase
                        workPhaseCount++
                        workPhaseLabel.text = "Work Phase"
                    }
                    else -> {
                        // Break Phase
                        breakPhaseCount++
                        breakPhaseLabel.text = "Break Phase"
                    }
                }

                if (workPhaseCount == 4 && breakPhaseCount == 3 && longBreakCount == 1) {
                    workCycleCount++
                    workPhaseCount = 0
                    breakPhaseCount = 0
                    longBreakCount = 0
                }

                // Tetapkan kondisi untuk memulai siklus baru atau mengakhiri
                if (isWorking) {
                    startWorkCycle()
                } else {
                    // Work cycles completed, reset everything
                    resetTimer()
                }
            }
        }

        pauseButton.visibility = View.GONE
        startButton.visibility = View.VISIBLE
//        stopButton.visibility = View.VISIBLE
        endTaskButton.visibility = View.VISIBLE

        countDownTimer.start()
    }

    private fun startTimer(duration: Long) {
        countDownTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
                remainingMillis = millisUntilFinished
            }

            override fun onFinish() {
                if (phaseCount % 2 == 1) {
                    workPhaseCount++
                }

                if (phaseCount % 4 == 0) {
                    longBreakCount++
                } else {
                    breakPhaseCount++
                }

//                if (workCycleCount < 8) {
//                    startWorkCycle()
//                } else {
//                    // Work cycles completed, reset everything
//                    resetTimer()
//                }
            }
        }

        initialDuration = duration
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
        remainingMillis = 0
        initialDuration = 0
        phaseCount = 0
        workPhaseCount = 0
        breakPhaseCount = 0
        longBreakCount = 0
        saveTaskButton.visibility = View.VISIBLE
        workCyclesLabel.visibility = View.GONE
        workPhaseLabel.visibility = View.GONE
        breakPhaseLabel.visibility = View.GONE
        longBreakLabel.visibility = View.GONE
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

        // Clear the taskNameEditText
        taskNameEditText.text.clear()

        // Cancel the countDownTimer
        countDownTimer.cancel()

        pauseButton.visibility = View.GONE
//        stopButton.visibility = View.GONE
        startButton.visibility = View.GONE
        endTaskButton.visibility = View.GONE

        resetTimer()
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmation")
            .setMessage("Are you sure you want to end the task?")
            .setPositiveButton("Yes") { _, _ ->
                endTask()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
