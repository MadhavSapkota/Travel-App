package com.app.travel.activity.forgotpassword
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.forgotpassword.di.DaggerForgotPasswordComponent
import com.app.travel.activity.forgotpassword.di.ForgotPasswordModule
import com.app.travel.activity.forgotpassword.mvp.ForgotPasswordPresenter
import com.app.travel.activity.forgotpassword.mvp.ForgotPasswordView
import com.app.travel.app.AppApplication
import com.app.travel.databinding.ActivityForgotPasswordBinding
import javax.inject.Inject

class ForgotPasswordActivity : AppCompatActivity() {

    @Inject
    lateinit var forgotPasswordView: ForgotPasswordView

    @Inject
    lateinit var forgotPresenter: ForgotPasswordPresenter

    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mvpKotlinApplication=AppApplication()
        DaggerForgotPasswordComponent.builder()
            .appComponent(mvpKotlinApplication.get(this).appComponent)
            .forgotPasswordModule(ForgotPasswordModule(this))
            .build()
            .inject(this)
        binding= ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        forgotPasswordView.start(binding)
        forgotPresenter.onCreateView()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ForgotPasswordActivity::class.java))
        }
    }


}