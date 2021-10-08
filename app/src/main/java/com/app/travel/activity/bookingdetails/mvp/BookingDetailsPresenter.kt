package com.app.travel.activity.bookingdetails.mvp
import android.content.ContentValues
import android.util.Log
import com.app.travel.activity.bookingdetails.dto.BookingDetailsData
import com.app.travel.activity.bookingdetails.dto.BookingDetailsResponse
import com.app.travel.apputlis.ApiConstants
import com.app.travel.apputlis.ResponseErrorHandler
import com.app.travel.apputlis.UserInfo.id
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class BookingDetailsPresenter(
    private val bookingDetailsView: BookingDetailsView,
    private val bookingDetailsModel: BookingDetailsModel){
    private val compositeDisposable = CompositeDisposable()

    fun onCreateView() {
        onClick()
        getBookingDetailsListRequest()
        onItemClick()
        onItemBackArrowClick()
    }

    //user action listner
    private fun onClick(){
        bookingDetailsView.getViewClickedObservable().doOnNext {bookingDetailsModel.getItineraryView()}.subscribe() /*itinerary details*/
        bookingDetailsView.getViewClickedArrowObservable().doOnNext {bookingDetailsModel.getDashboadView()}.subscribe() /*back arrow*/
    }

    //api request for booking details , if internet available, provides successful api connection, otherwise generates errors saying no network connection.
    private fun getBookingDetailsListRequest() {
        when {
            bookingDetailsView.checkNetwork() -> {
                DashboardRequest()
            }
            else -> bookingDetailsView.getErrorMessage(ApiConstants.NONETWORK)
        }
    }

    //BookingDetails api request when a user click booking details
    private fun DashboardRequest() {
        bookingDetailsView.showProgressDialog(ApiConstants.PROCESSING)
        compositeDisposable.add(
            bookingDetailsModel.getBookingDetails(bookingDetailsView.getBookingDetailsRequest(),id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::dashboardSuccess, this::dashboardError))
    }

    //api success response with action listner
    private fun dashboardSuccess(result: BookingDetailsResponse) {
        bookingDetailsView.hidProgressDialog()
        System.out.println("Result" + result)
        var termlist = result.data
        bookingDetailsView.showList(termlist, true)
        bookingDetailsView.setAdapter()
    }

    //api error message
    private fun dashboardError(e: Throwable) {
        bookingDetailsView.getErrorMessage(ResponseErrorHandler.handleErrorResponse(e))
        e.message?.let { Log.e(ContentValues.TAG, it) }
    }


    /* This function is defined to make booking items clickable in the adapter. */
    private fun onItemClick() {
        val disposableObserver = getItemClickObserver()
        bookingDetailsView.getdetailObserable().subscribe(disposableObserver)
        compositeDisposable.add(disposableObserver)
    }

    /*Implementation of making booking items clickable in the adapter. */
    private fun getItemClickObserver(): DisposableObserver<BookingDetailsData> {
        return object : DisposableObserver<BookingDetailsData>() {
            override fun onNext(bookingdata: BookingDetailsData) {}
            override fun onError(e: Throwable) {
                System.err.println("Error"+ e )
            }
            override fun onComplete() {
                System.out.println("clicked")
                Timber.e("Clicked")
            }
        }
    }


    /*ActionListener for back arrow button */
    private fun onItemBackArrowClick() {
        val disposableObserver = getItemBackArrowObserver()
        bookingDetailsView.getdetailClickedArrowObserable().subscribe(disposableObserver)
        compositeDisposable.add(disposableObserver)
    }

    /*ActionListener for back arrow button implementation*/
    private fun getItemBackArrowObserver(): DisposableObserver<BookingDetailsData> {
        return object : DisposableObserver<BookingDetailsData>() {
            override fun onNext(bookingdata: BookingDetailsData) {}
            override fun onError(e: Throwable) {
                System.err.println("Error"+ e )
            }
            override fun onComplete() {
                System.out.println("clicked")
                Timber.e("Clicked")
            }
        }
    }
}

