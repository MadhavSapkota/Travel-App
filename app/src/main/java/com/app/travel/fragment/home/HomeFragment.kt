package com.app.travel.fragment.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.travel.app.AppApplication
import com.app.travel.databinding.FragmentHomeBinding
import com.app.travel.fragment.home.di.DaggerHomeComponent
import com.app.travel.fragment.home.di.HomeModule
import com.app.travel.fragment.home.mvp.HomePresenter
import com.app.travel.fragment.home.mvp.HomeView

import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var homeView: HomeView

    @Inject
    lateinit var homePresenter: HomePresenter

    var showEt: Boolean?=false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val hrApplication=AppApplication()
        DaggerHomeComponent.builder()
            .appComponent(hrApplication.get(requireActivity()).appComponent)
            .homeModule(HomeModule(activity as AppCompatActivity))
            .build()
            .inject(this)

        var binding= FragmentHomeBinding.inflate(layoutInflater)
        val view=binding.root
        homeView.start(binding)
        homePresenter.onCreate()
        return view

    }


    companion object {
        fun start(): HomeFragment {
            val fragment=HomeFragment()
            return fragment
        }
    }

}