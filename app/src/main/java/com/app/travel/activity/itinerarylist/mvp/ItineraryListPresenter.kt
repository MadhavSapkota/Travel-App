package com.app.travel.activity.itinerarylist.mvp
import android.content.ContentValues
import android.util.Log
import com.app.travel.activity.itinerarylist.dto.ItineraryListData
import com.app.travel.activity.itinerarylist.dto.ItineraryListResponse
import com.app.travel.apputlis.ApiConstants
import com.app.travel.apputlis.ResponseErrorHandler
import com.app.travel.apputlis.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class ItineraryListPresenter(
    private val itinerarylistView: ItineraryListView,
    private val itinerarylistModel: ItineraryListModel){
    private val compositeDisposable = CompositeDisposable()

    fun onCreateView() {
        onClick()
        getBookingDetailsListRequest()
        onItemClick()
    }

    private fun onClick(){
        itinerarylistView.getViewClickedObservable().doOnNext {itinerarylistModel.getItineraryView()}.subscribe()
        itinerarylistView.getBackArrow().doOnNext {itinerarylistModel.getBookingDetailsView()}.subscribe()
    }

    private fun getBookingDetailsListRequest() {
        when {
            itinerarylistView.checkNetwork() -> {
                ItineraryListRequest()
            }
            else -> itinerarylistView.getErrorMessage(ApiConstants.NONETWORK)
        }
    }


    private fun ItineraryListRequest() {
        itinerarylistView.showProgressDialog(ApiConstants.PROCESSING)
        compositeDisposable.add(
            itinerarylistModel.getBookingDetails(itinerarylistView.getItineraryListRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::itineraryListSuccess, this::itineraryListError)
        )
    }


    private fun itineraryListSuccess(result: ItineraryListResponse) {
        itinerarylistView.hidProgressDialog()
        System.out.println("Result" + result)
        var termlist = result.data
        itinerarylistView.showList(termlist, true)
        itinerarylistView.setAdapter()
    }

    private fun itineraryListError(e: Throwable) {
        itinerarylistView.getErrorMessage(ResponseErrorHandler.handleErrorResponse(e))
        e.message?.let { Log.e(ContentValues.TAG, it) }
    }


    /*Following functions are defined to make booking items clickable in the adapter.*/
    private fun onItemClick() {
        val disposableObserver = getItemClickObserver()
        itinerarylistView.getdetailObserable().subscribe(disposableObserver)
        compositeDisposable.add(disposableObserver)
    }
    private fun getItemClickObserver(): DisposableObserver<ItineraryListData> {
        return object : DisposableObserver<ItineraryListData>() {
            override fun onNext(itinerarylistdata: ItineraryListData) {
                UserInfo.itineraryId = itinerarylistdata.id!!
                System.out.println("ItineraryId is" + UserInfo.itineraryId)
            }
            override fun onError(e: Throwable) {
                System.err.println("Error"+ e )
            }
            override fun onComplete() {
                Timber.e("Clicked")
            }
        }
    }
}