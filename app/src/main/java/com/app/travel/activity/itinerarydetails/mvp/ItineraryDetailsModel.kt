package com.app.travel.activity.itinerarydetails.mvp
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.bookingdetails.BookingDetailsActivity
import com.app.travel.activity.dashboard.DashboardActivity
import com.app.travel.activity.itinerarydetails.dto.ItineraryDetailsResponse
import com.app.travel.activity.itinerarylist.ItineraryListActivity
import com.app.travel.app.Webservice
import io.reactivex.Observable

class ItineraryDetailsModel(
    private val appCompatActivity: AppCompatActivity,
    private val webservice: Webservice
) {
    //DashboardActivity is called here.
    fun getDashboadView() {
         DashboardActivity.start(appCompatActivity)
    }

    fun getBookingDetails(info: ItineraryDetailsResponse,id:Int): Observable<ItineraryDetailsResponse> {
        return webservice.getitineraryDetails(info,id)
    }

    fun getItineraryView() {
        appCompatActivity.finish()
    }

    //bookingDetailsActivity is called here.
    fun getBookingDetailsView(){
        BookingDetailsActivity.start(appCompatActivity)
    }
}
