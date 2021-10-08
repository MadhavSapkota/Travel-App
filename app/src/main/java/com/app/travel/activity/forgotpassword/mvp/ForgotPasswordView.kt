package com.app.travel.activity.forgotpassword.mvp
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.R
import com.app.travel.activity.forgotpassword.dto.ForgotPasswordRequest
import com.app.travel.apputlis.AppUtils
import com.app.travel.apputlis.NetworkUtil
import com.app.travel.databinding.ActivityForgotPasswordBinding
import com.app.travel.dialog.ErrorDailog
import com.elarkgroup.gwcl.appcomponent.CustomProgressBar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class ForgotPasswordView(private val appCompatActivity: AppCompatActivity) {
    var binding: ActivityForgotPasswordBinding?=null
    var isUsernameValid: Boolean=false  //email
    private var progressBar = CustomProgressBar()

    fun start(binding_forgotPassword: ActivityForgotPasswordBinding) {

        binding=binding_forgotPassword
        getUsernameListner()
        loadOnlyClickable()
    }

    fun getEmailAddress(): String {
        return binding!!.etUsername.text.toString()
    }

   //email to make request for forgot password
    private fun getUsernameListner() {
        RxTextView.textChanges(binding!!.etUsername).skip(1)
            .map(CharSequence::toString)
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                when {
                    getEmailAddress().isEmpty() -> {
                        isUsernameValid=false
                        getBtnEnable(false)
                    }
                    else -> {
                        when {
                            AppUtils.isEmailValid(getEmailAddress()) -> {
                                isUsernameValid=true
                                when {
                                    isValid() -> getBtnEnable(true)
                                }
                            }
                            else -> {
                                isUsernameValid=false
                                var myEmailStr = getEmailAddress()
                                myEmailStr.validEmail() {
                                    // This method will be called when myEmailStr is not a valid email.
                                    val toast = Toast.makeText(appCompatActivity, it, Toast.LENGTH_SHORT)
                                    toast.show()
                                    val handler = Handler()
                                    handler.postDelayed(Runnable { toast.cancel() }, 1000)
                                }
                                getBtnEnable(false)
                            }
                        }
                    }
                }
            }.subscribe()
    }

    private fun isValid(): Boolean {
        return when {
            isUsernameValid -> true
            else -> false
        }
    }

    /*This function {@loadOnlyClickable()} is responsible to make button disable if user presses login button without entering inputs. */
    fun loadOnlyClickable(){
        if( isUsernameValid.equals(true)){
            binding!!.btnLogin.isEnabled = true

        }
        else{
            binding!!.btnLogin.isEnabled = false
        }
    }

    fun getBtnEnable(aboolena: Boolean) {
        when {
            aboolena -> binding!!.btnLogin.isEnabled=true
            else -> binding!!.btnLogin.isEnabled=false
        }
    }

    fun getForgotPasswordObserable(): Observable<Any> {
        return RxView.clicks(binding!!.btnLogin)
    }

    fun getLoginObserable(): Observable<Any> {
        return RxView.clicks(binding!!.buttonArrow)
    }


    /*
    When a user clicks submit button following functionality setSignUpImage changes the color of the submit button
    to little brightness. ie. from gradient_drawable to shapeemail
    */

    fun setBrighterButton(){
        binding!!.btnLogin.setImageResource(R.drawable.submitbrightness)
    }
    fun setDimForgotImage(){
        binding!!.btnLogin.setImageResource(R.drawable.gradient_drawable)
    }

    fun getForgotPasswordRequest(): ForgotPasswordRequest {
        return ForgotPasswordRequest(
            forgot_email= getEmailAddress(),
        )
    }

    fun showProgressDialog(statusString: String) {
        progressBar.show(appCompatActivity, statusString)
    }

    fun hidProgressDialog() {
        progressBar.getDialog()!!.dismiss()
    }

    fun getErrorMessage(message:String){
        appCompatActivity.runOnUiThread(Runnable {
            ErrorDailog(appCompatActivity, message!!)
        })
    }

    fun checkNetwork(): Boolean {
        return NetworkUtil.checkInternetConnection(appCompatActivity)
    }
}
