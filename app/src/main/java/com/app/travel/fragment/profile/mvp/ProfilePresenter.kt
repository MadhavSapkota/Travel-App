package com.app.travel.fragment.profile.mvp

import android.content.ContentValues
import android.util.Log
import com.app.travel.activity.userprofile.dto.UserProfileData
import com.app.travel.activity.userprofile.dto.UserProfileResponse
import com.app.travel.apputlis.ApiConstants
import com.app.travel.apputlis.ResponseErrorHandler
import com.app.travel.apputlis.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ProfilePresenter(
    private val profileView: ProfileView,
    private val profileModel: ProfileModel) {
    private val compositeDisposable = CompositeDisposable()

    fun onCreate() {
        onClick()
        getBookingDetailsListRequest()
        onItemClick() //editpassword
        onItemChangePasswordClick()
    }

    /*This functionality {@onClick()} includes all the user's clickable action listener in login activity.*/
    private fun onClick(){
        profileView.getViewClickedObservable().doOnNext {profileModel.getEditDetailsView()}.subscribe() /*action listner for the editdetails*/
        profileView.getViewClickedPasswordChangeObservable().doOnNext {profileModel.getChangePasswordView()}.subscribe() /*action listner for changepassword*/
    }

    /*This function {@getBookingDetailsListRequest()} checks if user has internet connection , then make getBookingDetailsListRequest request to the api.
      If a user doesnot have accessible to the network he gets error messages*/
    private fun getBookingDetailsListRequest() {
        when {
            profileView.checkNetwork() -> {
                ProfileRequest()
            }
            else -> profileView.getErrorMessage(ApiConstants.NONETWORK)
        }
    }

    /*This function is defined to make EditPassword imageview clickable in the adapter and its implementation.*/
    private fun onItemClick() {
        val disposableObserver = getItemClickObserver()
        profileView.getdetailObserable().subscribe(disposableObserver)
        compositeDisposable.add(disposableObserver)
    }
    private fun getItemClickObserver(): DisposableObserver<UserProfileData> {
        return object : DisposableObserver<UserProfileData>() {
            override fun onNext(userProfileData: UserProfileData) {}
            override fun onError(e: Throwable) {
                System.err.println("Error"+ e )
            }
            override fun onComplete() {
                System.out.println("clicked")
                Timber.e("Clicked")
            }
        }
    }


    /*ActionListener for change_password and its implementation.*/
    private fun onItemChangePasswordClick() {
        val disposableObserver = getItemChangePasswordObserver()
        profileView.getdetailChangePasswordObserable().subscribe(disposableObserver)
        compositeDisposable.add(disposableObserver)
    }

    private fun getItemChangePasswordObserver(): DisposableObserver<UserProfileData> {
        return object : DisposableObserver<UserProfileData>() {
            override fun onNext(userprofiledata: UserProfileData) {}
            override fun onError(e: Throwable) {
                System.err.println("Error"+ e )
            }
            override fun onComplete() {
                System.out.println("clicked")
                Timber.e("Clicked")
            }
        }
    }


    private fun ProfileRequest() {
        profileView.showProgressDialog(ApiConstants.PROCESSING)
        compositeDisposable.add(
            profileModel.getUserDetails(profileView.geUserDetailsRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::profileSuccess, this::profileError))
    }

    /*This functionality {@dashboardSuccess(params) calls the api operation with @params UserProfileResponse.}*/
    private fun profileSuccess(result: UserProfileResponse) {
        profileView.hidProgressDialog()
        System.out.println("Result" + result)
        var termlist = result.data
        UserInfo.email= result.data!!.email!!
        UserInfo.clientFullname = result.data!!.clientFullname!!
        System.out.println("EmailUser" + UserInfo.email)
        UserInfo.phone =result.data!!.phone!!
        UserInfo.nationality=result.data!!.nationality!!
        UserInfo.dob = result.data!!.dob!!
        System.out.println("DOB" + UserInfo.dob)
        if (termlist != null) {
            profileView.showList(termlist, true)
        }
        profileView.setAdapter()
    }
    /*This functionality shows erros message from the api on api response failure.*/
    private fun profileError(e: Throwable) {
        profileView.getErrorMessage(ResponseErrorHandler.handleErrorResponse(e))
        e.message?.let { Log.e(ContentValues.TAG, it) }
    }
}