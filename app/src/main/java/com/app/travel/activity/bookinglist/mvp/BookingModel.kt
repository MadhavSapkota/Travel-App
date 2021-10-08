package com.app.travel.activity.bookinglist.mvp

import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookingdetails.BookingDetailsActivity
import com.app.travel.activity.bookinglist.dto.BookingResponse
import com.app.travel.activity.dashboard.DashboardActivity
import com.app.travel.app.Webservice
import io.reactivex.Observable

class BookingModel(
    private val appCompatActivity: AppCompatActivity,
    private val webservice: Webservice
) {
    //calls the previous activity
    fun getDashboadView() {
        appCompatActivity.finish()
    }

    fun getBookingList(info: BookingResponse): Observable<BookingResponse> {
        return webservice.postDashboard(info)
    }

    //calls the bookingdetails activity
    fun getBookingView() {
        BookingDetailsActivity.start(appCompatActivity)
    }
}
