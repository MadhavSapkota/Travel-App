package com.app.travel.activity.dashboard.di
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.dashboard.mvp.DashboardModel
import com.app.travel.activity.dashboard.mvp.DashboardPresenter
import com.app.travel.activity.dashboard.mvp.DashboardView
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice
import dagger.Module
import dagger.Provides



@Module
class DashboardModule(private val appCompatActivity: AppCompatActivity ) {
    @Provides
    fun getDashboardModel(webservice: Webservice): DashboardModel {
        return DashboardModel(appCompatActivity, webservice)
    }
    @Provides
    fun getDashboardPresenter(
        dashboardView: DashboardView,
        dashboardModel: DashboardModel,

    ): DashboardPresenter {
        return DashboardPresenter(dashboardView, dashboardModel)
    }

    @AppActivity
    @Provides
    fun getDashboardView(): DashboardView {
        return DashboardView(appCompatActivity)
    }
}
