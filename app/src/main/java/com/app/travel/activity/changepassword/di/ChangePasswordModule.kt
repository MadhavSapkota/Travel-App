package com.app.travel.activity.changepassword.di
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.changepassword.mvp.ChangePasswordModel
import com.app.travel.activity.changepassword.mvp.ChangePasswordPresenter
import com.app.travel.activity.changepassword.mvp.ChangePasswordView
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice
import dagger.Module
import dagger.Provides


@Module
class ChangePasswordModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun getChangePasswordView(): ChangePasswordView {
        return ChangePasswordView(appCompatActivity)
    }

    @Provides
    fun getChangePasswordModel(webservice: Webservice): ChangePasswordModel {
        return ChangePasswordModel(appCompatActivity, webservice)
    }

    @Provides
    fun getChangePasswordPresenter(
        changePasswordView: ChangePasswordView,
        changePasswordModel: ChangePasswordModel
        ): ChangePasswordPresenter {
        return ChangePasswordPresenter(changePasswordView, changePasswordModel)
    }
}