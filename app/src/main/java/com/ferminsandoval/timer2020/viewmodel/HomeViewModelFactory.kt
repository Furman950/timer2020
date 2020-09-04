package com.ferminsandoval.timer2020.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ferminsandoval.timer2020.service.TimerBroadcastService
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(application, TimerBroadcastService(application.applicationContext)) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}