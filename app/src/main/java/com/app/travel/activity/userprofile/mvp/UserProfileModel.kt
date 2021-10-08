package com.app.travel.activity.userprofile.mvp
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookingdetails.BookingDetailsActivity
import com.app.travel.activity.changepassword.ChangePasswordActivity
import com.app.travel.activity.dashboard.DashboardActivity
import com.app.travel.activity.editprofile.EditProfileActivity
import com.app.travel.activity.itinerarylist.ItineraryListActivity
import com.app.travel.activity.userprofile.UserProfileActivity
import com.app.travel.activity.userprofile.dto.UserProfileResponse
import com.app.travel.app.Webservice
import io.reactivex.Observable


class UserProfileModel(
    private val appCompatActivity: AppCompatActivity,
    private val webservice: Webservice
) {

}
