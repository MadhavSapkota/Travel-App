package com.app.travel.activity.forgotpassword.mvp
import com.app.travel.activity.forgotpassword.dto.ForgotPasswordResponse
import com.app.travel.apputlis.ApiConstants;
import com.app.travel.apputlis.ResponseErrorHandler.handleErrorResponse
import com.app.travel.apputlis.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ForgotPasswordPresenter(private val forgotPasswordView: ForgotPasswordView, private val forgotPasswordModel: ForgotPasswordModel) {

    private val compositeDisposable= CompositeDisposable()
    fun onCreateView() {
        onClick()
    }

    //This onClick() method deals with user action listners
    private fun onClick() {
        forgotPasswordView.getForgotPasswordObserable().doOnNext { getForgotPasswordRequest() }.subscribe() /*submit button operation*/
        forgotPasswordView.getLoginObserable().doOnNext {forgotPasswordModel.getLoginView() }.subscribe() /*moves to the login page*/
    }

    /*
    This getForgotPasswordRequest() is responsible to check network connection, if found true then make request to the api
    otherwise it generates ErrorMessage of apiconstant of no network.
   */
    private fun getForgotPasswordRequest() {
        when {
            forgotPasswordView.checkNetwork() -> {
                userLoginRequest()
            }
            else -> forgotPasswordView.getErrorMessage(ApiConstants.NONETWORK)
        }
    }

    //editprofile api request
    private fun userLoginRequest() {
        forgotPasswordView.showProgressDialog(ApiConstants.PROCESSING)
        forgotPasswordView.setBrighterButton()
        compositeDisposable.add(
            forgotPasswordModel.getLogin(forgotPasswordView.getForgotPasswordRequest())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::forgotPasswordSuccess, this::userloginError)
        )
    }

    private fun forgotPasswordSuccess(result: ForgotPasswordResponse) {
        forgotPasswordView.hidProgressDialog()
        UserInfo.forgot_email= result!!.forgot_email!!
        forgotPasswordModel.getLoginView()
    }

    private fun userloginError(e: Throwable) {
        forgotPasswordView.setDimForgotImage()
        forgotPasswordView.hidProgressDialog()
        forgotPasswordView.getErrorMessage(handleErrorResponse(e))
    }

}
