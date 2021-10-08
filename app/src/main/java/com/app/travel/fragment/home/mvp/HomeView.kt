package com.app.travel.fragment.home.mvp
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.travel.R
import com.app.travel.activity.dashboard.DashboardActivity
import com.app.travel.activity.dashboard.dto.DashboardData
import com.app.travel.activity.dashboard.dto.DashboardResponse
import com.app.travel.activity.login.LoginActivity
import com.app.travel.apputlis.NetworkUtil
import com.app.travel.apputlis.UserInfo
import com.app.travel.databinding.FragmentHomeBinding
import com.app.travel.dialog.ErrorDailog
import com.app.travel.dialog.HomeErrorDailog
import com.app.travel.fragment.home.HomeAdapter
import com.app.travel.fragment.home.HomeFragment
import com.elarkgroup.gwcl.appcomponent.CustomProgressBar
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeView(
    private val appCompatActivity: AppCompatActivity,
    private val homeAdapter: HomeAdapter
) : FrameLayout(appCompatActivity,) {

    var binding: FragmentHomeBinding?=null
    private var progressBar = CustomProgressBar()

    fun start(homebinding: FragmentHomeBinding) {
        binding=homebinding
        setAdapter()
        setUserName()
        setRefreshListener()
    }

    //bookingView imageview
    fun getBookingObserable(): Observable<Any> {
        return RxView.clicks(binding!!.imageView7)
    }

    //SeeAll textview
    fun getSeeAllObservable(): Observable<Any> {
        return RxView.clicks(binding!!.textViewSeeAll)
    }

    //ItineraryView imageView
    fun getItineraryObservableView(): Observable<Any> {
        return RxView.clicks(binding!!.imageView9)
    }

    /*This functionality {@getViewClickedObservable} is to make clickable with the api items.*/
    fun getViewClickedObservable(): Observable<DashboardData> {
        return homeAdapter.clickSubject
    }

    /*This functionality{@getdetailObservable()} is to get clickable api items.*/
    fun getdetailObserable(): Observable<DashboardData> {
        return getViewClickedObservable()
    }

   /*This funtionality {@getErrorMessage()} shows api actual errors loaded from ErrorDialogBox.*/
    fun getErrorMessage(message: String) {
        appCompatActivity.runOnUiThread(Runnable { ErrorDailog(
            appCompatActivity,
            message!!) })
    }

    /*This functinality{@getEmptyBookingItems} shows api error messages(modified as "Sorry, you have not registered booking list" message) from HomeErrorDialogBox. */
    fun getEmptyBookingItems(message: String){
        appCompatActivity.runOnUiThread(Runnable { HomeErrorDailog(
            appCompatActivity,
            message!!) })
    }

    /*This function {@checkNetwork()} checks the network connectivity.*/
    fun checkNetwork(): Boolean {
        return NetworkUtil.checkInternetConnection(appCompatActivity)
    }

    /*This function {@showList(params)} shows the api data which are set in adapters.*/
    fun showList(termlist: ArrayList<DashboardData>?, aboolean: Boolean) {
        homeAdapter?.showListItems(termlist, aboolean)
    }

    /**This function {@setAdapter()} deals of setting adapters in homeview.*/
    fun setAdapter() {
        var layoutmanager: GridLayoutManager? = GridLayoutManager(appCompatActivity,2)
        val firstVisiblePosition = layoutmanager!!.findFirstVisibleItemPosition()
        binding!!.includesDashboardRecyclerview.rvBookingList.setHasFixedSize(true)
        binding!!.includesDashboardRecyclerview.rvBookingList.layoutManager = layoutmanager
        binding!!.includesDashboardRecyclerview.rvBookingList.adapter = homeAdapter
        layoutmanager!!.scrollToPositionWithOffset(firstVisiblePosition, 0)

    }

    /*This function {@getDashboardRequest()} deals with the api calling*/
    fun getDashboardRequest(): DashboardResponse {
        return DashboardResponse()
    }

    /*This function{@setUserName()} is to set username in dashboard by retrieving User's fullName from Shared Preferences.*/
    fun setUserName() {
        binding!!.tVUsername.text = UserInfo.fullname
        System.out.println("ClientFullName is " + UserInfo.fullname)
    }


    /*This function{@hideSeeAll()} is to hide SEE ALL textView*/
    fun hideSeeAll(){
        binding!!.textViewSeeAll.setVisibility(View.GONE)
    }


    /*This functionality{@setRefreshListener()} is for refreshing home data by calling HomeFragments*/
    fun setRefreshListener() {
        binding!!.swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(
                object : Runnable {
                    override fun run() {
                        binding!!.swipeRefresh.setRefreshing(false)
//                        progressBar.show(appCompatActivity, statusString)
                        loadFragment(HomeFragment())
                    }
                }, 1000
            )
        }
    }

   /*This {@loadFragment()} deals about loading fragments with transcation operations.*/
    private fun loadFragment(fragment: Fragment) {
        val transaction=appCompatActivity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view_dashboard, fragment)
        transaction.commit()
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
