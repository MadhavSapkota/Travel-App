package com.app.travel.activity.login.di

import com.app.travel.activity.login.LoginActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent

import dagger.Component

@AppActivity
@Component(modules=[(LoginModule::class)], dependencies=[(AppComponent::class)])
interface LoginComponent {
    abstract fun inject(loginActivity: LoginActivity)
}