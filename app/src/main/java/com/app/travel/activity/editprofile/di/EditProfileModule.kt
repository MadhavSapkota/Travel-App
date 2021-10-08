package com.app.travel.activity.editprofile.di
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.editprofile.mvp.EditProfileModel
import com.app.travel.activity.editprofile.mvp.EditProfilePresenter
import com.app.travel.activity.editprofile.mvp.EditProfileView
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice
import dagger.Module
import dagger.Provides


@Module
class EditProfileModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun getEditProfileView(): EditProfileView {
        return EditProfileView(appCompatActivity)
    }

    @Provides
    fun getEditProfileModel(webservice: Webservice): EditProfileModel {
        return EditProfileModel(appCompatActivity, webservice)
    }

    @Provides
    fun getEditProfilePresenter(
        editProfileView: EditProfileView,
        editProfileModel: EditProfileModel
        ): EditProfilePresenter {
        return EditProfilePresenter(editProfileView, editProfileModel,appCompatActivity)
    }
}