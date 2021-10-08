package com.app.travel.activity.dashboard.mvp
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookingdetails.BookingDetailsActivity
import com.app.travel.activity.dashboard.dto.DashboardResponse
import com.app.travel.app.Webservice
import com.app.travel.activity.bookinglist.BookingActivity.Companion.start
import com.app.travel.activity.userprofile.UserProfileActivity
import io.reactivex.Observable

class DashboardModel(
    private val appCompatActivity: AppCompatActivity,
    private val webservice: Webservice
) {
}


