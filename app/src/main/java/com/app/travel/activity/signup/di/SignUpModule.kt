package com.app.travel.activity.signup.di
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.signup.mvp.*
import com.app.travel.app.AppActivity
import com.app.travel.app.Webservice
import dagger.Module
import dagger.Provides

@Module
class SignUpModule(private val appCompatActivity: AppCompatActivity) {
    @AppActivity
    @Provides
    fun getSignUpView(): SignUpView {
        return SignUpView(appCompatActivity)
    }

    @Provides
    fun getSignUpModel(webservice: Webservice): SignUpModel {
        return SignUpModel(appCompatActivity, webservice)
    }

    @Provides
    fun getSignUpPresenter(
        signUpView: SignUpView,
        signUpModel: SignUpModel
    ): SignUpPresenter {
        return SignUpPresenter(signUpView, signUpModel)
    }
}


