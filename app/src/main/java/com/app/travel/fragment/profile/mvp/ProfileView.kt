package com.app.travel.fragment.profile.mvp

import android.graphics.drawable.Drawable
import android.os.Handler
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.travel.R
import com.app.travel.activity.dashboard.DashboardActivity
import com.app.travel.activity.userprofile.UserProfileActivity
import com.app.travel.activity.userprofile.dto.UserProfileData
import com.app.travel.activity.userprofile.dto.UserProfileResponse
import com.app.travel.apputlis.NetworkUtil
//import com.app.travel.databinding.ActivityProfileDataBinding
//import com.app.travel.databinding.ActivityUserProfileBinding
import com.app.travel.databinding.FragmentProfileBinding
import com.app.travel.dialog.ErrorDailog
import com.app.travel.fragment.profile.ProfileAdapter
import com.app.travel.fragment.profile.ProfileFragment
import com.elarkgroup.gwcl.appcomponent.CustomProgressBar
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable


class ProfileView(
    private val appCompatActivity: AppCompatActivity,
    private val profileAdapter: ProfileAdapter
) : FrameLayout(appCompatActivity) {

    var binding: FragmentProfileBinding? = null
    private var progressBar = CustomProgressBar()


    fun start(profilebinding: FragmentProfileBinding) {
        binding = profilebinding
        setAdapter()
        setRefreshListener()
    }

    //editpassword ImageView clickable
    fun getViewClickedObservable(): Observable<UserProfileData> {
        return profileAdapter.clickSubject
    }

    //editpassword clickable implmentation
    fun getdetailObserable(): Observable<UserProfileData> {
        return getViewClickedObservable()
    }

    //changepassword ImageView clickable
    fun getViewClickedPasswordChangeObservable(): Observable<UserProfileData> {
        return profileAdapter.clickSubjectChangePassword
    }

    //changepassword imageview implementation
    fun getdetailChangePasswordObserable(): Observable<UserProfileData> {
        return getViewClickedPasswordChangeObservable()
    }

    //error message when api cannot connect with the api
    fun getErrorMessage(message: String) {
        appCompatActivity.runOnUiThread(Runnable {
            ErrorDailog(
                appCompatActivity,
                message!!
            )
        })
    }

    fun showList(termlist: UserProfileData, aboolean: Boolean) {
        profileAdapter?.showListItems(termlist, aboolean)
    }

    fun checkNetwork(): Boolean {
        return NetworkUtil.checkInternetConnection(appCompatActivity)
    }

    /*this setAdapter() is to set adapter in views*/
    fun setAdapter() {
        var layoutmanager: LinearLayoutManager? = LinearLayoutManager(appCompatActivity)
        val firstVisiblePosition = layoutmanager!!.findFirstVisibleItemPosition()
        binding!!.userProfileRecyclerView.rvbookingItinerary.setHasFixedSize(true)
        binding!!.userProfileRecyclerView.rvbookingItinerary.setHasFixedSize(true)
        binding!!.userProfileRecyclerView.rvbookingItinerary.layoutManager = layoutmanager
        binding!!.userProfileRecyclerView.rvbookingItinerary.adapter = profileAdapter
        layoutmanager!!.scrollToPositionWithOffset(firstVisiblePosition, 0)
        profileAdapter.notifyDataSetChanged()
    }

    fun geUserDetailsRequest(): UserProfileResponse {
        return UserProfileResponse()
    }


    /*This functionality is for refreshing profile page  */
    fun setRefreshListener() {
        binding!!.swipeRefreshProfile.setOnRefreshListener {
            Handler().postDelayed(
                object : Runnable {
                    override fun run() {
                        binding!!.swipeRefreshProfile.setRefreshing(false)
                        loadFragment(ProfileFragment())
                    }
                }, 1000
            )
        }
    }

    /*This {@loadFragment(fragmentName())} calls the another fragments by replacing exisiting container*/
   fun loadFragment(fragment: Fragment) {
        val transaction = appCompatActivity.supportFragmentManager.beginTransaction()
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