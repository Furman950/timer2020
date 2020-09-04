package com.ferminsandoval.timer2020.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ferminsandoval.timer2020.App
import com.ferminsandoval.timer2020.CHANNEL_ID
import com.ferminsandoval.timer2020.R
import com.ferminsandoval.timer2020.databinding.ActivityHomeBinding
import com.ferminsandoval.timer2020.di.ApplicationComponent
import com.ferminsandoval.timer2020.extention.activityViewModels
import com.ferminsandoval.timer2020.extention.showSnackBar
import com.ferminsandoval.timer2020.viewmodel.HomeViewModel
import com.ferminsandoval.timer2020.viewmodel.HomeViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {


    @Inject
    lateinit var applicationComponent: ApplicationComponent



    private val homeViewModel by activityViewModels<HomeViewModel> {
        applicationComponent.homeViewModel().get()
    }

//    private val receiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            homeViewModel.restartCountDown()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        createNotificationChannelId()
        initDataBinding()
        setUpNumberPicker()

//        registerReceiver(receiver, IntentFilter("com.ferminsandoval.timer2020.alarm"))
    }

//    override fun onPause() {
//        super.onPause()
//        unregisterReceiver(receiver)
//    }

    private fun setUpNumberPicker() {
        findViewById<NumberPicker>(R.id.numberPicker).apply {
            minValue = 1
            maxValue = 60
        }
    }

    private fun createNotificationChannelId() {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
            vibrationPattern = longArrayOf(500, 750, 100, 1000)
            lightColor = Color.BLUE
            enableLights(true)
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun initDataBinding() {
//        homeViewModel = ViewModelProvider(
//            this,
//            HomeViewModelFactory(application)
//        ).get(HomeViewModel::class.java)

        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home).also {
            it.homeViewModel = this.homeViewModel
            it.lifecycleOwner = this
        }

        homeViewModel.timerOn.observe(this, Observer { showSnackBar(it) })
    }

    private fun showSnackBar(timerOn: Boolean) {
        val msg = if (timerOn) "Timer turned on" else "Timer turned off"
        findViewById<View>(R.id.home_layout).showSnackBar(msg, Snackbar.LENGTH_SHORT)
    }
}
