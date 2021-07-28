package com.satya.githubuser.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.satya.githubuser.R
import com.satya.githubuser.broadcast.AlarmReceiver
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val NAME = "setting"
        private const val TIME = "daily"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        alarmReceiver = AlarmReceiver()
        sharedPreferences = getSharedPreferences(NAME, Context.MODE_PRIVATE)

        setting_notif.isChecked = sharedPreferences.getBoolean(TIME, false)
        setting_notif.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setDailyAlarm(
                    applicationContext,
                    AlarmReceiver.TYPE_REPEATING,
                    getString(R.string.reminder)
                )
            } else {
                alarmReceiver.cancelAlarm(applicationContext, AlarmReceiver.TYPE_REPEATING)
            }
            saveChange(isChecked)
        }

    }

    private fun saveChange(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(TIME, value)
        editor.apply()
    }
}