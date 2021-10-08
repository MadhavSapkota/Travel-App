package com.app.travel.fragment.home.di
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import com.app.travel.fragment.home.HomeFragment
import dagger.Component

@AppActivity
@Component(modules=[(HomeModule::class)], dependencies = [(AppComponent::class)])
interface HomeComponent {
    abstract fun inject(homeFragment: HomeFragment)
}