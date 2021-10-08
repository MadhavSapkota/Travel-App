package com.app.travel.activity.splashscreen.di
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.splashscreen.mvp.SplashModel
import com.app.travel.activity.splashscreen.mvp.SplashPresenter
import com.app.travel.activity.splashscreen.mvp.SplashView
import com.app.travel.app.AppActivity
import dagger.Module
import dagger.Provides

@Module
class SplashModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun getSplashView(): SplashView {
        return SplashView(appCompatActivity)
    }

    @Provides
    fun getSplashModel(): SplashModel {
        return SplashModel(appCompatActivity)
    }

    @Provides
    fun getSplashPresenter(
        splashModel: SplashModel
    ): SplashPresenter {
        return SplashPresenter(splashModel)
    }
}