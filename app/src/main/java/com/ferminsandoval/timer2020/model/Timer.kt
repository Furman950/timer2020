package com.ferminsandoval.timer2020.model

import androidx.lifecycle.MutableLiveData

class Timer {
    var status = MutableLiveData<Boolean>().apply { postValue(true) }

    var nextAlarm = MutableLiveData<Long>()

}