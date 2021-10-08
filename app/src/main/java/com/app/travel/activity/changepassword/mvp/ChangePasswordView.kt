package com.app.travel.activity.changepassword.mvp
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.activity.changepassword.dto.ChangePasswordRequest
import com.app.travel.apputlis.NetworkUtil
import com.app.travel.databinding.ActivityChangePasswordBinding
import com.app.travel.dialog.ErrorDailog
import com.elarkgroup.gwcl.appcomponent.CustomProgressBar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class ChangePasswordView(private val appCompatActivity: AppCompatActivity) {
    var binding: ActivityChangePasswordBinding? = null
    var isOldPasswordValid: Boolean = false
    var isNewPasswordValid: Boolean = false
    var isConfirmPasswordValid: Boolean = false
    private var progressBar = CustomProgressBar()

    fun start(binding_changepasswordbinding: ActivityChangePasswordBinding) {
        binding = binding_changepasswordbinding
        getOldPasswordListner()
        getNewPasswordListner()
        getConfirmPasswordListner()
        loadOnlyClickable()
    }

    fun checkNetwork(): Boolean {
        return NetworkUtil.checkInternetConnection(appCompatActivity)
    }

    fun getErrorMessage(message: String) {
        appCompatActivity.runOnUiThread(Runnable {
            ErrorDailog(
                appCompatActivity,
                message!!)
        })
    }

    //back arrow
    fun getBackArrowObserable(): Observable<Any> {
        return RxView.clicks(binding!!.buttonArrow)
    }

    //new password edittextview
    fun getNewPassword(): String {
        System.out.println("FullName" + binding!!.etNewPassword.text.toString())
        return binding!!.etNewPassword.text.toString()
    }

    //old password edittextview
    fun getOldPassword(): String {
        System.out.println("DateBirth" + binding!!.etOldPassword.text.toString())
        return binding!!.etOldPassword.text.toString()
    }

    //confirmpassword edittextview
    fun getConfirmPassword(): String {
        System.out.println("EmailAddress" + binding!!.etConfirmPassword.text.toString())
        return binding!!.etConfirmPassword.text.toString()
    }

    //submit button
    fun btnConfirmPasswordObservable(): Observable<Any> {
        return RxView.clicks(binding!!.ivSubmitButton)
    }

    //progressDialog show
    fun showProgressDialog(statusString: String) {
        progressBar.show(appCompatActivity, statusString)
    }

    //progressDialog show
    fun hidProgressDialog() {
        progressBar.getDialog()!!.dismiss()
    }

    //oldpasswordlistener
    private fun getOldPasswordListner() {
        RxTextView.textChanges(binding!!.etOldPassword).skip(1)
            .map(CharSequence::toString)
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                when {
                    getOldPassword().isEmpty() -> {
                        isOldPasswordValid = false
                        getBtnEnable(false)
                    }
                    else -> {
                        isOldPasswordValid = true
                        when {
                            isValid() -> getBtnEnable(true)
                            else -> {
                                var myOldPasswordstr = binding!!.etOldPassword
                                myOldPasswordstr.validator()
                                    .nonEmpty()
                                    .minLength(8)
                                    .atleastOneUpperCase()
                                    .atleastOneSpecialCharacters()
                                    .atleastOneNumber()
                                    .addErrorCallback {
                                        //myPasswordstr.error = it (for red lined errors)
                                        val toast = Toast.makeText(appCompatActivity, it, Toast.LENGTH_SHORT)
                                        toast.show()
                                        val handler = Handler()
                                        handler.postDelayed(Runnable { toast.cancel() }, 1000)
                                    }.addSuccessCallback {

                                    }.check()
                                getBtnEnable(false)
                            }
                        }
                    }
                }
            }
            .subscribe()
    }

    //newpassword listener
    private fun getNewPasswordListner() {
        RxTextView.textChanges(binding!!.etNewPassword).skip(1)
            .map(CharSequence::toString)
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                when {
                    getNewPassword().isEmpty() -> {
                        isNewPasswordValid = false
                        getBtnEnable(false)
                    }
                    else -> {
                        isNewPasswordValid = true
                        when {
                            isValid() -> getBtnEnable(true)
                            else -> {
                                var myNewPasswordstr = binding!!.etNewPassword
                                myNewPasswordstr.validator()
                                    .nonEmpty()
                                    .minLength(8)
                                    .atleastOneUpperCase()
                                    .atleastOneSpecialCharacters()
                                    .atleastOneNumber()
                                    .addErrorCallback {
                                        //myPasswordstr.error = it (for red lined errors)
                                        val toast = Toast.makeText(appCompatActivity, it, Toast.LENGTH_SHORT)
                                        toast.show()
                                        val handler = Handler()
                                        handler.postDelayed(Runnable { toast.cancel() }, 1000)
                                    }.addSuccessCallback {

                                    }.check()
                                getBtnEnable(false)
                            }
                        }
                    }
                }
            }
            .subscribe()
    }

    //confirmpassword listener
    private fun getConfirmPasswordListner() {
        RxTextView.textChanges(binding!!.etConfirmPassword).skip(1)
            .map(CharSequence::toString)
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                when {
                    getConfirmPassword().isEmpty() -> {
                        isConfirmPasswordValid = false
                        getBtnEnable(false)
                    }
                    else -> {
                        isConfirmPasswordValid = true
                        when {
                            isValid() -> getBtnEnable(true)
                            else -> {
                                var myNewPasswordstr = binding!!.etNewPassword
                                myNewPasswordstr.validator()
                                    .nonEmpty()
                                    .minLength(8)
                                    .atleastOneUpperCase()
                                    .atleastOneSpecialCharacters()
                                    .atleastOneNumber()
                                    .addErrorCallback {
                                        //myPasswordstr.error = it (for red lined errors)
                                        val toast = Toast.makeText(appCompatActivity, it, Toast.LENGTH_SHORT)
                                        toast.show()
                                        val handler = Handler()
                                        handler.postDelayed(Runnable { toast.cancel() }, 1000)
                                    }.addSuccessCallback {
                                        setConfirmMessage()

                                    }.check()

                                getBtnEnable(false)
                            }
                        }
                    }
                }
            }
            .subscribe()
    }

    /*This function{@setConfirmMessage()} set error message to the user if entered password and confirm password are not same.*/
    fun setConfirmMessage() {
        if (getNewPassword().equals(getConfirmPassword())) {
            System.out.println("The UserPassword value and Confirm Password Value is " + getNewPassword() + getConfirmPassword())
        } else {
            Toast.makeText(
                appCompatActivity,
                "Make sure you have same passwords in password and confirm password",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun isValid(): Boolean {
        return when {
            isOldPasswordValid && checkPasswordVerification()
            -> true
            else -> false
        }
    }

    /*This function {@loadOnlyClickable()} is responsible to make button disable if user presses login button without entering inputs. */
    fun loadOnlyClickable() {
        when{
            isOldPasswordValid && checkPasswordVerification().equals(true) -> binding!!.ivSubmitButton.isEnabled = true
            else ->binding!!.ivSubmitButton.isEnabled = false
        }
    }

    /*The following function checkPasswordVerification checks the user's input as password and confirm password value.
     If password value == confirm password value, it enables the submit button, otherwise it disable the submit button.*/
    fun checkPasswordVerification(): Boolean {
        return when {
            getNewPassword().equals(getConfirmPassword()) -> true
            else -> false
        }
    }


    fun getBtnEnable(aboolena: Boolean) {
        when {
            aboolena -> binding!!.ivSubmitButton.isEnabled = true
            else -> binding!!.ivSubmitButton.isEnabled = false
        }
    }

    fun getChangePasswordRequest(): ChangePasswordRequest {
        return ChangePasswordRequest(
            old_password = getOldPassword(),
            new_password = getNewPassword(),
            confirm_password = getConfirmPassword(),
        )
    }
}