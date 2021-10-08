package com.app.travel.activity.signup.mvp
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.login.LoginActivity
import com.app.travel.activity.signup.SignUpActivity
import com.app.travel.activity.signup.dto.SignUpRequest
import com.app.travel.activity.signup.dto.SignUpResponse
import com.app.travel.app.Webservice
import io.reactivex.Observable


class SignUpModel(
    private val appCompatActivity: AppCompatActivity,
    private  val webservice: Webservice
) {

    fun getSignUp(info: SignUpRequest): Observable<SignUpResponse>{
        return webservice.postSignUp(info)
    }

    //calling loginactivity by finishing previous activity
    fun getLoginView() {
        LoginActivity.start(appCompatActivity)
        appCompatActivity.finish()
    }
}














