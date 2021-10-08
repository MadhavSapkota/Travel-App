package com.app.travel.fragment.home.di

import androidx.appcompat.app.AppCompatActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice
import com.app.travel.fragment.home.HomeAdapter
import com.app.travel.fragment.home.mvp.HomeModel
import com.app.travel.fragment.home.mvp.HomePresenter
import com.app.travel.fragment.home.mvp.HomeView
import dagger.Module
import dagger.Provides


@Module
class HomeModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun getHomeView(homeAdapter: HomeAdapter): HomeView {
        return HomeView(appCompatActivity, homeAdapter)
    }

    @Provides
    fun getHomeModel(webservice: Webservice): HomeModel {
        return HomeModel(appCompatActivity,webservice)
    }

    @Provides
    fun getHomePresenter(
        homeView: HomeView,
        homeModel: HomeModel
    ): HomePresenter {
        return HomePresenter(homeView, homeModel)
    }

    @Provides
    fun getHomeAdapter():HomeAdapter{
        return HomeAdapter(appCompatActivity)
    }
}