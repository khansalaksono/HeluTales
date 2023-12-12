package com.example.helutales

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class PomodoroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro)

        // Initialize ViewPager and TabLayout
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        // Create an adapter that returns a fragment for each section
        val adapter = PomodoroPagerAdapter(supportFragmentManager)

        // Add fragments to the adapter
        adapter.addFragment(TimerFragment(), "Timer")
        adapter.addFragment(HistoryFragment(), "History")
//        adapter.addFragment(SettingsFragment(), "Settings")

        // Set up the ViewPager with the sections adapter
        viewPager.adapter = adapter

        // Connect the ViewPager to the TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }
}
