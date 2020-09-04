package com.ferminsandoval.timer2020

import android.app.Application
import com.ferminsandoval.timer2020.di.ApplicationComponent
import com.ferminsandoval.timer2020.di.DaggerApplicationComponent

class App : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): ApplicationComponent {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }
}