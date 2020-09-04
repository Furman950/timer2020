package com.ferminsandoval.timer2020.di

import android.content.Context
import com.ferminsandoval.timer2020.service.UtilModule
import com.ferminsandoval.timer2020.view.HomeActivity
import com.ferminsandoval.timer2020.viewmodel.HomeViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Provider

@Component(modules = [UtilModule::class])
interface ApplicationComponent {


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }


    fun inject(homeActivity: HomeActivity)


    fun homeViewModel(): Provider<HomeViewModel>

}