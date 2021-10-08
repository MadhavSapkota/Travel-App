package com.app.travel.activity.splashscreen.mvp
import android.os.Handler

class SplashPresenter(
    private val splashModel: SplashModel) {
    fun onCreateView() {
        loadDelay()
    }

   /*This functionality {@loadDelay} loads the login screen activity after 3 seconds.*/
    private fun loadDelay() {
        Handler().postDelayed(
            object : Runnable {
                override fun run() {
                    splashModel.getLoginView()
                }
            }, 3000
        )
    }
}