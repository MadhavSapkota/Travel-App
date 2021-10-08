package com.app.travel.activity.dashboard
import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.travel.activity.dashboard.di.DaggerDashboardComponent
import com.app.travel.activity.dashboard.di.DashboardModule
import com.app.travel.activity.dashboard.mvp.DashboardPresenter
import com.app.travel.activity.dashboard.mvp.DashboardView
import com.app.travel.app.AppApplication
import com.app.travel.databinding.ActivityDashBoardBinding
import com.app.travel.databinding.FragmentHomeBinding
import javax.inject.Inject

class DashboardActivity : AppCompatActivity() {

    @Inject
    lateinit var dashboardView: DashboardView

    @Inject
    lateinit var dashboardPresenter: DashboardPresenter

    private lateinit var binding: ActivityDashBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mvpKotlinApplication=AppApplication()
        DaggerDashboardComponent.builder()
            .appComponent(mvpKotlinApplication.get(this).appComponent)
            .dashboardModule(DashboardModule(this))
            .build()
            .inject(this)

        binding= ActivityDashBoardBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        dashboardView.start(binding)
        dashboardPresenter.onCreateView()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, DashboardActivity::class.java))
        }

    }
}


