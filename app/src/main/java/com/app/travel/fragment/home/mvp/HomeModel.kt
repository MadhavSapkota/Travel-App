package com.app.travel.fragment.home.mvp
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookingdetails.BookingDetailsActivity
import com.app.travel.activity.bookinglist.BookingActivity
import com.app.travel.activity.dashboard.dto.DashboardResponse
import com.app.travel.activity.itinerarylist.ItineraryListActivity
import com.app.travel.app.Webservice
import io.reactivex.Observable


class HomeModel(
    private val appCompatActivity: AppCompatActivity,
    private val webservice: Webservice) {

    //itineraryItems
    fun getItineraryListView() {
        ItineraryListActivity.start(appCompatActivity)
    }

    /**This function getDashboard() deals with the api operations.*/
    fun getDashboard(info: DashboardResponse): Observable<DashboardResponse> {
        return webservice.postDashboard(info)
    }

    /*This function {@getBookingView()} is to call BookingActivity.*/
    fun getBookingView() {
        BookingActivity.start(appCompatActivity)
    }

    /*This function {getBookingDetailsView()} is to call BookingDetailsActivity.*/
    fun getBookingDetailsView() {
        BookingDetailsActivity.start(appCompatActivity)
    }


}