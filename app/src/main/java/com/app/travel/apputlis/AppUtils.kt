package com.app.travel.apputlis

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object AppUtils {
    //emailvalidation
    fun isEmailValid(email: String): Boolean {
        val regExpn=("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")

        val pattern= Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE)
        val matcher=pattern.matcher(email)
        return matcher.matches()
    }


    //firstnamevalidation
    fun isFirstNameValid(firstname: String):Boolean{
      val regExpn  = ("[a-zA-z]+([ '-][a-zA-Z]+)*")
      val pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE)
      val matcher = pattern.matcher(firstname)
      return matcher.matches()

    }
    //phonenumbervalidation
    fun isContactValid(phone: String):Boolean{
        val regExpn  = ("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*\$")
        val pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(phone)
        return matcher.matches()
    }

    //months validations
    fun getMonth(month: Int): String {
        return when {
            month.toString().length == 1 -> {
                "0$month"
            }
            else -> "$month"
        }
    }


       //for date formatter
        val formatter = SimpleDateFormat("MM/dd")
        val format1 = SimpleDateFormat("MMM dd")
}