package com.app.travel.activity.login.di

import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.login.mvp.LoginModel
import com.app.travel.activity.login.mvp.LoginPresenter
import com.app.travel.activity.login.mvp.LoginView
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice


import dagger.Module
import dagger.Provides

@Module
class LoginModule(private val appCompatActivity: AppCompatActivity) {

    @AppActivity
    @Provides
    fun getLoginView(): LoginView {
        return LoginView(appCompatActivity)
    }

    @Provides
    fun getLoginModel(webservice: Webservice): LoginModel {
        return LoginModel(appCompatActivity, webservice)
    }

    @Provides
    fun getLoginPresenter(
        loginView: LoginView,
        loginModel: LoginModel
    ): LoginPresenter {
        return LoginPresenter(loginView, loginModel)
    }
}