package com.app.travel.activity.userprofile.di
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.userprofile.mvp.UserProfileModel
import com.app.travel.activity.userprofile.mvp.UserProfilePresenter
import com.app.travel.activity.userprofile.mvp.UserProfileView
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice
import dagger.Module
import dagger.Provides


@Module
class UserProfileModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun getItineraryDetailsView(): UserProfileView {
        return UserProfileView(appCompatActivity)
    }

    @Provides
    fun getItineraryDetailsModel(webservice: Webservice): UserProfileModel {
        return UserProfileModel(appCompatActivity, webservice)
    }

    @Provides
    fun getUserProfilePresenter(
        userProfileView: UserProfileView,
        userProfileModel: UserProfileModel,

        ): UserProfilePresenter {
        return UserProfilePresenter(userProfileView, userProfileModel)
    }
}