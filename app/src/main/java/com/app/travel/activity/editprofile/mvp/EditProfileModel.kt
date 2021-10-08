package com.app.travel.activity.editprofile.mvp

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookingdetails.BookingDetailsActivity
import com.app.travel.activity.editprofile.EditProfileActivity
import com.app.travel.activity.editprofile.dto.EditProfileRequest
import com.app.travel.activity.editprofile.dto.EditProfileResponse
import com.app.travel.activity.userprofile.UserProfileActivity
import com.app.travel.app.Webservice
import com.app.travel.apputlis.imageutils.ImagePicker
import io.reactivex.Observable
import java.util.*


class EditProfileModel(
    private val appCompatActivity: AppCompatActivity,
    private val webservice: Webservice
) {
    fun getUserProfileView() {
        appCompatActivity.finish()
    }

//    //when a user move back to profile page without editing any data
    fun getUserProfileWithoutRefreshing() {
        appCompatActivity.finish()
    }


    fun getEditUserDetails(request: EditProfileRequest): Observable<EditProfileResponse> {
        return webservice.editProfileDetails(request)
    }

    //BookingDetailsView called
    fun getBookingDetailsView() {
        BookingDetailsActivity.start(appCompatActivity)
    }

    /*Following code is to open camera and the gallery options*/
    fun imagePicker(intentId: Int) {
        val chooseImageIntent = ImagePicker.getPickImageIntent(appCompatActivity)
        appCompatActivity.startActivityForResult(chooseImageIntent, intentId)
    }
}
