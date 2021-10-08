package com.app.travel.activity.splashscreen.mvp
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.login.LoginActivity


class SplashModel(
    private val appCompatActivity: AppCompatActivity) {

    /*This functionality {@getLoginView} calls the login activity.*/
    fun getLoginView() {
        LoginActivity.start(appCompatActivity)
        appCompatActivity.finish()
    }
}

