package com.app.travel.fragment.profile
//import com.app.travel.databinding.FragmentUserProfileBinding
//import com.app.travel.databinding.ActivityUserProfileBinding
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.travel.R
import com.app.travel.app.AppApplication
import com.app.travel.databinding.FragmentProfileBinding
import com.app.travel.fragment.profile.di.DaggerProfileComponent
import com.app.travel.fragment.profile.di.ProfileModule
import com.app.travel.fragment.profile.mvp.ProfilePresenter
import com.app.travel.fragment.profile.mvp.ProfileView
import javax.inject.Inject


class ProfileFragment : Fragment() {

    @Inject
    lateinit var profileView: ProfileView

    @Inject
    lateinit var profilePresenter: ProfilePresenter

    var showEt: Boolean?=false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val hrApplication=AppApplication()
        DaggerProfileComponent.builder()
            .appComponent(hrApplication.get(requireActivity()).appComponent)
            .profileModule(ProfileModule(activity as AppCompatActivity))
            .build()
            .inject(this)
        var binding= FragmentProfileBinding.inflate(layoutInflater)
        val view=binding.root
        profileView.start(binding)
        profilePresenter.onCreate()
        showEt = true
        return view

    }


    companion object {
        fun start(): ProfileFragment {
            val fragment=ProfileFragment()
            return fragment
        }
    }
}

