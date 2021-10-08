package com.app.travel.activity.forgotpassword.mvp
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.login.LoginActivity
import com.app.travel.activity.forgotpassword.dto.ForgotPasswordRequest
import com.app.travel.activity.forgotpassword.dto.ForgotPasswordResponse
import com.app.travel.app.Webservice
import io.reactivex.Observable

class ForgotPasswordModel(
    private val appCompatActivity: AppCompatActivity,
    private val webservice: Webservice
) {
    fun getLogin(info: ForgotPasswordRequest): Observable<ForgotPasswordResponse> {
        return webservice.postForgotPassword(info)
    }

    //LoginActivity is called here.
    fun getLoginView() {
        appCompatActivity.finish()
        LoginActivity.start(appCompatActivity)
    }
}
