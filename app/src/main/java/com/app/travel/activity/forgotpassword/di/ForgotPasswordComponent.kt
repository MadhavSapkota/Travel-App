package com.app.travel.activity.forgotpassword.di

import com.app.travel.activity.forgotpassword.ForgotPasswordActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import dagger.Component

@AppActivity
@Component(modules=[(ForgotPasswordModule::class)],dependencies =[(AppComponent::class)])
interface ForgotPasswordComponent {
    abstract fun inject(forgotPasswordActivity: ForgotPasswordActivity)

}