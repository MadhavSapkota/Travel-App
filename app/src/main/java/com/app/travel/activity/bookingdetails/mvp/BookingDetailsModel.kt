package com.app.travel.activity.bookingdetails.mvp
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookingdetails.BookingDetailsActivity
import com.app.travel.activity.bookingdetails.dto.BookingDetailsResponse
import com.app.travel.activity.dashboard.DashboardActivity
import com.app.travel.activity.itinerarylist.ItineraryListActivity
import com.app.travel.app.Webservice
import io.reactivex.Observable

class BookingDetailsModel(
    private val appCompatActivity: AppCompatActivity,
    private val webservice: Webservice
) {
    //calls DashboardView
    fun getDashboadView() {
        appCompatActivity.finish()
    }
    // for api integration
    fun getBookingDetails(info: BookingDetailsResponse, id:Int): Observable<BookingDetailsResponse> {
        return webservice.postBookingDetails(info,id)
    }

    //Calls itineraryList activity
    fun getItineraryView() {
        ItineraryListActivity.start(appCompatActivity)
    }
}
