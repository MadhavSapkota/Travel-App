package com.app.travel.activity.changepassword
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.changepassword.di.ChangePasswordModule
import com.app.travel.activity.changepassword.di.DaggerChangePasswordComponent
import com.app.travel.activity.changepassword.mvp.ChangePasswordPresenter
import com.app.travel.activity.changepassword.mvp.ChangePasswordView
import com.app.travel.app.AppApplication
import com.app.travel.databinding.ActivityChangePasswordBinding
import javax.inject.Inject


class   ChangePasswordActivity : AppCompatActivity() {

    @Inject
    lateinit var changePasswordView: ChangePasswordView

    @Inject
    lateinit var changePasswordPresenter: ChangePasswordPresenter

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mvpKotlinApplication = AppApplication()
        DaggerChangePasswordComponent.builder()
            .appComponent(mvpKotlinApplication.get(this).appComponent)
            .changePasswordModule(ChangePasswordModule(this))
            .build()
            .inject(this)

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        val view = binding.root
        changePasswordView.start(binding)
        setContentView(view)
        changePasswordPresenter.onCreateView()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ChangePasswordActivity::class.java))
        }
    }
}
