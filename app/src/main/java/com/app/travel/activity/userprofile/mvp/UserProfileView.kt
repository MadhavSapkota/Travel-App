package com.app.travel.activity.userprofile.mvp
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.databinding.ActivityUserProfileBinding


class UserProfileView(
    private val appCompatActivity: AppCompatActivity,
) {
    var binding: ActivityUserProfileBinding? = null
    fun start(binding_profilebinding: ActivityUserProfileBinding) {
        binding = binding_profilebinding
    }

}