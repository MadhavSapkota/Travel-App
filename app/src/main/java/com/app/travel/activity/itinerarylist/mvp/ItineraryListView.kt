package com.app.travel.activity.itinerarylist.mvp
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.travel.activity.itinerarylist.ItineraryListAdapter
import com.app.travel.activity.itinerarylist.dto.ItineraryListData
import com.app.travel.activity.itinerarylist.dto.ItineraryListResponse
import com.app.travel.apputlis.NetworkUtil
import com.app.travel.apputlis.UserInfo.tourname
import com.app.travel.databinding.FragmentItineraryListBinding
import com.app.travel.dialog.ErrorDailog
import com.elarkgroup.gwcl.appcomponent.CustomProgressBar
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable


class ItineraryListView(
    private val appCompatActivity: AppCompatActivity,
    private val itineraryListAdapter: ItineraryListAdapter
) {
    var binding: FragmentItineraryListBinding? = null
    private var progressBar = CustomProgressBar()
    fun start(binding_itinerarylist: FragmentItineraryListBinding) {
        binding = binding_itinerarylist
        setAdapter()
        setTitleName()
    }

    fun setTitleName() {
        val titleName = binding!!.tvBackgroundTitle
        titleName.text = tourname
    }

    //back arrow
    fun getBackArrow(): Observable<Any> {
        return RxView.clicks(binding!!.buttonArrow)
    }

    //set error
    fun getErrorMessage(message: String) {
        appCompatActivity.runOnUiThread(Runnable {
            ErrorDailog(
                appCompatActivity,
                message!!
            )
        })
    }

    fun showList(termlist: List<ItineraryListData>?, aboolean: Boolean) {
        itineraryListAdapter?.showListItems(termlist, aboolean)
    }

    //api contents clickable
    fun getViewClickedObservable(): Observable<ItineraryListData> {
        return itineraryListAdapter.clickSubject
    }

    //api contents cliakable implementation
    fun getdetailObserable(): Observable<ItineraryListData> {
        return getViewClickedObservable()
    }

    fun checkNetwork(): Boolean {
        return NetworkUtil.checkInternetConnection(appCompatActivity)
    }

    /*For adapters calling and setting recyclerview */
    fun setAdapter() {
        var layoutmanager: LinearLayoutManager? = LinearLayoutManager(appCompatActivity)
        val firstVisiblePosition = layoutmanager!!.findFirstVisibleItemPosition()
        binding!!.bookingItineraryRecyclerview.rvbookingItinerary.setHasFixedSize(true)
        binding!!.bookingItineraryRecyclerview.rvbookingItinerary.setHasFixedSize(true)
        binding!!.bookingItineraryRecyclerview.rvbookingItinerary.layoutManager = layoutmanager
        binding!!.bookingItineraryRecyclerview.rvbookingItinerary.adapter = itineraryListAdapter
        layoutmanager!!.scrollToPositionWithOffset(firstVisiblePosition, 0)
    }

    fun getItineraryListRequest(): ItineraryListResponse {
        return ItineraryListResponse()
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