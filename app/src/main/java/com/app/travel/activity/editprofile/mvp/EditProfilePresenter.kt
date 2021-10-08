package com.app.travel.activity.editprofile.mvp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.R
import com.app.travel.activity.editprofile.dto.EditProfileResponse
import com.app.travel.app.AppApplication.Companion.getContext
import com.app.travel.apputlis.ApiConstants
import com.app.travel.apputlis.ResponseErrorHandler.handleErrorResponse
import com.app.travel.apputlis.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class EditProfilePresenter(
    private val editProfileView: EditProfileView,
    private val editProfileModel: EditProfileModel,
    private val appCompatActivity: AppCompatActivity,
) {
    private val compositeDisposable = CompositeDisposable()
    lateinit var _calendar: Calendar
    var day: Int = 0
    var year: Int = 0
    var month: Int = 0
    val ImageREQUESTID = 102

    fun onCreateView() {
        onClick()
        _calendar = Calendar.getInstance()
        year = _calendar.get(Calendar.YEAR)
        day = _calendar.get(Calendar.DAY_OF_MONTH)
        month = _calendar.get(Calendar.MONTH)

       /*when a user click calendar views, this popup date picker dialog*/
        editProfileView.getDateObserable().doOnNext { editProfileView.showDatePicker(year, month, day) }
            .subscribe()
    }

    /*UserProfile image setup*/
    fun getuserPicture(bitmap: Bitmap) {
        when {
            bitmap != null -> {
                editProfileView.setCustomerImage(
                    BitmapDrawable(
                        appCompatActivity.resources,
                        bitmap
                    )
                )
            }
        }
    }

   //This onClick() method deals with user action listners
    private fun onClick() {
        editProfileView.getEditProfileObserable().doOnNext { getEditDetailsListRequest() }.subscribe() /*for save edited information*/
        editProfileView.getBackArrowObserable().doOnNext { editProfileModel.getUserProfileWithoutRefreshing() }.subscribe() /*for back arrow*/
        editProfileView.getCameraObserable().doOnNext {editProfileModel.imagePicker(ImageREQUESTID)}.subscribe() /*for gallery*/
    }



     /*
       This getEditDetailsListRequest() is responsible to check network connection, if found true then make request to the EditProfile
       otherwise it generates ErrorMessage of apiconstant of no network.
      */
    private fun getEditDetailsListRequest() {
        when {
            editProfileView.checkNetwork() -> {
                EditProfileRequest()
            }
            else -> editProfileView.getErrorMessage(ApiConstants.NONETWORK)
        }
    }

    //editprofile api request
    private fun EditProfileRequest() {
        editProfileView.showProgressDialog(ApiConstants.PROCESSING)
        compositeDisposable.add(
            editProfileModel.getEditUserDetails(editProfileView.getEditDetailsRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::editProfileSuccess, this::editProfileError)
        )
    }

    //editProfileSuccess, get and set api response in sharepreferences "userinfo" class
    private fun editProfileSuccess(result: EditProfileResponse) {
        editProfileView.hidProgressDialog()
        UserInfo.clientFullname = result.data!!.clientFullname!!
        UserInfo.dob = result.data!!.dob!!
        UserInfo.email = result.data!!.email!!
        UserInfo.phone = result.data!!.phone!!
        UserInfo.nationality = result.data!!.nationality!!
        editProfileModel.getUserProfileView()
        System.out.println("ResultValue" + result)
    }

    //editProfileError
    private fun editProfileError(e: Throwable) {
        editProfileView.hidProgressDialog()
        editProfileView.getErrorMessage(handleErrorResponse(e))
    }
}