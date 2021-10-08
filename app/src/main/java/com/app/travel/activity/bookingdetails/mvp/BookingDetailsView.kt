package com.app.travel.activity.bookingdetails.mvp
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.travel.activity.bookingdetails.BookingDetailsAdapter
import com.app.travel.activity.bookingdetails.dto.BookingDetailsData
import com.app.travel.activity.bookingdetails.dto.BookingDetailsResponse
import com.app.travel.apputlis.NetworkUtil
import com.app.travel.databinding.FragmentBookingDetailsBinding
import com.app.travel.dialog.ErrorDailog
import com.elarkgroup.gwcl.appcomponent.CustomProgressBar
import io.reactivex.Observable


class BookingDetailsView(
    private val appCompatActivity: AppCompatActivity,
    private val bookingDetailsAdapter: BookingDetailsAdapter
) {
    var binding: FragmentBookingDetailsBinding? = null
    private var progressBar = CustomProgressBar()
    fun start(binding_bookingDetails: FragmentBookingDetailsBinding) {
        binding = binding_bookingDetails
        setAdapter()
    }

    //errors message
    fun getErrorMessage(message: String) {
        appCompatActivity.runOnUiThread(Runnable { ErrorDailog(
            appCompatActivity, message!!) })
    }

    //show listed items
    fun showList(termlist: BookingDetailsData?, aboolean: Boolean) {
        bookingDetailsAdapter?.showListItems(termlist, aboolean)
    }

    //api contents clickable
    fun getViewClickedObservable(): Observable<BookingDetailsData> {
        return bookingDetailsAdapter.clickSubject
    }

    //api contents clickable implementation
    fun getdetailObserable(): Observable<BookingDetailsData> {
        return getViewClickedObservable()
    }

    //api contents back arrow
    fun getViewClickedArrowObservable(): Observable<BookingDetailsData> {
        return bookingDetailsAdapter.clickBackSubject
    }
    //api contents back arrow implementation
    fun getdetailClickedArrowObserable(): Observable<BookingDetailsData> {
        return getViewClickedObservable()
    }

    fun checkNetwork(): Boolean {
        return NetworkUtil.checkInternetConnection(appCompatActivity)
    }

    /* This is for setting adapter*/
    fun setAdapter() {
        var layoutmanager: LinearLayoutManager? = LinearLayoutManager(appCompatActivity)
        val firstVisiblePosition = layoutmanager!!.findFirstVisibleItemPosition()
        binding!!.bookingItineraryRecyclerview.rvbookingItinerary.setHasFixedSize(true)
        binding!!.bookingItineraryRecyclerview.rvbookingItinerary.setHasFixedSize(true)
        binding!!.bookingItineraryRecyclerview.rvbookingItinerary.layoutManager = layoutmanager
        binding!!.bookingItineraryRecyclerview.rvbookingItinerary.adapter = bookingDetailsAdapter
        layoutmanager!!.scrollToPositionWithOffset(firstVisiblePosition, 0)

    }

    fun getBookingDetailsRequest(): BookingDetailsResponse {
        return BookingDetailsResponse()
    }

    /*This functionality {@showProgressDialog(params)} is to set display progress dialog box when user tries to login into the system.
    params: statusString as "loading........."*/
    fun showProgressDialog(statusString: String) {
        progressBar.show(appCompatActivity, statusString)
    }


    /*This functionality {@ hidProgressDialog()} is to hide progress dialog box if user enters correct information to login into the system
    */
    fun hidProgressDialog() {
        progressBar.getDialog()!!.dismiss()
    }
}