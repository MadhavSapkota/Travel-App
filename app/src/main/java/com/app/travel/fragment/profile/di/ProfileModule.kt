package com.app.travel.fragment.profile.di
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice
import com.app.travel.fragment.profile.ProfileAdapter
import com.app.travel.fragment.profile.mvp.ProfileModel
import com.app.travel.fragment.profile.mvp.ProfilePresenter
import com.app.travel.fragment.profile.mvp.ProfileView
import dagger.Module
import dagger.Provides


@Module
class ProfileModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun getHomeView(profileAdapter: ProfileAdapter): ProfileView {
        return ProfileView(appCompatActivity,profileAdapter)
    }

    @Provides
    fun getHomeModel(webservice: Webservice): ProfileModel {
        return ProfileModel(appCompatActivity,webservice)
    }

    @Provides
    fun getHomePresenter(
        profileView: ProfileView,
        profileModel: ProfileModel
    ): ProfilePresenter {
        return ProfilePresenter(profileView, profileModel)
    }

    @Provides
    fun getProfileAdapter():ProfileAdapter{
        return ProfileAdapter(appCompatActivity)
    }


}