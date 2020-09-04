package com.ferminsandoval.timer2020.view.adapters

import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @BindingAdapter("isBusy")
    @JvmStatic
    fun setIsBusy(view: View, isBusy: Boolean) {
        val animation = view.animation

        if (isBusy && animation == null) {
            view.startAnimation(spinningAnimation())
        } else if (animation != null) {
            animation.cancel()
            view.animation = null
        }
    }

    @JvmStatic
    private fun spinningAnimation(): Animation = RotateAnimation(
        0f,
        360f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        duration = 1000
        repeatCount = Animation.INFINITE
        repeatMode = Animation.RESTART
    }
}