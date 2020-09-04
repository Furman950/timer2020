package com.ferminsandoval.timer2020.viewmodel

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.util.MutableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ferminsandoval.timer2020.*
import com.ferminsandoval.timer2020.model.Timer
import com.ferminsandoval.timer2020.service.TimerBroadcastService
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    context: Context,
    val timerBroadcastService: TimerBroadcastService
) : ViewModel() {

    private val TAG = "homeViewModel"
    private var timer: Timer = Timer()
    private var pref = context.getSharedPreferences(TIMER, Context.MODE_PRIVATE)


    var isBusy = MutableLiveData<Boolean>().apply { postValue(false) }


    var numberPicker = MutableLiveData<Int>()
    var timerOn: MutableLiveData<Boolean> = timer.status
    var countDown = MutableLiveData<String>().apply { postValue("00:00") }
    var countDownTimer: CountDownTimer? = null

    init {
        val timerStatus = pref.getBoolean(TIMER_ON, false)
        val breakLength = pref.getLong(BREAK_LENGTH, DEFAULT_BREAK_LENGTH) / MINUTE
        timerOn.postValue(timerStatus)
        numberPicker.postValue(breakLength.toInt())

        if (timerStatus) startCountDown()
    }


    override fun onCleared() {
        countDownTimer?.hashCode()
        super.onCleared()
    }


    fun toggle() {
        val timerOn = timerOn.value ?: false

        if (timerOn) {
            val breakLength = numberPicker.value!! * MINUTE
            isBusy.postValue(true)

            pref.edit().apply {
                putBoolean(TIMER_ON, timerOn)
                putLong(BREAK_LENGTH, breakLength)
            }.apply()
            timerBroadcastService.setNextBreakTime()
            startCountDown()
        } else {
            isBusy.postValue(false)

            timerBroadcastService.unregisterTimer()
            countDownTimer?.cancel()
            countDown.postValue("00:00")
            pref.edit().apply { putBoolean(TIMER_ON, timerOn) }.apply()
        }
    }

    fun restartCountDown() {
        Log.d(TAG, "RestartCountDown()")
        countDownTimer = null
        startCountDown()
    }

    private fun startCountDown() {
        val nextBreakTime = pref.getLong(NEXT_BREAK_TIME, 0)
        val timeTillNextBreak = nextBreakTime - SystemClock.elapsedRealtime()
        countDownTimer = object : CountDownTimer(timeTillNextBreak, 1000) {
            override fun onFinish() {}

            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / MINUTE
                val seconds = (millisUntilFinished - (minutes * MINUTE)) / SECOND
                val time = String.format("%02d:%02d", minutes, seconds)
                countDown.postValue(time)
            }
        }.start()
    }
}