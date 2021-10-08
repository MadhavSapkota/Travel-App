package com.app.travel.activity.signup.di

import com.app.travel.activity.signup.SignUpActivity
import com.app.travel.activity.signup.di.SignUpModule
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import dagger.Component


@AppActivity
@Component(modules=[(SignUpModule::class)], dependencies=[(AppComponent::class)])
interface SignUpComponent {
    abstract fun inject(signUpActivity: SignUpActivity)
}



