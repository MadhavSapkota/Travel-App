package com.app.travel.activity.login.mvp
import com.app.travel.activity.login.dto.LoginResponse
import com.app.travel.apputlis.ApiConstants
import com.app.travel.apputlis.ResponseErrorHandler.handleErrorResponse
import com.app.travel.apputlis.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter(private val loginView: LoginView,
                     private val loginModel: LoginModel) {

    private val compositeDisposable=CompositeDisposable()
    fun onCreateView() {
        onClick()
    }

   /*This functionality {@onClick()} includes all the user's clickable action listener in login activity.*/
    private fun onClick() {
        loginView.getLoginObserable().doOnNext { getLoginRequest() }.subscribe()
        loginView.getSignupObserable().doOnNext { loginModel.getSignUPView() }.subscribe()
        loginView.getForgotPasswordObserable().doOnNext {loginModel.getForgotPasswordView()}.subscribe()
    }

    /*This function {@getLoginRequest()} checks if user has internet connection , then make userlogin request to the api.
    If a user doesnot have accessible to the network he gets error messages*/
    private fun getLoginRequest() {
        when {
            loginView.checkNetwork() -> {
                userLoginRequest()
            }
            else -> loginView.getErrorMessage(ApiConstants.NONETWORK)
        }
    }

    /*This function {@getUserLoginRequest} calls progressdialog bar and calls setBrighterLoginImage functions from loginView.With the main operations
    to make login request to the api.*/
    private fun userLoginRequest() {
        loginView.showProgressDialog(ApiConstants.PROCESSING)
        loginView.setBrighterLoginImage()
        compositeDisposable.add(
            loginModel.getLogin(loginView.getLoginRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::userLoginSuccess, this::userloginError)
        )
    }

    /*This functionality {@userLoginSuccess(params) calls the api operation with @params LoginResponse.}*/
    private fun userLoginSuccess(result: LoginResponse) {
        loginView.hidProgressDialog()
        UserInfo.fullname=result.data!!.clientFullname!!
        UserInfo.clientdId=result.data!!.clientId!!.toString()
        UserInfo.email==result.data!!.email!!
        UserInfo.companyname=result.data!!.companyName!!
        UserInfo.userid=result.data!!.userId!!.toString()
        UserInfo.username =result.data!!.userName!!
        UserInfo.branchname=result.data!!.branchName!!
        UserInfo.branchcode=result.data!!.branchCode!!.toString()
        UserInfo.token=result.data!!.token!!
        UserInfo.nationality=result.data!!.nationality!!
        UserInfo.companyid=result.data!!.companyId!!.toString()
        UserInfo.loginStatus=true
        loginModel.getDashboardView()
    }


    /*This functionality shows erros message from the api on api response failure.*/
    private fun userloginError(e: Throwable) {
        loginView.setDimLoginImage()
        loginView.hidProgressDialog()
        loginView.getErrorMessage(handleErrorResponse(e))
    }

}