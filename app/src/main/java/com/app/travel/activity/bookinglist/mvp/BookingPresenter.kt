package com.app.travel.activity.bookinglist.mvp
import android.content.ContentValues
import android.util.Log
import com.app.travel.activity.bookinglist.dto.BookingData
import com.app.travel.activity.bookinglist.dto.BookingResponse
import com.app.travel.apputlis.ApiConstants
import com.app.travel.apputlis.ResponseErrorHandler
import com.app.travel.apputlis.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class BookingPresenter(
    private val bookingView: BookingView,
    private val bookingModel: BookingModel
) {
    private val compositeDisposable = CompositeDisposable()

    fun onCreateView() {
        onClick()
        getBookingListRequest()
        onItemClick()
    }

    private fun onClick() {
        bookingView.getDashboardObserable().doOnNext { bookingModel.getDashboadView() }.subscribe()
        //to move into bookingList page on clicking api image content i.e Tourname with it's date
        bookingView.getViewClickedObservable().doOnNext { bookingModel.getBookingView() }
            .subscribe()
    }

    // this function calls the api if found internet connection otherwise calls bookingView errors.
    private fun getBookingListRequest() {
        when {
            bookingView.checkNetwork() -> {
                BookingRequest()
            }
            else -> bookingView.getErrorMessage(ApiConstants.NONETWORK)
        }
    }

    //BookingApi request calling
    private fun BookingRequest() {
        bookingView.showProgressDialog(ApiConstants.PROCESSING)
        compositeDisposable.add(
            bookingModel.getBookingList(bookingView.getBookingListRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::bookingSuccess, this::bookingError)
        )
    }

    //bookingapi sucess on calling following function.
    private fun bookingSuccess(result: BookingResponse) {
        bookingView.hidProgressDialog()
        System.out.println("Result" + result)
        var termlist = result.data!!
        var bookingTitle = result.data!!.get(0).bookingTitle
        var bookingId = result.data!!.get(0).id
        System.out.println("BookingTitle is" + bookingTitle)
        if (bookingId != null) {
            UserInfo.id = bookingId
            System.out.println("BookingId is" + bookingId)
        }
        bookingView.showList(termlist, true)
        bookingView.setAdapter()
    }

    /*This set erros messages as Sorry ,booking items are not registered, if user has not registered in booking items.
    Otherwise sets errors as generated from api*/
    private fun bookingError(e: Throwable) {
        e.message?.let { Log.e(ContentValues.TAG, it) }
        if (e.message.equals("HTTP 404 Not Found")) {
            System.out.println("Message Error is " + e.message)
            bookingView.getErrorMessage("Sorry, booking items are not registered.")
        } else {
            bookingView.getErrorMessage(ResponseErrorHandler.handleErrorResponse(e))
        }
    }

    /* This function is defined to make booking items clickable in the adapter.*/
    private fun onItemClick() {
        val disposableObserver = getItemClickObserver()
        bookingView.getdetailObserable().subscribe(disposableObserver)
        compositeDisposable.add(disposableObserver)
    }
    private fun getItemClickObserver(): DisposableObserver<BookingData> {
        return object : DisposableObserver<BookingData>() {
            override fun onNext(bookingdata: BookingData) {}
            override fun onError(e: Throwable) {}
            override fun onComplete() {
                Timber.e("Clicked")
            }
        }
    }
}