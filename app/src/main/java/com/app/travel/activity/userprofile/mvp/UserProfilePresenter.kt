package com.app.travel.activity.userprofile.mvp
import io.reactivex.disposables.CompositeDisposable


class UserProfilePresenter(
    private val userProfileView: UserProfileView,
    private val userProfileModel: UserProfileModel){
    private val compositeDisposable = CompositeDisposable()

    fun onCreateView() {}

}

