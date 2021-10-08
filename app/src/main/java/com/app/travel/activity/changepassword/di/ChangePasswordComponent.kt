package com.app.travel.activity.changepassword.di
import com.app.travel.activity.changepassword.ChangePasswordActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import dagger.Component


@AppActivity
@Component(modules=[(ChangePasswordModule::class)],dependencies =[(AppComponent::class)])
interface ChangePasswordComponent{
    abstract fun inject(changePasswordActivity: ChangePasswordActivity)
}