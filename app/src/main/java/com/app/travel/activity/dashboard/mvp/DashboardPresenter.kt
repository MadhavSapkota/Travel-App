package com.app.travel.activity.dashboard.mvp

import android.R
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.travel.activity.dashboard.dto.DashboardData
import com.app.travel.activity.dashboard.dto.DashboardResponse
import com.app.travel.apputlis.ApiConstants
import com.app.travel.apputlis.ResponseErrorHandler.handleErrorResponse
import com.app.travel.apputlis.UserInfo
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class DashboardPresenter(
    private val dashboardView: DashboardView,
    private val dashboardModel: DashboardModel
) {
    private val compositeDisposable = CompositeDisposable()


    fun onCreateView() {
    }

}
