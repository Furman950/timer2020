package com.ferminsandoval.timer2020.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferminsandoval.timer2020.model.Timer

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val TIMER = "Timer"
    }

    private var timer: Timer = Timer()

    fun toggle() {
        timer.status.value = timer.status.value?.not()
    }

    var status: LiveData<Boolean> = timer.status
}