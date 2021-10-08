package com.app.travel.activity.changepassword.mvp
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.changepassword.ChangePasswordActivity
import com.app.travel.activity.changepassword.dto.ChangePasswordRequest
import com.app.travel.activity.changepassword.dto.ChangePasswordResponse
import com.app.travel.activity.userprofile.UserProfileActivity
import com.app.travel.app.Webservice
import io.reactivex.Observable


class ChangePasswordModel(
    private val appCompatActivity: AppCompatActivity,
    private val webservice: Webservice
) {
   //calling to go to previous stack
    fun getUserProfileView() {
        appCompatActivity.finish()
    }

    fun getChangePassword(request: ChangePasswordRequest): Observable<ChangePasswordResponse> {
        return webservice.postChangePassword(request)
    }

    //calling to start changepasswordactivtity
    fun getChangePasswordView(){
        ChangePasswordActivity.start(appCompatActivity)
    }
}
