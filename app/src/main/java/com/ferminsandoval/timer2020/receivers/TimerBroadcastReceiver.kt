package com.ferminsandoval.timer2020.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ferminsandoval.timer2020.*
import com.ferminsandoval.timer2020.service.TimerBroadcastService

class TimerBroadcastReceiver : BroadcastReceiver() {
    private lateinit var pref: SharedPreferences
    private lateinit var context: Context

    override fun onReceive(context: Context?, intent: Intent?) {
        this.context = context!!
        sendNotification()

        pref = context.getSharedPreferences(TIMER, Context.MODE_PRIVATE)
        val timerOn = pref.getBoolean(TIMER_ON, false)

        if (timerOn) {
            TimerBroadcastService(context).setNextBreakTime()
        }
    }

    private fun sendNotification() {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Timer 2020")
            .setContentText("Time is up")
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, notification)
        }
    }
}