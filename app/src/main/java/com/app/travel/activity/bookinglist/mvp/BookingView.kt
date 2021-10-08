package com.app.travel.activity.bookinglist.mvp
import android.app.SearchManager
import android.content.Context
import android.os.Handler
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.travel.R
import com.app.travel.activity.bookingdetails.BookingDetailsActivity
import com.app.travel.activity.bookinglist.BookingActivity
import com.app.travel.activity.bookinglist.BookingAdapter
import com.app.travel.activity.bookinglist.dto.BookingData
import com.app.travel.activity.bookinglist.dto.BookingResponse
import com.app.travel.apputlis.NetworkUtil
import com.app.travel.databinding.FragmentBookingBinding
import com.app.travel.dialog.ErrorDailog
import com.app.travel.fragment.home.HomeFragment
import com.elarkgroup.gwcl.appcomponent.CustomProgressBar
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable


class BookingView(
    private val appCompatActivity: AppCompatActivity,
    private val bookingAdapter: BookingAdapter,
    var searchManager: SearchManager = appCompatActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager): SearchView.OnQueryTextListener {
    var binding: FragmentBookingBinding? = null
    private var progressBar = CustomProgressBar()
    fun start(binding_booking: FragmentBookingBinding) {
        binding = binding_booking
        searchViewImplemtn()
        setAdapter()
        setRefreshListener()
    }
    //back arrow implementation
    fun getDashboardObserable(): Observable<Any> {
        return RxView.clicks(binding!!.btnBackArrow)
    }

    //searchview implementation
    fun searchViewImplemtn(){
        binding!!.searchView.setSearchableInfo(searchManager.getSearchableInfo(appCompatActivity!!.componentName))
        binding!!.searchView.setOnQueryTextListener(this)
    }

    fun getErrorMessage(message: String) {
        appCompatActivity.runOnUiThread(Runnable {
            ErrorDailog(
                appCompatActivity,
                message!!
            )
        })
    }

    fun showList(termlist: ArrayList<BookingData>?, aboolean: Boolean) {
        bookingAdapter?.showListItems(termlist, aboolean)
    }

    //api contents clickable
    fun getViewClickedObservable(): Observable<BookingData> {
        return bookingAdapter.clickSubject
    }
    //api contents clickable implementation
    fun getdetailObserable(): Observable<BookingData> {
        return getViewClickedObservable()
    }

    fun checkNetwork(): Boolean {
        return NetworkUtil.checkInternetConnection(appCompatActivity)
    }

    //setting adapter in views
    fun setAdapter() {
        var layoutmanager: LinearLayoutManager? = GridLayoutManager(appCompatActivity,2)
        val firstVisiblePosition = layoutmanager!!.findFirstVisibleItemPosition()
        binding!!.dashboardRecyclerview.rvBookingList.setHasFixedSize(true)
        binding!!.dashboardRecyclerview.rvBookingList.setHasFixedSize(true)
        binding!!.dashboardRecyclerview.rvBookingList.layoutManager = layoutmanager
        binding!!.dashboardRecyclerview.rvBookingList.adapter = bookingAdapter
        layoutmanager!!.scrollToPositionWithOffset(firstVisiblePosition, 0)
    }

    fun getBookingListRequest(): BookingResponse {
        return BookingResponse()
    }

    //searchable overide functionality
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }
    //here calls booking adapter.getFilter() methods
    override fun onQueryTextChange(textChange: String?): Boolean {
        bookingAdapter.getFilter()?.filter(textChange)
        return true
    }

    /*This functionality is for refreshing Home Data*/
    fun setRefreshListener() {
        binding!!.swipeRefreshBooking.setOnRefreshListener {
            Handler().postDelayed(
                object : Runnable {
                    override fun run() {
                        binding!!.swipeRefreshBooking.setRefreshing(false)
                        BookingActivity.start(appCompatActivity)
                        appCompatActivity.finish()
                    }
                }, 1000
            )
        }
    }

    /*This functionality {@showProgressDialog(params)} is to set display progress dialog box when user tries to login into the system.
    params: statusString as "loading........."*/
    fun showProgressDialog(statusString: String) {
        progressBar.show(appCompatActivity, statusString)
    }

    /*This functionality {@ hidProgressDialog()} is to hide progress dialog box if user enters correct information to login into the system*/
    fun hidProgressDialog() {
        progressBar.getDialog()!!.dismiss()
    }
}