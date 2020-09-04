package com.ferminsandoval.timer2020.delegates

import androidx.lifecycle.ViewModel

class DaggerViewModels<T : ViewModel>() : Lazy<T> {
    override val value: T
        get() = TODO("Not yet implemented")

    override fun isInitialized(): Boolean {
        TODO("Not yet implemented")
    }
}