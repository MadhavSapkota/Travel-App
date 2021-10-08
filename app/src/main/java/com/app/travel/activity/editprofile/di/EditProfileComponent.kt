package com.app.travel.activity.editprofile.di
import com.app.travel.activity.editprofile.EditProfileActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import dagger.Component


@AppActivity
@Component(modules=[(EditProfileModule::class)],dependencies =[(AppComponent::class)])
interface EditProfileComponent{
    abstract fun inject(editProfileActivity: EditProfileActivity)
}