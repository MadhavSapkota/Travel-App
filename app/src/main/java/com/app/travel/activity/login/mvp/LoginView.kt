package com.app.travel.activity.login.mvp

import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.R
import com.app.travel.activity.dashboard.DashboardActivity
import com.app.travel.activity.login.dto.LoginRequest
import com.app.travel.apputlis.AppUtils
import com.app.travel.apputlis.NetworkUtil
import com.app.travel.apputlis.UserInfo
import com.app.travel.databinding.ActivityLoginBinding
import com.app.travel.dialog.ErrorDailog
import com.elarkgroup.gwcl.appcomponent.CustomProgressBar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class LoginView(private val appCompatActivity: AppCompatActivity) {
    var binding: ActivityLoginBinding? = null
    var isUsernameValid: Boolean = false
    var isPassworvalid: Boolean = false
    private var progressBar = CustomProgressBar()

    /*This functionality{@start(binding_login) is the starting point where xml layout is called here so we can get layout id's values to do various operations}*/
    fun start(binding_login: ActivityLoginBinding) {

        binding = binding_login
        getUsernameListner()
        getPasswordListner()
        setDashboardActivity()
        loadOnlyClickable()
    }

    /*This functionality {@getEmailAddress()} is to grap etEmail id from EmailAddress EditTextView XML Layout as login_activity*/
    fun getEmailAddress(): String {
        return binding!!.etEmail.text.toString()
    }

    /*This functionality {@getUserPassowrd()} is to grap etPassword id from Password EditTextView*/
    fun getUserPassowrd(): String {
        return binding!!.etPassword.text.toString()
    }

    /*This functionality {@getUsernameListner()} is to set action listner in etEmail EditTextView.
      It checks the username validations by taking rules from AppUtils.If this doesnot satisy
      the rules written in apputils, login button cannot be pressed.*/

    private fun getUsernameListner() {
        RxTextView.textChanges(binding!!.etEmail).skip(1)
            .map(CharSequence::toString)
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                when {
                    getEmailAddress().isEmpty() -> {
                        isUsernameValid = false
                        getBtnEnable(false)
                    }
                    else -> {
                        when {
                            AppUtils.isEmailValid(getEmailAddress()) -> {
                                isUsernameValid = true
                                when {
                                    isValid() -> getBtnEnable(true)
                                }
                            }
                            else -> {
                                isUsernameValid = false
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
            }
            .subscribe()
    }

    /*This functionality {@getPasswordListner()} is to set action listner in etPassword EditTextView.
      It checks the password validations by taking rules from AppUtils.If this doesnot satisy
      the rules written in apputils, login button cannot be pressed.*/

    private fun getPasswordListner() {
        RxTextView.textChanges(binding!!.etPassword).skip(1)
            .map(CharSequence::toString)
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                when {
                    getUserPassowrd().isEmpty() -> {
                        isPassworvalid = false
                        getBtnEnable(false)
                    }
                    else -> {
                        isPassworvalid = true
                        when {
                            isValid() -> getBtnEnable(true)
                            else -> {
                                getBtnEnable(false)
                            }
                        }
                    }
                }
            }
            .subscribe()
    }

    /*This functionality {@isValid()} checks that the etEmail and etPassword checks for rules satisfaction written in apputils.If
    they satisfy the rules it returns true , otherwise it returns false*/
    private fun isValid(): Boolean {
        return when {
            isUsernameValid && isPassworvalid -> true
            else -> false
        }
    }


    /*This functionality {@getBtnEnable(params) ensures for login button activation and deactivation} */
    fun getBtnEnable(aboolena: Boolean) {
        when {
            aboolena -> binding!!.btnLogin.isEnabled = true
            else -> binding!!.btnLogin.isEnabled = false
        }
    }

    /*This functionality {@getLoginObserable()} is to grap btnLogin id from Login Button.*/
    fun getLoginObserable(): Observable<Any> {
        return RxView.clicks(binding!!.btnLogin)
    }


    /*This functionality {@getLoginRequest} makes a request to the api by taking user entered email input and password input to the server.*/
    fun getLoginRequest(): LoginRequest {
        return LoginRequest(
            email = getEmailAddress(),
            password = getUserPassowrd(),
        )
    }


    /*
     When a user clicks submit button following functionality {@setBrighterLoginImage} setBrighterLoginImage changes the color of the submit button
     to little brightness. ie. from gradient_drawable to shapeemail
     */
    fun setBrighterLoginImage() {
        binding!!.btnLogin.setImageResource(R.drawable.submitbrightness)
    }

    fun setDimLoginImage(){
        binding!!.btnLogin.setImageResource(R.drawable.gradient_drawable)
    }

    /*This functionality {@showProgressDialog(params)} is to set display progress dialog box when user tries to login into the system.
      params: statusString as "loading........."*/
    fun showProgressDialog(statusString: String) {
        progressBar.show(appCompatActivity, statusString)
    }

    /*This functionality {@ hidProgressDialog()} is to hide progress dialog box if user enters correct information to login into the system
    */
    fun hidProgressDialog() {
        progressBar.getDialog()!!.dismiss()
    }

    /*This functionality {@getErrorMessage()} is to set Error message in dialog box when user enters wrong username and password.*/
    fun getErrorMessage(message: String) {
        appCompatActivity.runOnUiThread(Runnable {
            ErrorDailog(appCompatActivity, message!!)
        })
    }

    /*This functionality {@getSignupObserable()} is to grap tvSignUp id from SignUp TextView XML Layout login_activity*/
    fun getSignupObserable(): Observable<Any> {
        return RxView.clicks(binding!!.tvSignUp)
    }

    /*This functionality{@getForgotPasswordObservable() }*/
    fun getForgotPasswordObserable(): Observable<Any> {
        return RxView.clicks(binding!!.tvForget)
    }

    /*This functionality {@checkNetwork()} works to ensure for network connections.*/
    fun checkNetwork(): Boolean {
        return NetworkUtil.checkInternetConnection(appCompatActivity)
    }


    /* This functionality {@getDashboardActivity()} is for login Session Management.If a user has already logininto the system then he/she doesnot need to
   make login again and again.On clicking back button to exit from the system without clicking logout alert dialog box.He/She can directly enter into
   system without login.*/
    fun setDashboardActivity() {
        System.out.println("Dashboard loginSatus" + UserInfo.loginStatus)
        if (UserInfo.loginStatus.equals(true)) {
            DashboardActivity.start(appCompatActivity)
        }
    }

    /*This function {@loadOnlyClickable()} is responsible to make button disable if user presses login button without entering inputs. */
    fun loadOnlyClickable() {
        if (isUsernameValid && isPassworvalid.equals(true)) {
            binding!!.btnLogin.isEnabled = true
        } else {
            binding!!.btnLogin.isEnabled = false
        }
    }
}