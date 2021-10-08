package com.app.travel.activity.login.mvp

import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.dashboard.DashboardActivity
import com.app.travel.activity.forgotpassword.ForgotPasswordActivity
import com.app.travel.activity.login.dto.LoginRequest
import com.app.travel.activity.login.dto.LoginResponse
import com.app.travel.activity.signup.SignUpActivity
import com.app.travel.app.Webservice
import io.reactivex.Observable

class LoginModel(
    private val appCompatActivity: AppCompatActivity,
    private val webservice: Webservice
) {
    /*This functionality {@getDashboardView()} calls DashboardActivity.*/
    fun getDashboardView() {
        DashboardActivity.start(appCompatActivity)
    }


    /*This functionality {@getLogin()} calls api where params @LoginRequest class includes requested attributes during login.
      params @LoginResponse class includes obtained response during login.Similarly {@getLogin()}deals with the api connection operation).*/

    fun getLogin(info: LoginRequest): Observable<LoginResponse> {
        return webservice.postLogin(info)
    }

    /*This functionality {@getSignUPView()} calls Signup Activity*/
    fun getSignUPView() {
        SignUpActivity.start(appCompatActivity)
    }

    /*This functionality {@getForgotPasswordView()} calls ForgotPassword Activity. */
    fun getForgotPasswordView() {
        ForgotPasswordActivity.start(appCompatActivity)
    }
}

