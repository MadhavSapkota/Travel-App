package com.app.travel.activity.editprofile.mvp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.travel.R
import com.app.travel.activity.editprofile.dto.EditProfileRequest
import com.app.travel.apputlis.AppUtils
import com.app.travel.apputlis.NetworkUtil
import com.app.travel.apputlis.UserInfo
import com.app.travel.apputlis.rules.ValidationRule
import com.app.travel.databinding.FragmentEditprofileBinding
import com.app.travel.dialog.ErrorDailog
import com.elarkgroup.gwcl.appcomponent.CustomProgressBar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.wajahatkarim3.easyvalidation.core.view_ktx.numberEqualTo
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class EditProfileView(private val appCompatActivity: AppCompatActivity) :
    AdapterView.OnItemSelectedListener {
    var binding: FragmentEditprofileBinding? = null
    var isUsernameValid: Boolean = false
    var isEmailValid: Boolean = false
    var isCountryValid: Boolean = false
    var isPhoneNumberValid: Boolean = false
    var isDateofBirthValid: Boolean = false
    private var progressBar = CustomProgressBar()
    val REQUEST_GALLERY_PHOTO = 102
    var spinnerValue: String = ""

    //Date Picker
    var getDatePicker: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            var selectedMonth = selectedMonth
            selectedMonth += 1
            binding!!.etDateOfBirth.setText(
                "$selectedYear-${
                    AppUtils.getMonth(
                        selectedMonth
                    )
                }-${AppUtils.getMonth(selectedDay)}"
            )
        }

    fun start(binding_profilebinding: FragmentEditprofileBinding) {
        binding = binding_profilebinding
        getUsernameListner()
        getPhoneNumberListner()
        getDateofBirthListner()
        getEmailAddressListner()
        setUserName()
        setEmailAddress()
        setPhoneNumber()
        setDateOfBirth()
        getCameraObserable()
        setSpinner()
        loadOnlyClickable()
//        setProfilePic()
    }


    fun getClientFullname(): String {
        System.out.println("FullName" + binding!!.etFullName.text.toString())
        return binding!!.etFullName.text.toString()
    }


    fun checkNetwork(): Boolean {
        return NetworkUtil.checkInternetConnection(appCompatActivity)
    }

    //For Error message
    fun getErrorMessage(message: String) {
        appCompatActivity.runOnUiThread(Runnable {
            ErrorDailog(
                appCompatActivity,
                message!!
            )
        })
    }

    //back arrow
    fun getBackArrowObserable(): Observable<Any> {
        return RxView.clicks(binding!!.buttonArrow)
    }


    //date of birth
    fun getDateOfBirth(): String {
        System.out.println("DateBirth" + binding!!.etDateOfBirth.text.toString())
        return binding!!.etDateOfBirth.text.toString()
    }

    fun getDateObserable(): Observable<Any> {
        return RxView.clicks(binding!!.etDateOfBirth)
    }

    fun getEmailAddress(): String {
        System.out.println("EmailAddress" + binding!!.etEmailAddress.text.toString())
        return binding!!.etEmailAddress.text.toString()
    }

    fun getPhoneNumber(): String {
        System.out.println("PhoneNumber" + binding!!.etPhoneDetails.text.toString())
        return binding!!.etPhoneDetails.text.toString()
    }
    //savebtn
    fun getEditProfileObserable(): Observable<Any> {
        return RxView.clicks(binding!!.ivSaveInformation)
    }

    fun setUserName() {
        binding!!?.etFullName?.setText(UserInfo.clientFullname)
        System.out.println("ClientFullName is " + UserInfo.clientFullname)
    }

    fun setEmailAddress() {
        binding!!.etEmailAddress.setText(UserInfo.email)
        System.out.println("EmailAddress is " + UserInfo.email)
    }


    fun getCountryAddress(): String {
        System.out.println("CountryName " + binding!!.etAddress.toString())
        return binding!!.etAddress.toString()
    }

    //spinner setup by fetching country_array from string in adapter.
    fun setSpinner() {
        ArrayAdapter.createFromResource(
            appCompatActivity, R.array.country_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding!!.etAddress.adapter = adapter
        }
        binding!!.etAddress.onItemSelectedListener = this
    }


    fun setPhoneNumber() {
        binding!!.etPhoneDetails.setText(UserInfo.phone)
        System.out.println("PhoneNumber is " + UserInfo.phone)
    }

    fun setDateOfBirth() {
        binding!!.etDateOfBirth.setText(UserInfo.dob)
        System.out.println("DateofBirth is" + UserInfo.dob)
    }

    fun showProgressDialog(statusString: String) {
        progressBar.show(appCompatActivity, statusString)
    }

    fun hidProgressDialog() {
        progressBar.getDialog()!!.dismiss()
    }


    /*Camera opening*/
    fun getCameraObserable(): Observable<Any> {
        return RxView.clicks(binding!!.imageView8)
    }

    //showDatePicker is to show dialog for DatePickerDialog
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

    /*For the username*/
    private fun getUsernameListner() {
        RxTextView.textChanges(binding!!.etFullName).skip(1)
            .map(CharSequence::toString)
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {

                when {
                    getClientFullname().isEmpty() -> {
                        isUsernameValid = false
                        getBtnEnable(false)
                    }
                    else -> {
                        when {
                            AppUtils.isFirstNameValid(getClientFullname()) -> {
                                isUsernameValid = true
                                when {
                                    isValid() -> getBtnEnable(true)
                                }
                            }
                            else -> {
                                isUsernameValid = false
                                var myUserName = getClientFullname()
                                var validator = myUserName.validator()
                                var isValid = validator.addRule(ValidationRule()).check()
                                if (isValid.equals(false)) {
                                    // This method will be called when users enters username which contains invalid inputs as defined in FirstNameRule.
                                    val toast = Toast.makeText(appCompatActivity, "Invalid Username", Toast.LENGTH_SHORT)
                                    toast.show()
                                    val handler = Handler()
                                    handler.postDelayed(Runnable { toast.cancel() }, 10)

                                }
                                getBtnEnable(false)
                            }
                        }
                    }
                }
            }
            .subscribe()
    }

    /*For emaillistner*/
    private fun getEmailAddressListner() {
        RxTextView.textChanges(binding!!.etEmailAddress).skip(1)
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

//    private fun getCountryAddressListner() {
//        RxTextView.textChanges(binding!!.editprofileRecyclerView.etAddress).skip(1)
//            .debounce(200, TimeUnit.MILLISECONDS)
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnNext {
//                when {
//                    getNationalAddress().isEmpty() -> {
//                        isCountryValid = false
//                        getBtnEnable(false)
//                    }
//                    else -> {
//                        isCountryValid = true
//                        when {
//                            isValid() -> getBtnEnable(true)
//                            else -> getBtnEnable(false)
//                        }
//                    }
//                }
//            }.subscribe()
//    }


    /*For PhoneNumber*/
    private fun getPhoneNumberListner() {
        RxTextView.textChanges(binding!!.etPhoneDetails).skip(1)
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
            }.subscribe()
    }

    /*For DateOfBirth*/
    private fun getDateofBirthListner() {
        RxTextView.textChanges(binding!!.etDateOfBirth).skip(1)
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
            }.subscribe()
    }

    //this inValid() code is to check all the userinput validations and set true if accurate else false.
    private fun isValid(): Boolean {
        return when {
            isUsernameValid && isEmailValid && isPhoneNumberValid &&
                    isDateofBirthValid -> true
            else -> false
        }
    }

    /*This function {@loadOnlyClickable()} is responsible to make button disable if user presses login button without entering inputs. */
    fun loadOnlyClickable() {
        if (isUsernameValid && isEmailValid && isPhoneNumberValid && isDateofBirthValid.equals(true)) {
            binding!!.ivSaveInformation.isEnabled = true
        } else {
            binding!!.ivSaveInformation.isEnabled = false

        }
    }

    //BottonEnable if user enters right user infotmation or BottonDisable if user enters right or wrong userinformation
    fun getBtnEnable(aboolena: Boolean) {
        when {
            aboolena -> binding!!.ivSaveInformation.isEnabled = true
            else -> binding!!.ivSaveInformation.isEnabled = false
        }
    }

    //api request
    fun getEditDetailsRequest(): EditProfileRequest {
        return EditProfileRequest(
            client_fullname = getClientFullname(),
            dob = getDateOfBirth(),
            email = getEmailAddress(),
            phone = getPhoneNumber(),
            nationality = spinnerValue,
        )
    }

    fun setCustomerImage(drawable: Drawable) {
        binding!!.shapeableImageView.setImageDrawable(drawable)
    }

    //Spinner Item selections code
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        (parent?.getChildAt(0) as TextView).setText(UserInfo.nationality)
        if (position != 0) {
            spinnerValue = parent?.getItemAtPosition(position) as String
            println("country position is$spinnerValue")
            (parent?.getChildAt(0) as TextView).setText(spinnerValue)
        } else {
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(
            appCompatActivity,
            "Select your country",
            Toast.LENGTH_SHORT
        ).show()
    }
}