package com.app.travel.activity.signup.mvp
import com.app.travel.activity.signup.dto.SignUpResponse
import com.app.travel.apputlis.ApiConstants
import com.app.travel.apputlis.ResponseErrorHandler.handleErrorResponse
import com.app.travel.apputlis.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class SignUpPresenter(private val signUpView: SignUpView, private val signUpModel: SignUpModel) {
    private val compositeDisposable = CompositeDisposable()
    lateinit var _calendar: Calendar
    var day: Int = 0
    var year: Int = 0
    var month: Int = 0

    fun onCreateView() {
        onClick()
        _calendar = Calendar.getInstance()
        year = _calendar.get(Calendar.YEAR)
        day = _calendar.get(Calendar.DAY_OF_MONTH)
        month = _calendar.get(Calendar.MONTH)
        signUpView.getDateObserable().doOnNext { signUpView.showDatePicker(year, month, day) }
            .subscribe()
    }

    private fun onClick() {
        signUpView.getSignUpObserable().doOnNext { getSignUpRequest() }.subscribe()
        signUpView.getLoginObserable().doOnNext { signUpModel.getLoginView() }.subscribe()
    }

    private fun getSignUpRequest() {
        when {
            signUpView.checkNetwork() -> {
                userSignUpRequest()
            }
            else -> signUpView.getErrorMessage(ApiConstants.NONETWORK)
        }
    }

    private fun userSignUpRequest() {
        signUpView.showProgressDialog(ApiConstants.PROCESSING)
        signUpView.setBrighterSignUpButton()
        compositeDisposable.add(
            signUpModel.getSignUp(signUpView.getSignUpRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::userSignupSuccess, this::userSignupError)) }

    private fun userSignupSuccess(result: SignUpResponse) {
       System.out.println("Response is " + result)
        signUpView.hidProgressDialog()
        UserInfo.email == result.data!!.email!!
        System.out.println("Email is " + UserInfo.email)
        UserInfo.clientFullname = result.data!!.clientFullname!!
        System.out.println("ClientFullName is " + UserInfo.clientFullname)
        UserInfo.nationality = result.data!!.nationality!!
        System.out.println("Nationality is " + UserInfo.nationality )
        UserInfo.phone = result.data!!.phone!!
        System.out.println("Phone is " +   UserInfo.phone)
        UserInfo.dob = result.data!!.dob!!
        System.out.println("Dob is " + UserInfo.dob)
        UserInfo.password = result.data!!.password!!
        System.out.println("Password is " + UserInfo.password)
        signUpModel.getLoginView()
    }

    private fun userSignupError(e: Throwable) {
        signUpView.setDimSubmitButton()
        signUpView.hidProgressDialog()
        signUpView.getErrorMessage(handleErrorResponse(e))
    }
}