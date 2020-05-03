package com.ferminsandoval.timer2020.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ferminsandoval.timer2020.R
import com.ferminsandoval.timer2020.databinding.ActivityHomeBinding
import com.ferminsandoval.timer2020.viewmodel.HomeViewModel
import com.ferminsandoval.timer2020.viewmodel.HomeViewModelFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initDataBinding()
    }

    private fun initDataBinding() {
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(application)).get(HomeViewModel::class.java)

        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home).also {
            it.homeViewModel = this.homeViewModel
            it.lifecycleOwner = this
        }
    }
}
