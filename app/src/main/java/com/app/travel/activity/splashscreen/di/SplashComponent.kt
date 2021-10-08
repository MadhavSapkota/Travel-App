package com.app.travel.activity.splashscreen.di
import com.app.travel.activity.splashscreen.SplashActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import dagger.Component

@AppActivity
@Component(modules=[(SplashModule::class)], dependencies=[(AppComponent::class)])
interface SplashComponent {
    abstract fun inject(splashActivity: SplashActivity)
}