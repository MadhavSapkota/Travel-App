package com.app.travel.fragment.home.mvp

import android.content.ContentValues
import android.util.Log
import com.app.travel.activity.dashboard.dto.DashboardData
import com.app.travel.activity.dashboard.dto.DashboardResponse
import com.app.travel.apputlis.ApiConstants
import com.app.travel.apputlis.ResponseErrorHandler
import com.app.travel.apputlis.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class HomePresenter(
    private val homeView: HomeView,
    private val homeModel: HomeModel
) {
    private val compositeDisposable = CompositeDisposable()
    fun onCreate() {
        onClick()
        getDashboardRequest()
        onItemClick()
    }

    private fun onClick() {
        //to move into bookingList page on clicking imageView7 i.e booking Button
        homeView.getBookingObserable().doOnNext { homeModel.getBookingView() }.subscribe()

        //to move into bookingDetails page on clicking api image content i.e Tourname with it's date
        homeView.getViewClickedObservable().doOnNext { homeModel.getBookingDetailsView() }
            .subscribe()

        //see all
        homeView.getSeeAllObservable().doOnNext { homeModel.getBookingView() }.subscribe()

        //ItineraryList Activity
        homeView.getItineraryObservableView().doOnNext {homeModel.getItineraryListView()}.subscribe()
    }


    private fun getDashboardRequest() {
        when {
            homeView.checkNetwork() -> {
                DashboardRequest()
            }
            else -> homeView.getErrorMessage(ApiConstants.NONETWORK)
        }
    }

    /*Calling Dashboard api request*/
    private fun DashboardRequest() {
        homeView.showProgressDialog(ApiConstants.PROCESSING)
        compositeDisposable.add(
            homeModel.getDashboard(homeView.getDashboardRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::dashboardSuccess, this::dashboardError)
        )
    }

    /*This function is called when api is sucessful*/
    private fun dashboardSuccess(result: DashboardResponse) {
        homeView.hidProgressDialog()
        System.out.println("Result" + result)
        var termlist = result.data
        var bookingTitle = result.data!!.get(0).bookingTitle
        var bookingId = result.data!!.get(0).id

        UserInfo.tourname = bookingTitle!!
        System.out.println("TourName is" + bookingTitle)
        if (bookingId != null) {
            UserInfo.id = bookingId
            System.out.println("BookingId is" + bookingId)
        }
        UserInfo.loginStatus=true
        homeView.showList(termlist, true)
        homeView.setAdapter()
    }


    /*This function generates errors if a users doesnot have registered booking items.Home AlertDialog Box is called here.*/
    private fun dashboardError(e: Throwable) {
        e.message?.let { Log.e(ContentValues.TAG, it) }
        if (e.message.equals("HTTP 404 Not Found")) {
            homeView.hideSeeAll()
            System.out.println("Message Error is " + e.message)
            homeView.getEmptyBookingItems("Sorry, booking items are not registered.")
        } else {
            homeView.getErrorMessage(ResponseErrorHandler.handleErrorResponse(e))
        }
    }

    /*This function is defined to make booking items clickable in the adapter.*/
    private fun onItemClick() {
        val disposableObserver = getItemClickObserver()
        homeView.getdetailObserable().subscribe(disposableObserver)
        compositeDisposable.add(disposableObserver)
    }
    /*This function is called to make api items clickable*/
    private fun getItemClickObserver(): DisposableObserver<DashboardData> {
        return object : DisposableObserver<DashboardData>() {
            override fun onNext(dashboardResponse: DashboardData) {}
            override fun onError(e: Throwable) {}
            override fun onComplete() {
                System.out.println("clicked")
                Timber.e("Clicked")
            }
        }
    }

}