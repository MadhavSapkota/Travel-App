package com.app.travel.activity.signup
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.signup.di.DaggerSignUpComponent
import com.app.travel.activity.signup.di.SignUpModule
import com.app.travel.activity.signup.mvp.SignUpPresenter
import com.app.travel.activity.signup.mvp.SignUpView
import com.app.travel.app.AppApplication
import com.app.travel.databinding.ActivitySignupBinding
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    @Inject
    lateinit var signUpView: SignUpView

    @Inject
    lateinit var signUpPresenter: SignUpPresenter

    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mvpKotlinApplication=AppApplication()
        DaggerSignUpComponent.builder()
            .appComponent(mvpKotlinApplication.get(this).appComponent)
            .signUpModule(SignUpModule(this))
            .build()
            .inject(this)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        signUpView.start(binding)
        signUpPresenter.onCreateView()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SignUpActivity::class.java))
        }
    }
}