package com.app.travel.activity.splashscreen.mvp
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.databinding.ActivitySplashBinding

class SplashView(private val appCompatActivity: AppCompatActivity) {
    var binding: ActivitySplashBinding?=null


    /*This functionality {@start(binding_splash: ActivitySplashBInding)} is to intialize splash layout.
      @ binding_splash is splash layout
    */
    fun start(binding_splash: ActivitySplashBinding) {
        binding=binding_splash
    }
}