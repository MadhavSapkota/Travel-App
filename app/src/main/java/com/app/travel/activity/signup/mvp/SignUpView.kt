package com.app.travel.activity.signup.mvp

import android.R.layout
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Handler
import android.text.method.DigitsKeyListener
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import com.app.travel.R
import com.app.travel.R.drawable.submitbrightness
import com.app.travel.activity.signup.dto.SignUpRequest
import com.app.travel.apputlis.AppUtils
import com.app.travel.apputlis.NetworkUtil
import com.app.travel.apputlis.rules.ValidationRule
import com.app.travel.databinding.ActivitySignupBinding
import com.app.travel.dialog.ErrorDailog
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.elarkgroup.gwcl.appcomponent.CustomProgressBar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.numberEqualTo
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


class SignUpView(private val appCompatActivity: AppCompatActivity) : OnItemSelectedListener {

    var binding: ActivitySignupBinding? = null
    var isUsernameValid: Boolean = false
    var isPassworvalid: Boolean = false
    var isEmailValid: Boolean = false
    var isCountryValid: Boolean = false
    var isPhoneNumberValid: Boolean = false
    var isDateofBirthValid: Boolean = false
    var isConfirmPasswordValid: Boolean = false
    var spinnerValue: String = ""

    private var progressBar = CustomProgressBar()

    @SuppressLint("SetTextI18n")
    var getDatePicker: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            var selectedMonth = selectedMonth
            selectedMonth += 1
            binding!!.includeSignupForm.etDateOfBirth.setText(
                "$selectedYear-${
                    AppUtils.getMonth(
                        selectedMonth
                    )
                }-${AppUtils.getMonth(selectedDay)}"
            )
        }


    private fun hideKeyboard() {
        binding!!.includeSignupForm.etDateOfBirth.keyListener = null
        binding!!.includeSignupForm.etDateOfBirth.isFocusable = false
    }


    fun start(binding_signUp: ActivitySignupBinding) {
        binding = binding_signUp
        getUsernameListner()
        getPasswordListner()
        getPhoneNumberListner()
        getDateofBirthListner()
        //getCountryAddressListner()
        getConfirmPasswordListner()
        getEmailAddressListner()
        setSpinner()
        loadOnlyClickable()
    }

    //username
    fun getUsername(): String {
        System.out.println("Username" + binding!!.includeSignupForm.etFullName.text.toString())
        return binding!!.includeSignupForm.etFullName.text.toString()
    }

    //email
    fun getEmailAddress(): String {
        System.out.println("Email" + binding!!.includeSignupForm.etEmail.text.toString())
        return binding!!.includeSignupForm.etEmail.text.toString()
    }

    //password
    fun getUserPassowrd(): String {
        System.out.println("Password" + binding!!.includeSignupForm.etPassword.text.toString())
        return binding!!.includeSignupForm.etPassword.text.toString()

    }

    //countryaddress spinner
    fun getCountryAddress(): String {
        System.out.println("CountryName " + binding!!.includeSignupForm.etCountryName.toString())
        return binding!!.includeSignupForm.etCountryName.toString()
    }

    //setting spinner
    fun setSpinner() {
        ArrayAdapter.createFromResource(
            appCompatActivity, R.array.country_array, layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding!!.includeSignupForm.etCountryName.adapter = adapter
        }
        binding!!.includeSignupForm.etCountryName.onItemSelectedListener = this
    }

    /*
     When a user clicks submit button following functionality setBrighterSignUpButton changes the color of the submit button
     to little brightness and setDimSubmitButton to little dim. ie. from gradient_drawable to shapeemail
     */

    fun setBrighterSignUpButton() {
        binding!!.includeSignupForm.btnSignUp.setImageResource(submitbrightness)
    }

    fun setDimSubmitButton() {
        binding!!.includeSignupForm.btnSignUp.setImageResource(R.drawable.gradient_drawable)
    }

    //phone
    fun getPhoneNumber(): String {
        System.out.println("Value of PhoneNumber is" + binding!!.includeSignupForm.etPhoneDetails.text.toString())
        return binding!!.includeSignupForm.etPhoneDetails.text.toString()
    }

    //dob
    fun getDateOfBirth(): String {
        System.out.println("Value of Dob is" + binding!!.includeSignupForm.etDateOfBirth.text.toString())
        return binding!!.includeSignupForm.etDateOfBirth.text.toString()
    }

    //confirm password
    fun getConfirmPassword(): String {
        System.out.println("Value of ConfirmPassword is" + binding!!.includeSignupForm.etConfirmPassword.text.toString())
        return binding!!.includeSignupForm.etConfirmPassword.text.toString()
    }




/*This functionality {@getPhoneNumberListner()} is to set action listner in etPhoneDetails EditTextView.
  It checks the phonenumber validations by taking rules from AppUtils.If this doesnot satisy
  the rules written in apputils, signup button cannot be pressed.*/

    private fun getPhoneNumberListner() {
        RxTextView.textChanges(binding!!.includeSignupForm.etPhoneDetails).skip(1)
            .map(CharSequence::toString)
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                when {
                    getPhoneNumber().isEmpty() -> {
                        isPhoneNumberValid = false
                        getBtnEnable(false)
                    }
                    else -> {
                        when {
                            AppUtils.isContactValid(getPhoneNumber()) -> {
                                isPhoneNumberValid = true
                                when {
                                    isValid() -> getBtnEnable(true)
                                }
                            }
                            else -> {
                                isPhoneNumberValid = false
                                var myPhoneNumber = getPhoneNumber()
                                myPhoneNumber.numberEqualTo(10) {
                                    // This method will be called when user enters phoneNumber which doesnot contains number less than 10.
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

    /*This functionality {@getDateofBirthListner()} is to set action listner in etDateOfBirth EditTextView.
      It checks the dateofbirth validations by taking rules from AppUtils.If this doesnot satisy
      the rules written in apputils, signup button cannot be pressed.*/

    private fun getDateofBirthListner() {
        RxTextView.textChanges(binding!!.includeSignupForm.etDateOfBirth).skip(1)
            .map(CharSequence::toString)
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {


                when {
                    getDateOfBirth().isEmpty() -> {
                        isDateofBirthValid = false
                        getBtnEnable(false)
                    }
                    else -> {

                        isDateofBirthValid = true
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

    /*This functionality {@getUsernameListner()} is to set action listner in etFullName EditTextView.
          It checks the dateofbirth validations by taking rules from AppUtils.If this doesnot satisy
          the rules written in apputils, signup button cannot be pressed.*/

    private fun getUsernameListner() {
        RxTextView.textChanges(binding!!.includeSignupForm.etFullName).skip(1)
            .map(CharSequence::toString)
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                when {
                    getUsername().isEmpty() -> {
                        isUsernameValid = false
                        getBtnEnable(false)
                    }
                    else -> {
                        when {
                            AppUtils.isFirstNameValid(getUsername()) -> {
                                isUsernameValid = true
                                when {
                                    isValid() -> getBtnEnable(true)
                                }
                            }
                            else -> {
                                isUsernameValid = false
                                var myUserName = getUsername()
                                var validator = myUserName.validator()
                                var isValid = validator.addRule(ValidationRule()).check()
                                if (isValid.equals(false)) {
                                    // This method will be called when users enters username which contains invalid inputs as defined in FirstNameRule.
                                    val toast = Toast.makeText(appCompatActivity, "Invalid Username", Toast.LENGTH_SHORT)
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
           the rules written in apputils, signup button cannot be pressed.*/

    private fun getPasswordListner() {
        RxTextView.textChanges(binding!!.includeSignupForm.etPassword).skip(1)
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
                                var myPasswordstr = binding!!.includeSignupForm.etPassword

                                myPasswordstr.validator()
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


    /*This functionality {@getCountryAddressListner()} is to set action listner in etCountryName EditTextView.
      It checks the country validations by taking rules from AppUtils.If this doesnot satisy
      the rules written in apputils, signup button cannot be pressed.*/

    private fun getCountryAddressListner() {
        RxAdapterView.itemSelections(binding!!.includeSignupForm.etCountryName).skip(1)
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                when {
                    getCountryAddress().isEmpty() -> {
                        var etcountryName = binding!!.includeSignupForm.etCountryName
                        var isValid = etcountryName.isEmpty()
                        System.out.println("The value of isValid is " + isValid)

                        if (isValid.equals(false)) {
                            Toast.makeText(
                                appCompatActivity,
                                "Please select your country",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        isCountryValid = false
                        getBtnEnable(false)
                    }
                    else -> {
                        isCountryValid = true
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


    /*This functionality {@getConfirmPasswordListner()} is to set action listner in etConfirmPassword EditTextView.
       It checks the password validations by taking rules from AppUtils.If this doesnot satisy
       the rules written in apputils, signup button cannot be pressed.*/

    private fun getConfirmPasswordListner() {
        RxTextView.textChanges(binding!!.includeSignupForm.etConfirmPassword).skip(1)
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

                                var myConfirmPasswordstr =
                                    binding!!.includeSignupForm.etConfirmPassword

                                myConfirmPasswordstr.validator()
                                    .nonEmpty()
                                    .minLength(8)
                                    .atleastOneUpperCase()
                                    .atleastOneSpecialCharacters()
                                    .atleastOneNumber()
                                    .addErrorCallback {
                                        //myPasswordstr.error = it (for red-lined error)
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

    /*This functionality {@getEmailAddressListner()} is to set action listner in etEmail EditTextView.
   It checks the email validations by taking rules from AppUtils.If this doesnot satisy
   the rules written in apputils, signup button cannot be pressed.*/

    private fun getEmailAddressListner() {
        RxTextView.textChanges(binding!!.includeSignupForm.etEmail).skip(1)
            .map(CharSequence::toString)
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                when {
                    getEmailAddress().isEmpty() -> {
                        isEmailValid = false
                        getBtnEnable(false)
                    }

                    else -> {
                        when {
                            AppUtils.isEmailValid(getEmailAddress()) -> {
                                isEmailValid = true
                                when {
                                    isValid() -> getBtnEnable(true)
                                }
                            }
                            else -> {
                                isEmailValid = false
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

    /*This functionality {@isValid()} checks the following valid and checks for rules satisfaction written in apputils.If
      they satisfy the rules it returns true , otherwise it returns false*/
    private fun isValid(): Boolean {
        return when {
            isUsernameValid && isEmailValid && isPhoneNumberValid && isPassworvalid && isConfirmPasswordValid && isDateofBirthValid && checkPasswordVerification() -> true
            else -> false
        }
    }

    /*This function{@setConfirmMessage()} set error message to the user if entered password and confirm password are not same.*/
    fun setConfirmMessage() {
        if (getUserPassowrd().equals(getConfirmPassword())) {
            System.out.println("The UserPassword value and Confirm Password Value is " + getUserPassowrd() + getConfirmPassword())
        } else {
            Toast.makeText(
                appCompatActivity,
                "Make sure you have same passwords in password and confirm password",
                Toast.LENGTH_SHORT
            ).show()
            //ErrorDailog(appCompatActivity, "Make sure you have same passwords in password and confirm password") }
        }
    }


    /*This function {@loadOnlyClickable()} is responsible to make button disable if user presses login button without entering inputs. */
    fun loadOnlyClickable() {
        if (isUsernameValid && isEmailValid && isPhoneNumberValid && isPassworvalid && isConfirmPasswordValid && isDateofBirthValid && checkPasswordVerification().equals(
                true
            )
        ) {
            binding!!.includeSignupForm.btnSignUp.isEnabled = true

        } else {
            binding!!.includeSignupForm.btnSignUp.isEnabled = false
        }
    }

    /*This functionality {@getBtnEnable(params) ensures for SignUp button activation and deactivation} */
    fun getBtnEnable(aboolena: Boolean) {
        when {
            aboolena -> binding!!.includeSignupForm.btnSignUp.isEnabled = true
            else -> binding!!.includeSignupForm.btnSignUp.isEnabled = false
        }
    }


    /*
       The following function checkPasswordVerification checks the user's input as password and confirm password value.
       If password value == confirm password value, it enables the submit button, otherwise it disable the submit button.
     */

    fun checkPasswordVerification(): Boolean {
        return when {
            getUserPassowrd().equals(getConfirmPassword()) -> true
            else -> false
        }
    }



    fun getSignUpObserable(): Observable<Any> {
        return RxView.clicks(binding!!.includeSignupForm.btnSignUp)
    }

    fun showProgressDialog(statusString: String) {
        progressBar.show(appCompatActivity, statusString)
    }

    fun hidProgressDialog() {
        progressBar.getDialog()!!.dismiss()
    }

    //errormessage
    fun getErrorMessage(message: String) {
        appCompatActivity.runOnUiThread(Runnable {
            ErrorDailog(appCompatActivity, message!!)
        })
    }

    //back arrow
    fun getLoginObserable(): Observable<Any> {
        return RxView.clicks(binding!!.buttonArrow)
    }

    // for date
    fun getDateObserable(): Observable<Any> {
        return RxView.clicks(binding!!.includeSignupForm.etDateOfBirth)
    }

    fun getSpinnerObserable(): Observable<Any> {
        return RxView.clicks(binding!!.includeSignupForm.etCountryName)
    }

    //ensures network connection
    fun checkNetwork(): Boolean {
        return NetworkUtil.checkInternetConnection(appCompatActivity)
    }

    //showDatePicker dialog to pick date
    fun showDatePicker(year: Int, month: Int, day: Int) {
        val d = DatePickerDialog(
            appCompatActivity,
            AlertDialog.THEME_HOLO_LIGHT,
            getDatePicker,
            year,
            month,
            day
        )
        d.requestWindowFeature(Window.FEATURE_NO_TITLE)
        d.datePicker.maxDate = System.currentTimeMillis() - 1000
        d.show()
    }


    /**
     * This overide functionality implements the action listener when a user selects the spinnerItems.
     * @parent: AdapterView, @position: Int
     *
     * The main logic behind the OnItemSelectedListener is to set the textcolor of the spinnervalue to black for array[0]<x
     * and array[0] to LTGRAY color .
     *
     * And store the user selected spinner items values in spinnerValue.
     **/
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position != 0) {
            spinnerValue = parent?.getItemAtPosition(position) as String
            println("country position is$spinnerValue")
            (parent?.getChildAt(0) as TextView).setTextColor(Color.BLACK)
        } else {
            (parent?.getChildAt(0) as TextView).setTextColor(Color.LTGRAY)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    /*This functionality {@getSignUpRequest()} makes a request to the api by taking user entered client_fullname,email,nationality,phone,db,password and cpassword
    input to the server.*/
    fun getSignUpRequest(): SignUpRequest {
        return SignUpRequest(
            client_fullname = getUsername(),
            email = getEmailAddress(),
            nationality = spinnerValue,
            phone = getPhoneNumber(),
            dob = getDateOfBirth(),
            password = getUserPassowrd(),
            cpassword = getConfirmPassword(),
        )
    }
}

