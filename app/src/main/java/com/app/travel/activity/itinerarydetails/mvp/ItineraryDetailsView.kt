package com.app.travel.activity.itinerarydetails.mvp
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.travel.activity.itinerarydetails.ItineraryDetailsAdapter
import com.app.travel.activity.itinerarydetails.dto.ItineraryDetailsData
import com.app.travel.activity.itinerarydetails.dto.ItineraryDetailsResponse
import com.app.travel.apputlis.NetworkUtil
import com.app.travel.apputlis.UserInfo.tourname
import com.app.travel.databinding.FragmentItineraryDetailsBinding
import com.app.travel.dialog.ErrorDailog
import com.elarkgroup.gwcl.appcomponent.CustomProgressBar
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable


class ItineraryDetailsView(
    private val appCompatActivity: AppCompatActivity,
    private val itineraryDetailsAdapter: ItineraryDetailsAdapter
) {
    var binding: FragmentItineraryDetailsBinding? = null
    private var progressBar = CustomProgressBar()
    fun start(binding_itinerarydetails: FragmentItineraryDetailsBinding) {
        binding = binding_itinerarydetails
        setAdapter()
        setTitleName()
    }

    fun setTitleName(){
      val titleName =  binding!!.tvBackgroundTitle
      titleName.text = tourname
    }

    fun getBackArrow(): Observable<Any> {
        return RxView.clicks(binding!!.buttonArrow)
    }

    fun getErrorMessage(message: String) {
        appCompatActivity.runOnUiThread(Runnable { ErrorDailog(
            appCompatActivity,
            message!!) })
    }

    fun showList(termlist: ItineraryDetailsData, aboolean: Boolean) {
        itineraryDetailsAdapter?.showListItems(termlist, aboolean)
    }

    fun checkNetwork(): Boolean {
        return NetworkUtil.checkInternetConnection(appCompatActivity)
    }

    fun setAdapter() {
        var layoutmanager: LinearLayoutManager?  = LinearLayoutManager(appCompatActivity)
        val firstVisiblePosition = layoutmanager!!.findFirstVisibleItemPosition()
        binding!!.bookingItinerarydetailsRecyclerview.rvbookingItinerary.setHasFixedSize(true)
        binding!!.bookingItinerarydetailsRecyclerview.rvbookingItinerary.setHasFixedSize(true)
        binding!!.bookingItinerarydetailsRecyclerview.rvbookingItinerary.layoutManager = layoutmanager
        binding!!.bookingItinerarydetailsRecyclerview.rvbookingItinerary.adapter = itineraryDetailsAdapter
        layoutmanager!!.scrollToPositionWithOffset(firstVisiblePosition, 0)

    }

    fun getItineraryDetailsRequest(): ItineraryDetailsResponse {
        return ItineraryDetailsResponse()
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