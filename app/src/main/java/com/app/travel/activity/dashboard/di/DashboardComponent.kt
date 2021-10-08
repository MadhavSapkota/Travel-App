package com.app.travel.activity.dashboard.di
import com.app.travel.activity.dashboard.DashboardActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.AppComponent
import dagger.Component

@AppActivity
@Component(modules=[(DashboardModule::class)],dependencies =[(AppComponent::class)])
interface DashboardComponent {
    abstract fun inject(dashboardActivity: DashboardActivity)

}