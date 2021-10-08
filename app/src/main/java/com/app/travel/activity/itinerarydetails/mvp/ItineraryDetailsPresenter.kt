package com.app.travel.activity.itinerarydetails.mvp
import android.content.ContentValues
import android.util.Log
import com.app.travel.activity.itinerarydetails.dto.ItineraryDetailsResponse
import com.app.travel.apputlis.ApiConstants
import com.app.travel.apputlis.ResponseErrorHandler
import com.app.travel.apputlis.UserInfo.itineraryId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers



class ItineraryDetailsPresenter(
    private val itinerarydetailsView: ItineraryDetailsView,
    private val itinerarydetailsModel: ItineraryDetailsModel){
    private val compositeDisposable = CompositeDisposable()

    fun onCreateView() {
        onClick()
        getItineraryDetailsListRequest()
    }

    //This onClick() method deals with user action listners
    private fun onClick(){
        itinerarydetailsView.getBackArrow().doOnNext {itinerarydetailsModel.getItineraryView()}.subscribe() /*backarrow functionality*/
    }

    /*
     This getBookingDetailsListRequest() is responsible to check network connection, if found true then make request to the ItineraryDetails
     otherwise it generates ErrorMessage of apiconstant of no network.
    */
    private fun getItineraryDetailsListRequest() {
        when {
            itinerarydetailsView.checkNetwork() -> {
                itineraryDetailsRequest()
            }
            else -> itinerarydetailsView.getErrorMessage(ApiConstants.NONETWORK)
        }
    }

    //editprofile api request
    private fun itineraryDetailsRequest() {
        itinerarydetailsView.showProgressDialog(ApiConstants.PROCESSING)
        compositeDisposable.add(
            itinerarydetailsModel.getBookingDetails(itinerarydetailsView.getItineraryDetailsRequest(),itineraryId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::itinerarySuccess, this::itineraryError)
        )
    }

    private fun itinerarySuccess(result: ItineraryDetailsResponse) {
        itinerarydetailsView.hidProgressDialog()
        System.out.println("Result" + result)
        var termlist = result.data
        if (termlist != null) {
            itinerarydetailsView.showList(termlist, true)
        }
        itinerarydetailsView.setAdapter()
    }

    private fun itineraryError(e: Throwable) {
        itinerarydetailsView.getErrorMessage(ResponseErrorHandler.handleErrorResponse(e))
        e.message?.let { Log.e(ContentValues.TAG, it) }
    }
}

