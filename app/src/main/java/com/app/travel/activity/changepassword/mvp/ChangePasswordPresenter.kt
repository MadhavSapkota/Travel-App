package com.app.travel.activity.changepassword.mvp
import com.app.travel.activity.changepassword.dto.ChangePasswordResponse
import com.app.travel.apputlis.ApiConstants
import com.app.travel.apputlis.ResponseErrorHandler.handleErrorResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ChangePasswordPresenter(
    private val changePasswordView: ChangePasswordView,
    private val changePasswordModel: ChangePasswordModel
) {
    private val compositeDisposable = CompositeDisposable()

    fun onCreateView() {
        onClick()
    }

    private fun onClick() {
        changePasswordView.btnConfirmPasswordObservable().doOnNext { getChangePasswordRequest() }.subscribe() /*changepassword save operation*/
        changePasswordView.getBackArrowObserable().doOnNext { changePasswordModel.getUserProfileView() }.subscribe() /*back arrow*/
    }

    private fun getChangePasswordRequest() {
        when {
            changePasswordView.checkNetwork() -> {
                ChangePasswordRequest()
            }
            else -> changePasswordView.getErrorMessage(ApiConstants.NONETWORK)
        }
    }

    private fun ChangePasswordRequest() {
        changePasswordView.showProgressDialog(ApiConstants.PROCESSING)
        compositeDisposable.add(
            changePasswordModel.getChangePassword(changePasswordView.getChangePasswordRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::changePasswordSuccess, this::changePasswordError)
        )
    }

    private fun changePasswordSuccess(result: ChangePasswordResponse) {
        changePasswordView.hidProgressDialog()
        changePasswordModel.getUserProfileView()
        System.out.println("ResultValue" + result)
    }

    private fun changePasswordError(e: Throwable) {
        changePasswordView.hidProgressDialog()
        changePasswordView.getErrorMessage(handleErrorResponse(e))
    }
}