package com.app.travel.activity.userprofile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.travel.R
import com.app.travel.activity.userprofile.di.DaggerUserProfileComponent
import com.app.travel.activity.userprofile.di.UserProfileModule
import com.app.travel.activity.userprofile.mvp.UserProfilePresenter
import com.app.travel.activity.userprofile.mvp.UserProfileView
import com.app.travel.app.AppApplication
import com.app.travel.databinding.ActivityUserProfileBinding
import com.app.travel.fragment.profile.ProfileFragment
import javax.inject.Inject


class UserProfileActivity : AppCompatActivity() {
    @Inject
    lateinit var userProfileView: UserProfileView

    @Inject
    lateinit var userProfilePresenter: UserProfilePresenter
    private lateinit var binding: ActivityUserProfileBinding
    val ImageREQUESTID = 102
    var showEt: Boolean?=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mvpKotlinApplication = AppApplication()
        DaggerUserProfileComponent.builder()
            .appComponent(mvpKotlinApplication.get(this).appComponent)
            .userProfileModule(UserProfileModule(this))
            .build()
            .inject(this)
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater())
        val view = binding.root
        userProfileView.start(binding)
        setContentView(view)
        showEt = true
        userProfilePresenter.onCreateView()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, UserProfileActivity::class.java))
        }
    }
}