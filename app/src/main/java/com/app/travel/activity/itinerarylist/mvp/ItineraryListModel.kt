package com.app.travel.activity.itinerarylist.mvp

import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookingdetails.BookingDetailsActivity
import com.app.travel.activity.dashboard.DashboardActivity
import com.app.travel.activity.itinerarydetails.ItineraryDetailsActivity
import com.app.travel.activity.itinerarylist.ItineraryListActivity
import com.app.travel.activity.itinerarylist.dto.ItineraryListResponse
import com.app.travel.app.Webservice
import io.reactivex.Observable

class ItineraryListModel(
    private val appCompatActivity: AppCompatActivity,
    private val webservice: Webservice
) {

    /*DashboardActivity is called here.*/
    fun getDashboadView() {
        DashboardActivity.start(appCompatActivity)
    }

    fun getBookingDetails(info: ItineraryListResponse): Observable<ItineraryListResponse> {
        return webservice.getitineraryList(info)
    }

    /*ItineraryDetails Activity is called here*/
    fun getItineraryView() {
        ItineraryDetailsActivity.start(appCompatActivity)
    }

    /*calls previous activity*/
    fun getBookingDetailsView() {
        appCompatActivity.finish()
    }

}
