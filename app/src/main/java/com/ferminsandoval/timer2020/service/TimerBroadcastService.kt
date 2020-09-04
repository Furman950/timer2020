package com.ferminsandoval.timer2020.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.widget.Toast
import com.ferminsandoval.timer2020.BREAK_LENGTH
import com.ferminsandoval.timer2020.DEFAULT_BREAK_LENGTH
import com.ferminsandoval.timer2020.NEXT_BREAK_TIME
import com.ferminsandoval.timer2020.TIMER
import com.ferminsandoval.timer2020.receivers.TimerBroadcastReceiver
import javax.inject.Inject


class TimerBroadcastService @Inject constructor(private val context: Context) : TimerBroadcast {
    private var pref = context.getSharedPreferences(TIMER, Context.MODE_PRIVATE)
    private val alarmMrg = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val requestId = 5585

    private val pendingIntent = Intent(context, TimerBroadcastReceiver::class.java).let { intent ->
        intent.action = "com.ferminsandoval.timer2020.alarm"
        PendingIntent.getBroadcast(context, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun setNextBreakTime() {
        val breakLength = pref.getLong(BREAK_LENGTH, DEFAULT_BREAK_LENGTH)
        val nextBreakTime = registerTimer(breakLength)
        pref.edit().putLong(NEXT_BREAK_TIME, nextBreakTime).apply()
    }


    private fun registerTimer(breakLength: Long): Long {
        val nextAlarm = SystemClock.elapsedRealtime() + breakLength
        alarmMrg.setExactAndAllowWhileIdle(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            nextAlarm,
            pendingIntent
        )
        Toast.makeText(context, "Registered Timer", Toast.LENGTH_SHORT).show()
        return nextAlarm
    }

    override fun unregisterTimer() {
        alarmMrg.cancel(pendingIntent)
        Toast.makeText(context, "Unregistered  Timer", Toast.LENGTH_SHORT).show()
    }
}