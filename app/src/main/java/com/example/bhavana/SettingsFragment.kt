package com.example.bhavana

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    private lateinit var workDurationSeekBar: SeekBar
    private lateinit var breakDurationSeekBar: SeekBar
    private lateinit var workCyclesSeekBar: SeekBar

    private lateinit var workDurationTextView: TextView
    private lateinit var breakDurationTextView: TextView
    private lateinit var workCyclesTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        workDurationSeekBar = view.findViewById(R.id.workDurationSeekBar)
        breakDurationSeekBar = view.findViewById(R.id.breakDurationSeekBar)
        workCyclesSeekBar = view.findViewById(R.id.workCyclesSeekBar)

        workDurationTextView = view.findViewById(R.id.workDurationLabel)
        breakDurationTextView = view.findViewById(R.id.breakDurationLabel)
        workCyclesTextView = view.findViewById(R.id.workCyclesLabel)

        setupSeekBarChangeListener()

        return view
    }

    private fun setupSeekBarChangeListener() {
        workDurationSeekBar.setOnSeekBarChangeListener(createSeekBarChangeListener(workDurationTextView, " minutes"))
        breakDurationSeekBar.setOnSeekBarChangeListener(createSeekBarChangeListener(breakDurationTextView, " minutes"))
        workCyclesSeekBar.setOnSeekBarChangeListener(createSeekBarChangeListener(workCyclesTextView, " iterations"))
    }

    private fun createSeekBarChangeListener(textView: TextView, unit: String): SeekBar.OnSeekBarChangeListener {
        return object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textView.text = "$progress$unit"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }
    }
}

