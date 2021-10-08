package com.app.travel.fragment.profile.di
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import com.app.travel.fragment.profile.ProfileFragment
import dagger.Component

@AppActivity
@Component(modules = [(ProfileModule::class)], dependencies = [(AppComponent::class)])
interface ProfileComponent {
    abstract fun inject(profileFragment: ProfileFragment)
}