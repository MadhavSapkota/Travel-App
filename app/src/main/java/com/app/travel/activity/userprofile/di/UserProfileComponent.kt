package com.app.travel.activity.userprofile.di
import com.app.travel.activity.userprofile.UserProfileActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import dagger.Component


@AppActivity
@Component(modules=[(UserProfileModule::class)],dependencies =[(AppComponent::class)])
interface UserProfileComponent{
    abstract fun inject(userProfileActivity: UserProfileActivity)
}