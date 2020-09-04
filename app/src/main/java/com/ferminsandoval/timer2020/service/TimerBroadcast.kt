package com.ferminsandoval.timer2020.service

interface TimerBroadcast {
    fun setNextBreakTime()
    fun unregisterTimer()
}