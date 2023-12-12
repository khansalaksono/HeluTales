package com.example.bhavana
// PomodoroActivity.kt
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.bhavana.HistoryFragment
import com.example.bhavana.R
import com.example.bhavana.TimerFragment
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

    private class PomodoroPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val fragmentTitleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList[position]
        }
    }
}