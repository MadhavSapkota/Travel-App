package com.app.travel.fragment.profile.mvp

import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookingdetails.BookingDetailsActivity
import com.app.travel.activity.changepassword.ChangePasswordActivity
import com.app.travel.activity.dashboard.DashboardActivity
import com.app.travel.activity.editprofile.EditProfileActivity
import com.app.travel.activity.userprofile.UserProfileActivity
import com.app.travel.activity.userprofile.dto.UserProfileResponse
import com.app.travel.app.Webservice
import io.reactivex.Observable


class ProfileModel(
    private val appCompatActivity: AppCompatActivity,
    private val webservice: Webservice
) {

    //calling dashboardactivity
    fun getDashboadView() {
        DashboardActivity.start(appCompatActivity)
    }

    fun getUserDetails(info: UserProfileResponse): Observable<UserProfileResponse> {
        return webservice.getprofileDetails(info)
    }

    //calling editprofileactivity
    fun getEditDetailsView() {
        EditProfileActivity.start(appCompatActivity)
    }

    //calling boookingdetailsactivity
    fun getBookingDetailsView() {
        BookingDetailsActivity.start(appCompatActivity)
    }

    fun getUserDetailsView() {
        UserProfileActivity.start(appCompatActivity)
    }

    //calling passwordactivity
    fun getChangePasswordView() {
        ChangePasswordActivity.start(appCompatActivity)
    }
}