package com.app.travel.activity.forgotpassword.di

import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.forgotpassword.mvp.ForgotPasswordModel
import com.app.travel.activity.forgotpassword.mvp.ForgotPasswordPresenter
import com.app.travel.activity.forgotpassword.mvp.ForgotPasswordView
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice

import dagger.Module
import dagger.Provides



@Module
class ForgotPasswordModule(private val appCompatActivity: AppCompatActivity ) {
    @AppActivity
    @Provides
    fun getForgotPasswordView(): ForgotPasswordView {
        return ForgotPasswordView(appCompatActivity)
    }

    @Provides
    fun getForgotPasswordModel(webservice: Webservice): ForgotPasswordModel {
        return ForgotPasswordModel(appCompatActivity, webservice)
    }

    @Provides
    fun getForgotPasswordPresenter(
        forgotPasswordView: ForgotPasswordView,
        forgotPasswordModel: ForgotPasswordModel
    ): ForgotPasswordPresenter {
        return ForgotPasswordPresenter(forgotPasswordView, forgotPasswordModel)
    }




}
