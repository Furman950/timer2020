package com.ferminsandoval.timer2020.service

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class UtilModule {

//    @Singleton
//    @Provides
//    fun provideTimerBroadcastService(context: Context): TimerBroadcastService =
//        TimerBroadcastService(context)

    @Binds
    abstract fun provideTimerBroadcastService(timerBroadcastService: TimerBroadcastService): TimerBroadcast
}